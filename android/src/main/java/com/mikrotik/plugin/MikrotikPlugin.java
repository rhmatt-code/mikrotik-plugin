package com.mikrotik.plugin;

import android.util.Log;
import com.getcapacitor.*;
import com.getcapacitor.annotation.CapacitorPlugin;
import javax.net.SocketFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.legrange.mikrotik.ApiConnection;

@CapacitorPlugin(name = "MikrotikPlugin")
public class MikrotikPlugin extends Plugin {

    private ApiConnection con;
    @PluginMethod
    public void connect(PluginCall call) {
      try {
        String ip = call.getString("ip");
        String username = call.getString("username");
        String password = call.getString("password");
        int port = call.getInt("port", 8728);

        con = ApiConnection.connect(SocketFactory.getDefault(), ip, port, 30000);
        con.login(username, password);

        JSObject ret = new JSObject();
        ret.put("success", true);
        ret.put("message", "Login Berhasil");

        call.resolve(ret);

      } catch (Exception e) {
        JSObject ret = new JSObject();
        ret.put("success", false);
        ret.put("error", e.toString());
        call.resolve(ret);
      }
    }

  @PluginMethod
  public void getResource(PluginCall call){
    JSObject ret = new JSObject();

    new Thread(() -> {
      try {
        if (con == null) {
          ret.put("success", false);
          ret.put("error", "not connected");
          call.resolve(ret);
          return;
        }

        List<Map<String, String>> result = con.execute("/system/resource/print");

        if (result == null || result.isEmpty()) {
          ret.put("success", false);
          ret.put("error", "no data");
          call.resolve(ret);
          return;
        }

        Map<String, String> data = result.get(0);

        JSObject router = new JSObject();

        router.put("uptime", data.get("uptime"));
        router.put("model", data.get("board-name"));
        router.put("version", data.get("version"));
        router.put("cpu", data.get("cpu-load"));

        long totalRam = Long.parseLong(data.get("total-memory"));
        long freeRam = Long.parseLong(data.get("free-memory"));
        long usedRam = totalRam - freeRam;
        int ramPercent = (int)((usedRam * 100) / totalRam);

        long totalDisk = Long.parseLong(data.get("total-hdd-space"));
        long freeDisk = Long.parseLong(data.get("free-hdd-space"));
        long usedDisk = totalDisk - freeDisk;
        int diskPercent = (int)((usedDisk * 100) / totalDisk);
        router.put("ram", ramPercent);
        router.put("disk", diskPercent);

        ret.put("success", true);
        ret.put("data", router);

        call.resolve(ret);

      } catch (Exception e){
        ret.put("success", false);
        ret.put("error", e.toString());
        call.resolve(ret);
      }
    }).start();
  }


    @PluginMethod
    public void getIdentity(PluginCall call){
        JSObject ret = new JSObject();
        new Thread (() -> {
        try{
            if(con == null){
                ret.put("success", false);
                ret.put("error", "not connected");
                call.resolve(ret);
                return;
            }

            List<Map<String, String>> result = con.execute("/system/identity/print");
            if (result == null || result.isEmpty()) {
              ret.put("success", false);
              ret.put("error", "no data from mikrotik");
              call.resolve(ret);
              return;
            }
            Map<String, String> data = result.get(0);

            JSObject router = new JSObject();
            router.put("identity", data.get("name"));

            ret.put("success", true);
            ret.put("data", router);

        } catch (Exception e){
            ret.put("success", false);
            ret.put("error", e.toString());
        }

        call.resolve(ret);
        }).start();
    }

    @PluginMethod
    public void getPppoeSecrets(PluginCall call) {
        JSObject ret = new JSObject();
        new Thread(() -> {
            try {
                if (con == null) {
                    ret.put("success", false);
                    ret.put("error", "not connected");
                    call.resolve(ret);
                    return;
                }

                // Ambil semua secrets
                List<Map<String, String>> secrets = con.execute("/ppp/secret/print");

                // Ambil semua yang aktif
                List<Map<String, String>> actives = con.execute("/ppp/active/print");

                // Buat map nama -> data active
                Map<String, Map<String, String>> activeMap = new HashMap<>();
                for (Map<String, String> a : actives) {
                    activeMap.put(a.get("name"), a);
                }

                JSArray list = new JSArray();
                for (Map<String, String> s : secrets) {
                    JSObject item = new JSObject();
                    String name = s.get("name");
                    boolean isActive = activeMap.containsKey(name);

                    item.put("name", name);
                    item.put("profile", s.get("profile"));
                    item.put("service", s.get("service"));
                    item.put("comment", s.getOrDefault("comment", ""));
                    item.put("status", isActive ? "active" : "offline");

                    if (isActive) {
                        Map<String, String> activeData = activeMap.get(name);
                        item.put("ipAddress", activeData.get("address"));
                        item.put("uptime", activeData.get("uptime"));
                        item.put("caller", activeData.get("caller-id"));
                    }

                    list.put(item);
                }

                ret.put("success", true);
                ret.put("data", list);
                call.resolve(ret);

            } catch (Exception e) {
                ret.put("success", false);
                ret.put("error", e.toString());
                call.resolve(ret);
            }
        }).start();
    }

    @PluginMethod
    public  void getPppoeProfile(PluginCall call){
      JSObject ret = new JSObject();

      new Thread(() -> {
        try {
          List<Map<String, String>> result =
            con.execute("/ppp/profile/print");

          JSArray arr = new JSArray();

          for (Map<String, String> row : result) {
            JSObject item = new JSObject();
            item.put("name", row.get("name"));
            item.put("rateLimit", row.get("rate-limit"));
            arr.put(item);
          }

          ret.put("success", true);
          ret.put("data", arr);

        } catch (Exception e) {
          ret.put("success", false);
          ret.put("error", e.toString());
        }

        call.resolve(ret);
      }).start();
    }

  @PluginMethod
  public void addPppoeProfile(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {
      try {

        String name = call.getString("name");
        String rate = call.getString("rate");

        String cmd =
          "/ppp/profile/add " +
            "name=" + name + " " +
            "rate-limit=" + rate;

        con.execute(cmd);

        ret.put("success", true);

      } catch (Exception e) {
        ret.put("success", false);
        ret.put("error", e.toString());
      }

      call.resolve(ret);

    }).start();
  }

  @PluginMethod
  public void addPppoeSecret(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {
      try {

        String username = call.getString("username");
        String password = call.getString("password");
        String profile = call.getString("profile");

        String cmd =
          "/ppp/secret/add " +
            "name=" + username + " " +
            "password=" + password + " " +
            "service=pppoe " +
            "profile=" + profile;

        con.execute(cmd);

        ret.put("success", true);

      } catch (Exception e) {
        ret.put("success", false);
        ret.put("error", e.toString());
      }

      call.resolve(ret);

    }).start();
  }

  @PluginMethod
  public void deletePPoE(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        String name = call.getString("name");

        String cmd =
          "/ppp/secret/remove " +
          ".id=" + name;

        con.execute(cmd);

        ret.put("success", true);
        ret.put("message", "PPPoE berhasil dihapus");

      } catch (Exception e) {

        ret.put("success", false);
        ret.put("error", e.toString());
      }

      call.resolve(ret);

    }).start();
  }

  @PluginMethod
  public void editPPoE(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        String oldName = call.getString("name");
        String profile = call.getString("profileNew");

        String cmd =
          "/ppp/secret/set " +
            ".id=" + oldName + " " +
            "profile=" + profile;

        con.execute(cmd);

        ret.put("success", true);
        ret.put("message", "PPPoE berhasil diupdate");

      } catch (Exception e) {

        ret.put("success", false);
        ret.put("error", e.toString());
      }

      call.resolve(ret);

    }).start();
  }

    @PluginMethod
    public void getLogs(PluginCall call) {
        JSObject ret = new JSObject();
        new Thread(() -> {
        try {
            if(con == null){
                ret.put("success", false);
                ret.put("error", "not connected");
                call.resolve(ret);
                return;
            }

            // TEST ambil identity router
            List<Map<String, String>> logs =
              con.execute("/log/print");
            JSArray logArray = new JSArray();

            for (Map<String, String> log : logs){
                JSObject item = new JSObject();
                item.put("time", log.get("time"));
                item.put("message", log.get("message"));
                item.put("topics", log.get("topics"));

                logArray.put(item);
            }


            ret.put("success", true);
            ret.put("logs", logArray);

        } catch (Exception e) {
            ret.put("success", false);
            ret.put("error", e.getMessage());
        }

        call.resolve(ret);
        }).start();
    }

    @PluginMethod
    public void getHotspot(PluginCall call) {
        JSObject ret = new JSObject();
        new Thread(() -> {
            try {
                if (con == null) {
                    ret.put("success", false);
                    ret.put("error", "not connected");
                    call.resolve(ret);
                    return;
                }

                // Ambil semua secrets
                List<Map<String, String>> secrets = con.execute("/ip/hotspot/user/print");

                // Ambil semua yang aktif
                List<Map<String, String>> actives = con.execute("/ip/hotspot/active/print");

                // Buat map nama -> data active
                Map<String, Map<String, String>> activeMap = new HashMap<>();
                for (Map<String, String> a : actives) {
                    activeMap.put(a.get("user"), a);
                }

                JSArray list = new JSArray();
                for (Map<String, String> s : secrets) {
                    JSObject item = new JSObject();
                    String name = s.get("name");
                    boolean isActive = activeMap.containsKey(name);

                    item.put("name", name);
                    item.put("password", s.get("password"));
                    item.put("profile", s.get("profile"));
                    item.put("status", isActive ? "active" : "offline");

                    if (isActive) {
                        Map<String, String> activeData = activeMap.get(name);
                        item.put("ipAddress", activeData.get("address"));
                        item.put("uptime", activeData.get("uptime"));
                    }

                    list.put(item);
                }

                ret.put("success", true);
                ret.put("data", list);
                call.resolve(ret);

            } catch (Exception e) {
                ret.put("success", false);
                ret.put("error", e.toString());
                call.resolve(ret);
            }
        }).start();
    }

    @PluginMethod
    public void getHotspotProfiles(PluginCall call) {

        JSObject ret = new JSObject();

        new Thread(() -> {
            try {

                List<Map<String,String>> result =
                    con.execute("/ip/hotspot/user/profile/print");

                JSArray arr = new JSArray();

                for(Map<String,String> row : result){

                    JSObject item = new JSObject();
                    item.put("name", row.get("name"));

                    arr.put(item);
                }

                ret.put("success", true);
                ret.put("data", arr);

            } catch(Exception e){
                ret.put("success", false);
                ret.put("error", e.toString());
            }

            call.resolve(ret);

        }).start();
    }

  @PluginMethod
  public void generateHotspotVouchers(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        int total = call.getInt("total", 10);
        String duration = call.getString("duration", "1h");
        String profile = call.getString("profile", "default");
        String prefix = call.getString("prefix", "VC");

        JSArray arr = new JSArray();

        for (int i = 0; i < total; i++) {

          String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

          StringBuilder random = new StringBuilder();

          for (int x = 0; x < 5; x++) {
            random.append(
              chars.charAt(
                (int)(Math.random() * chars.length())
              )
            );
          }

          String voucher = prefix + random.toString();

          String cmd =
            "/ip/hotspot/user/add " +
              "name=" + voucher + " " +
              "password=" + voucher + " " +
              "profile=" + profile + " " +
              "limit-uptime=" + duration;

          con.execute(cmd);

          JSObject item = new JSObject();
          item.put("username", voucher);
          item.put("password", voucher);
          item.put("duration", duration);
          item.put("profile", profile);

          arr.put(item);
        }

        ret.put("success", true);
        ret.put("message", "Voucher berhasil dibuat");
        ret.put("data", arr);

        call.resolve(ret);

      } catch (Exception e) {

        ret.put("success", false);
        ret.put("error", e.toString());

        call.resolve(ret);
      }

    }).start();
  }

  @PluginMethod
  public void addHotspotProfile(PluginCall call) {

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        String name = call.getString("name", "");
        String shared = call.getString("shared", "1");
        String duration = call.getString("duration");
        String rateLimit = call.getString("rate");


        String cmd =
            "/ip/hotspot/user/profile/add " +
            "name=" + name + " " +
            "shared-users=" + shared + " " +
            "status-autorefresh=1m " +
            "rate-limit=\"" + rateLimit + "\"" +
            "on-login=\"if ([/system scheduler find name=\\$user]=\"\") do={/system scheduler add name=\\$user interval=" + duration + " on-event=/ip hotspot user remove [find name=\\$user]}\"";



        con.execute(cmd);

        ret.put("success", true);

      } catch (Exception e) {

        ret.put("success", false);
        ret.put("error", e.toString());

      }

      call.resolve(ret);

    }).start();
  }

  @PluginMethod
  public void EditHotspot(PluginCall call){

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        String name = call.getString("name");
        String password = call.getString("password");
        String profile = call.getString("profileNew");

        List<Map<String,String>> users =
          con.execute("/ip/hotspot/user/print");

        String id = "";

        for(Map<String,String> u : users){
          if(name.equals(u.get("name"))){
            id = u.get(".id");
            break;
          }
        }

        if(id.equals("")){
          ret.put("success", false);
          ret.put("error", "user tidak ditemukan");
          call.resolve(ret);
          return;
        }

        String cmd =
          "/ip/hotspot/user/set " +
            ".id=" + id + " " +
            "password=" + password + " " +
            "profile=" + profile;

        con.execute(cmd);

        ret.put("success", true);

      } catch(Exception e){

        ret.put("success", false);
        ret.put("error", e.toString());

      }

      call.resolve(ret);

    }).start();
  }

  @PluginMethod
  public void deleteHotspot(PluginCall call){

    JSObject ret = new JSObject();

    new Thread(() -> {

      try {

        String name = call.getString("name");

        List<Map<String,String>> users =
          con.execute("/ip/hotspot/user/print");

        String id = "";

        for(Map<String,String> u : users){
          if(name.equals(u.get("name"))){
            id = u.get(".id");
            break;
          }
        }

        if(id.equals("")){
          ret.put("success", false);
          ret.put("error", "user tidak ditemukan");
          call.resolve(ret);
          return;
        }

        con.execute(
          "/ip/hotspot/user/remove .id=" + id
        );

        ret.put("success", true);

      } catch(Exception e){

        ret.put("success", false);
        ret.put("error", e.toString());

      }

      call.resolve(ret);

    }).start();
  }
}

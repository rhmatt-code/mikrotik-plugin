export interface MikrotikPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  connect(options: { ip: string; username: string; password: string; port: number }): Promise<any>;
  getResource(): Promise<any>;
  getLogs(): Promise<any>;
  getPppoeSecrets(): Promise<any>;
  getHotspot(): Promise<any>;
  getActiveOnly(): Promise<any>;
  getIdentity(): Promise<any>;
  getPppoeProfile(): Promise<any>;
  addPppoeProfile(data: {name: string; rate: number;}): Promise<any>;
  addPppoeSecret(data: {username: string; password: string; profile: string;}): Promise<any>;
  addHotspotProfile(data: {name: string; duration: string; rate: string;}): Promise<any>;
  generateHotspotVouchers(option: any): Promise<any>;
  getHotspotProfiles(): Promise<any>;
  editPPoE(data: {name: string; profileNew: string;}): Promise<any>;
  deletePPoE(data: {name: string;}): Promise<any>;
  editHotspot(data: {name: string; password: string; profileNew: string;}): Promise<any>;
  deleteHotspot(data: {name: string;}): Promise<any>;
}

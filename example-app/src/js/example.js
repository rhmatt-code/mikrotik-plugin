import { MikrotikPlugin } from 'mikrotik-plugin';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    MikrotikPlugin.echo({ value: inputValue })
}

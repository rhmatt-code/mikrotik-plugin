import { registerPlugin } from '@capacitor/core';

import type { MikrotikPlugin } from './definitions';

const MikrotikPlugin = registerPlugin<MikrotikPlugin>('MikrotikPlugin', {
  web: () => import('./web').then((m) => new m.MikrotikPluginWeb()),
});

export * from './definitions';
export { MikrotikPlugin };

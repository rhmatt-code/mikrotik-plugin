import { WebPlugin } from '@capacitor/core';
import type { MikrotikPlugin } from './definitions';

export class MikrotikPluginWeb extends WebPlugin implements MikrotikPlugin {

  async echo(_options: { value: string }): Promise<{ value: string }> {
    throw this.unimplemented('Not implemented on web.');
  }

  async connect(_options: { ip: string; username: string; password: string; port: number }): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getResource(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getLogs(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getPppoeSecrets(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getHotspot(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getActiveOnly(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getIdentity(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getPppoeProfile(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async addPppoeProfile(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async addPppoeSecret(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async addHotspotProfile(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async generateHotspotVouchers(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getHotspotProfiles(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async editPPoE(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async deletePPoE(): Promise<any> {
    throw this.unimplemented('Not implemented on web.');
  }

  async EditHotspot(): Promise<any>{
    throw this.unimplemented('Not implemented on web.');
  }
  
  async deleteHotspot(): Promise<any>{
    throw this.unimplemented('Not implemented on web.');
  }
}
import { Injectable } from "@angular/core";
@Injectable({
    providedIn: 'root'
})

export class LocalStoreManager {
    private reservedKeys: any =
        {
            token: 'token'
        };
    public setToken(token: string) {
        localStorage.setItem(this.reservedKeys.token, token);
    }
    public getToken() : string {
        return localStorage.getItem(this.reservedKeys.token);
    }
}
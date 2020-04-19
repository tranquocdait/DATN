import { Injectable } from '@angular/core';
@Injectable({
    providedIn: 'root'
})

export class LocalStoreManager {
    private pageProfile = '';
    private reservedKeys: any =
        {
            token: 'token',
            numberCart: 'numberCart'
        };
    public setPageProfile(pageProfile: string): void {
        this.pageProfile = pageProfile;
    }
    public getPageProfile(): string {
        return this.pageProfile;
    }
    public setNumberCart(numberCart: string): void {
        localStorage.setItem(this.reservedKeys.numberCart, numberCart);
    }
    public getNumberCart(): number {
        let numberCart = Number.parseInt(localStorage.getItem(this.reservedKeys.numberCart));
        if (localStorage.getItem(this.reservedKeys.numberCart) === null || Number.isNaN(numberCart)) {
            this.setNumberCart('0');
            numberCart = 0;
        }
        return numberCart;
    }
    public addNumberCart() {
        if (this.getNumberCart() === null) {
            localStorage.setItem(this.reservedKeys.numberCart, '0');
        }
        const abc: any = this.getNumberCart();
        localStorage.setItem(this.reservedKeys.numberCart, (this.getNumberCart() + 1).toString());
    }
    public subNumberCart(): void {
        if (this.getNumberCart() > 0) {
            localStorage.setItem(this.reservedKeys.numberCart, (this.getNumberCart() + 1).toString());
        }
    }
    public deleteNumberCart(): void {
        localStorage.removeItem(this.reservedKeys.numberCart);
    }
    public setToken(token: string): void {
        localStorage.setItem(this.reservedKeys.token, token);
    }
    public removeToken() {
        localStorage.removeItem(this.reservedKeys.token);
    }
    public getToken(): string {
        return localStorage.getItem(this.reservedKeys.token);
    }
}

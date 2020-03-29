import { Injectable } from "@angular/core";
import { Observable,throwError } from "rxjs";
import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import {catchError } from 'rxjs/operators';
@Injectable({
    providedIn: 'root'
})

export class EndpointFactory {
    baseUrl: string = "http://localhost:8080/";
    constructor(private httpclient: HttpClient) {

    }
    protected getRequestHeaders(): { headers: HttpHeaders | { [header: string]: string | string[] } } {
        const headers = new HttpHeaders({
        //   Authorization: this.authService.accessToken,
           Authorization: 'token ',
          'Content-Type': 'application/json'
        });
    
        return { headers: headers };
      }
    
    public getEndPointByHeader<T>(endpointUrl): Observable<any> {
        return this.httpclient.get<T>(this.baseUrl+endpointUrl, this.getRequestHeaders()).pipe<T>(
            catchError((error) => {
                return this.handleError(error, () => this.getEndPointByHeader(endpointUrl));
            })
        );
    }
    public getEndPoint<T>(endpointUrl): Observable<any> {
        return this.httpclient.get<T>(this.baseUrl+endpointUrl).pipe<T>(
            catchError((error) => {
                return this.handleError(error, () => this.getEndPoint(endpointUrl));
            })
        );
    }
    public postWithOutToken<T>(urlSearchParams: HttpParams, suburl: string): Observable<any> {
        let url: string = this.baseUrl + suburl;
        let headers: HttpHeaders = new HttpHeaders();
        headers = headers.set('Content-Type', 'application/x-www-form-urlencoded');

        return this.httpclient.post<T>(url, urlSearchParams.toString(), {  headers: headers}).pipe<T>(
            catchError((error) => {
                return this.handleError(error, () => this.postWithOutToken(urlSearchParams, suburl));
            })
        );
    }
    public postEndPoint<T>(params: any, suburl: string): Observable<any> {
        return this.httpclient.post<T>(this.baseUrl+suburl, params).pipe<T>(
            catchError((error) => {
                return this.handleError(error, () => this.postEndPoint(params, suburl));
            })
        );
    }
    public putEndPoint<T>(params: any, suburl: string): Observable<any> {
        return this.httpclient.put<T>(this.baseUrl+suburl, params).pipe<T>(
            catchError((error) => {
                return this.handleError(error, () => this.putEndPoint(params, suburl));
            })
        );
    }
    public deleteEndPoint<T>(params: any, suburl: string): Observable<any> {
        return this.httpclient.delete<T>(this.baseUrl+suburl, params);
    }
    protected handleError(error, continuation: () => Observable<any>) {
        if (error.status === 401) {
    
        }
        return throwError(error);
      }
}
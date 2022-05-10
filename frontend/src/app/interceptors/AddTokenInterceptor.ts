import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
} from '@angular/common/http';
import { ErrorHandler, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, catchError, first, mergeMap, Observable, ObservableInput, Subject, switchMap, tap, throwError } from 'rxjs';
import { UserService } from '../services/user.service';

@Injectable()
export class AddTokenInterceptor implements HttpInterceptor {
    constructor(private userService: UserService, private router: Router) {}

    refreshTokenInProgress = false;

    logout() {
        localStorage.clear();
        this.router.navigate(["login"]);
    }

    isRefreshing = false;

    handleResponseError(request?: any, next?: any): any {
        this.isRefreshing = true;

        this.userService.refresh().subscribe(response => {
            this.isRefreshing = false;

            localStorage.setItem("accessToken", JSON.stringify(response.accessToken));

            next.handle(this.addTokenHeader(request)).subscribe();
        })
    }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<any> {
        if (this.isRefreshing) return next.handle(request);

        request = this.addTokenHeader(request);

        return next.handle(request).pipe(catchError(error => {
            return this.handleResponseError(request, next);
        }))
    }

    addTokenHeader(req: HttpRequest<any>) {
        let accessToken = JSON.parse(localStorage.getItem("accessToken") as string);

        return req.clone({ headers: req.headers.append('Authorization', `Bearer ${accessToken}`) });
    }
}

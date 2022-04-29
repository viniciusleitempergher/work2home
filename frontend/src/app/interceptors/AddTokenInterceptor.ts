import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
} from '@angular/common/http';
import { Observable } from 'rxjs';
    
export class AddTokenInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let accessToken = JSON.parse(localStorage.getItem("accessToken") as any);        
        
        const clonedRequest = req.clone({ headers: req.headers.append('Authorization', `Bearer ${accessToken}`) });

        return next.handle(clonedRequest);
    }
}
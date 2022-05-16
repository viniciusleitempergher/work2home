import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { delay, retry } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  relatorioUsuario(): Promise<any> {
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/relatorio/usuario`,
        { headers: headers, responseType: 'blob' as 'json' }).pipe(
          retry(15),
          delay(2000)
        )
        .subscribe(response => {
          resolve(response as any);
        });
    })
  }
}
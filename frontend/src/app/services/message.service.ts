import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs';
import { environment } from 'src/environments/environment';
import { MessageDto } from 'src/models/MessageDto';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private http:HttpClient) { }

  getMyMessages(): Promise<MessageDto[]> {
    return new Promise(resolve => {
      this.http
      .get(`${environment.apiHostAddress}/message/me`).pipe(
        retry(15),
      )
      .subscribe((response) => resolve(response as MessageDto[]));
    })
  }

  sendMessage(msg: MessageDto): Promise<MessageDto> {
    console.log(msg);
    
    return new Promise(resolve => {
      this.http
      .post(`${environment.apiHostAddress}/message`, msg).pipe(
        retry(15),
      )
      .subscribe((response) => resolve(response as MessageDto));
    })
  }
}

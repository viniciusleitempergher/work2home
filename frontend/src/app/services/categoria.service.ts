import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private http:HttpClient) { }

  #cadastrarNome(nome:string) {
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/categoria`, {
        nome
      }).subscribe(response => resolve(response))
    })
  }

  #cadastrarImagem(categoria:any, imagem:File) {
    return new Promise(resolve => {
      const formData = new FormData();
      formData.append("imagem", imagem, imagem.name)
      
      this.http.post(`/categoria/${categoria.id}/imagem`, formData)
        .subscribe(response => resolve(response))
    })
  }

  cadastrar(nome: string, imagem: File): Promise<void> {
    return new Promise(async resolve => {
      let categoria:any = await this.#cadastrarNome(nome);

      await this.#cadastrarImagem(categoria, imagem)
    })
  }
}

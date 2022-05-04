import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Categoria } from 'src/models/Categoria';

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
    });
  }

  #cadastrarImagem(categoria:any, imagem:File) {
    return new Promise(resolve => {
      const formData = new FormData();
      formData.append("image", imagem, imagem.name)
      
      this.http.post(`${environment.apiHostAddress}/categoria/${categoria.id}/imagem`, formData)
        .subscribe(response => resolve(response))
    })
  }

  cadastrar(nome: string, imagem: File): Promise<Categoria> {
    return new Promise(async resolve => {
      let categoriaNome = await this.#cadastrarNome(nome);
      let categoriaImg = await this.#cadastrarImagem(categoriaNome, imagem);

      console.log(categoriaNome);
      console.log(categoriaImg);
      

      resolve(new Categoria());
    })
  }

  getAll():Promise<Categoria[]>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/categoria`)
        .subscribe(response => resolve(response as Categoria[]))
    })
  }

  deletar(id: number):Promise<void> {
    return new Promise(resolve => {
      this.http.delete(`${environment.apiHostAddress}/categoria/${id}`)
        .subscribe(response => resolve())
    })
  }

  cadastrarCategoria(id:number):Promise<void>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/prestador/categoria/${id}`,{})
      .subscribe(response => resolve())
    })
  }
  deletarCategoria(id: number):Promise<void> {
    console.log("teste"+id)
    return new Promise(resolve => {
      this.http.delete(`${environment.apiHostAddress}/prestador/categoria/${id}`)
        .subscribe(response => resolve())
    })
  }
}

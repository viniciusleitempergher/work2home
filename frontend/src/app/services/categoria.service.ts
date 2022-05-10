import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { retry } from 'rxjs';
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
      }).pipe(
        retry(15),
      ).subscribe(response => resolve(response))
    });
  }

  #cadastrarImagem(categoria:any, imagem:File): Promise<Categoria> {
    return new Promise(resolve => {
      const formData = new FormData();
      formData.append("image", imagem, imagem.name)
      
      this.http.post(`${environment.apiHostAddress}/categoria/${categoria.id}/imagem`, formData)
        .pipe(
          retry(15),
        ).subscribe(response => resolve(response as Categoria))
    })
  }

  cadastrar(nome: string, imagem: File): Promise<Categoria> {
    return new Promise(async resolve => {
      let categoriaNome = await this.#cadastrarNome(nome);
      let categoriaImg:Categoria = await this.#cadastrarImagem(categoriaNome, imagem);   

      resolve(categoriaImg);
    })
  }

  getAll():Promise<Categoria[]>{
    return new Promise(resolve => {
      this.http.get(`${environment.apiHostAddress}/categoria`).pipe(
         retry(15),
        )
        .subscribe(response => resolve(response as Categoria[]))
    })
  }

  deletar(id: number):Promise<void> {
    return new Promise(resolve => {
      this.http.delete(`${environment.apiHostAddress}/categoria/${id}`).pipe(
          retry(15),
        )
        .subscribe(response => resolve())
    })
  }

  cadastrarCategoria(id:number):Promise<void>{
    return new Promise(resolve => {
      this.http.post(`${environment.apiHostAddress}/prestador/categoria/${id}`,{}).pipe(
        retry(15),
      )
      .subscribe(response => resolve())
    })
  }
  deletarCategoria(id: number):Promise<void> {
    console.log("teste"+id)
    return new Promise(resolve => {
      this.http.delete(`${environment.apiHostAddress}/prestador/categoria/${id}`).pipe(
          retry(15),
        )
        .subscribe(response => resolve())
    })
  }
}

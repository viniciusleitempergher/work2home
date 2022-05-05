import { Avaliacao } from "./Avaliacao";
import { Categoria } from "./Categoria";
import { Cidade } from "./Cidade";
import { CidadePrestador } from "./CidadePrestador";

export class Prestador {
  id:number = 0;
  nome:string = '';
	email:string = '';
	telefone:string = '';
	mediaAvaliacao:number = 0;
	categorias:Categoria[] = [];
	cidades:CidadePrestador[] = [];
	avaliacoes: Avaliacao[] = [];
}

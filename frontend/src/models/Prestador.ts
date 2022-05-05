import { Avaliacao } from "./Avaliacao";
import { Categoria } from "./Categoria";
import { CidadePrestador } from "./CidadePrestador";

export class Prestador {
    nome:string = '';
	email:string = '';
	telefone:string = '';
	mediaAvaliacao:number = 0;
	imagemUrl:string='';
	categorias:Categoria[] = [];
	cidades:CidadePrestador[] = [];
	avaliacoes: Avaliacao[] = [];
}
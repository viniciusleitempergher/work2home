import { Avaliacao } from "./Avaliacao";
import { Categoria } from "./Categoria";
import { CidadePrestador } from "./CidadePrestador";

export class Prestador {
	id:number = 0;
	nome:string = '';
	email:string = '';
	telefone:string = '';
	cnpj:string = '';
	imagemUrl:string='';
	nomeFantasia:string = '';
	mediaAvaliacao:number = 0;
	categorias:Categoria[] = [];
	cidades:CidadePrestador[] = [];
	avaliacoes: Avaliacao[] = [];
}

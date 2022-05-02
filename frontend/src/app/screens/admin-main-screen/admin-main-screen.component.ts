import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { Usuario } from 'src/models/Usuario';

@Component({
  selector: 'app-admin-main-screen',
  templateUrl: './admin-main-screen.component.html',
  styleUrls: ['./admin-main-screen.component.css']
})
export class AdminMainScreenComponent implements OnInit {

  @Input() user:Usuario = {} as Usuario;

  constructor(private adminService:AdminService, private router: Router) { }

  ngOnInit(): void {

  }

  redirectCategoriasPage() {
    this.router.navigate(["cadastrar-categoria"]);
  }

   async relatorioUsuario(){
    const fileURL = URL.createObjectURL(await this.adminService.relatorioUsuario());
    window.open(fileURL);
  }
}

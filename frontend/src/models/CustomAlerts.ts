import Swal from "sweetalert2";

export class CustomAlerts {
    static primaryAlert = Swal.mixin({
      customClass: {
        title: 'titles',
        popup: 'popus',
        input: 'inputs',
        icon: 'icons',
        confirmButton: 'botaoDenuncia',
        cancelButton: 'botaoCancela'
      },
      buttonsStyling: false
    });
}
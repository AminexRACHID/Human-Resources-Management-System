import { Component } from '@angular/core';

@Component({
  selector: 'app-add-absence',
  templateUrl: './add-absence.component.html',
  styleUrl: './add-absence.component.css'
})
export class AddAbsenceComponent {
  public justificationValue: string = '';
  onJustifierChange() {
    alert(this.justificationValue.valueOf().toString());
    if (this.justificationValue == 'Oui'){
      console.log(this.justificationValue.toString());
    }
    if (this.justificationValue == 'Non'){
      console.log(this.justificationValue.toString());
    }
    if (this.justificationValue == ''){
      console.log(this.justificationValue.toString());
    }
  }
  onSubmit(){
    if (this.justificationValue == 'Oui'){
      console.log(this.justificationValue.toString());
    }
    if (this.justificationValue == 'Non'){
      console.log(this.justificationValue.toString());
    }
    if (this.justificationValue == ''){
      console.log(this.justificationValue.toString());
    }
  }
}

import { Component } from '@angular/core';
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";
import {OffreStageService} from "../../../services/stages/offre-stage.service";

@Component({
  selector: 'app-add-new-stage',
  templateUrl: './add-new-stage.component.html',
  styleUrl: './add-new-stage.component.css'
})
export class AddNewStageComponent {

  constructor(private alertService:AlertluncherService, private offreService:OffreStageService) {
  }

  stage: any = {
    title: '',
    type: '',
    duration: 0,
    remuneration: false,
    startDate: '',
    diploma: '',
    description : ''
  };

  validateDuration() {
    const durationInput = document.getElementById('duration');
    // @ts-ignore
    if (durationInput.value < 0) {
      // @ts-ignore
      durationInput.value = '';
    } else {
      // @ts-ignore
      durationInput.setCustomValidity('');
    }
  }

  onSubmit() {
    // console.log(this.stage);
    this.offreService.saveOffreStage(this.stage).subscribe(
      (response) => {
        // console.log('Employee created successfully', response);
        this.alertService.successAlertService("Nouvel offre de Stage enregistré avec succès.")
        // this.resetForm();
      },
      (error) => {
        // console.error('Error creating employee', error);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
      }
    );
  }
}

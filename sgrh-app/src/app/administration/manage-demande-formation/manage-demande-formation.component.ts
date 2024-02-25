import {Component, OnInit} from '@angular/core';
import {FormationService} from "../../services/formations/formation.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-manage-demande-formation',
  templateUrl: './manage-demande-formation.component.html',
  styleUrl: './manage-demande-formation.component.css'
})
export class ManageDemandeFormationComponent implements OnInit{

  trainingRequest:any;
  constructor(private formationService: FormationService, private alertService: AlertluncherService) {
  }
  ngOnInit(): void {
    this.getInfoRequestTraining();
  }

  getInfoRequestTraining(){
    this.formationService.getTrainingRequest()
      .subscribe({
        next: (data) => {
          this.trainingRequest = data;
        },
        error: (err) => {
          // console.log(err);
        }
      });
  }

  acceptRequest(id : number) {
    this.formationService.acceptTrainingRequest(id)
      .subscribe({
        next: (data) => {
          this.alertService.successAlertService("La demande de formation a été acceptée.");
          this.ngOnInit();
        },
        error: (err) => {
          // console.log(err);
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          this.ngOnInit();
        }
      });
  }

  rejectRequest(id : number) {
    this.formationService.rejectTrainingRequest(id)
      .subscribe({
        next: (data) => {
          this.alertService.successAlertService("La demande de formation a été refusée.");
          this.ngOnInit();
        },
        error: (err) => {
          // console.log(err);
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          this.ngOnInit();
        }
      });
  }
}

import {Component, OnInit} from '@angular/core';
import {OffreStageService} from "../../../services/stages/offre-stage.service";
import {StagiaireService} from "../../../services/stagiaires/stagiaire.service";
import {AuthService} from "../../../services/authentification/auth.service";
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-demande-attestation',
  templateUrl: './demande-attestation.component.html',
  styleUrl: './demande-attestation.component.css'
})
export class DemandeAttestationComponent implements OnInit{

  stages:any;
  userId:any;
  constructor(private stageService:OffreStageService, private stagiaireService:StagiaireService, private authService:AuthService, private alertService:AlertluncherService) {
  }

  ngOnInit(): void {
    this.stagiaireService.getCondidateByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        setTimeout(() => {
          this.getCondidateStageId(this.userId);
        }, 500);
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getCondidateStageId(id:any){
    this.stageService.getStageCondidate(id).subscribe(
      response => {
        this.stages = response;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  demandeAttestation(id : any) {

    const infos = {
      idStage : id,
      email : this.authService.username
    }

    this.stageService.sendAttestationEmail(infos).subscribe(
      response => {
        this.alertService.successAlertService("Félicitations, votre attestation de stage a été envoyée par email.");
      },
      error => {
        this.alertService.warningAlertService("La génération de votre attestation n'est possible qu'après la fin de votre stage.");
        // console.error('API Error:', error);
      }
    );
  }
}

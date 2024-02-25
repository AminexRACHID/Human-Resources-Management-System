import {Component, OnInit} from '@angular/core';
import {OffreStageService} from "../../../services/stages/offre-stage.service";
import {StagiaireService} from "../../../services/stagiaires/stagiaire.service";
import {AuthService} from "../../../services/authentification/auth.service";

@Component({
  selector: 'app-demande-attestation',
  templateUrl: './demande-attestation.component.html',
  styleUrl: './demande-attestation.component.css'
})
export class DemandeAttestationComponent implements OnInit{

  stages:any;
  userId:any;
  constructor(private stageService:OffreStageService, private stagiaireService:StagiaireService, private authService:AuthService) {
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

}

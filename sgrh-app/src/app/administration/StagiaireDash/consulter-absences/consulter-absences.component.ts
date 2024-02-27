import {AfterViewInit, Component, OnInit,ViewChild} from '@angular/core';
import {AbsenceService} from "../../../services/absences/absence.service";
import {AuthService} from "../../../services/authentification/auth.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {StagiaireService} from "../../../services/stagiaires/stagiaire.service";

@Component({
  selector: 'app-consulter-absences',
  templateUrl: './consulter-absences.component.html',
  styleUrl: './consulter-absences.component.css'
})
export class ConsulterAbsencesComponent implements OnInit,AfterViewInit{

  userId:any;
  absences:any;
  nbrAbsencejusti:any;
  nbrAbsenceNonjusti:any;
  constructor(private absenceService:AbsenceService, private authService: AuthService, private stagiaireService:StagiaireService) {
  }
  ngOnInit(): void {
    this.stagiaireService.getCondidateByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        // console.log(this.userId);
        setTimeout(() => {
          this.getAbsence(this.userId);
          this.getNbrAbsence(this.userId);
        }, 500);
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }
  ngAfterViewInit(): void {

  }
  getAbsence(id:number){
    this.absenceService.getAbsenceByStagiaireId(id).subscribe(
      response => {
        this.absences = response;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getNbrAbsence(id:any){
    this.absenceService.getNbrAbsenceStagiaire(id).subscribe(
      response => {
        this.nbrAbsenceNonjusti = response.nbrNonJustifier;
        this.nbrAbsencejusti = response.nbrJustifier;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

}

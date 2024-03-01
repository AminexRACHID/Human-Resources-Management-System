import {Component, OnInit} from '@angular/core';
import {FormationService} from "../../../services/formations/formation.service";
import {AuthService} from "../../../services/authentification/auth.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-employee-demande-formation',
  templateUrl: './employee-demande-formation.component.html',
  styleUrl: './employee-demande-formation.component.css'
})
export class EmployeeDemandeFormationComponent implements OnInit{

  userId : any;
  formations:any;
  search:any
  constructor(private formationService: FormationService, public authService: AuthService, private employeeService:EmployeeService, private alertService:AlertluncherService) {
  }
  ngOnInit(): void {
    this.getFormations();
    this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }

  getFormations(){
    this.formationService.getFormations().subscribe(
      response => {
        this.formations = response;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }

  submitToTrainig(format:any){
    let body = {
      employeeId : this.userId,
      formationId : format
    }
    // console.log(body)
    this.formationService.submitFormation(body).subscribe(
      response => {
        // console.log('message', response.message);
        this.alertService.successAlertService("Votre demande a été soumise avec succès.")
      },
      error => {
        this.alertService.errorAlertService("Vous avez déjà soumis une demande pour cette formation.");
      }
    );
  }

  searchFormations(){
    if(this.search === '' || this.search === undefined){
      this.getFormations();
    }else {
      console.log(this.search);
      this.formationService.searchFormation(this.search).subscribe(
        response => {
          this.formations = response
        },
        error => {
          // console.error(error);
          this.alertService.warningAlertService("Aucune formation portant ce nom n'a été trouvée.")
        }
      );
    }
  }



}

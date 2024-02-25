import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/authentification/auth.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {FormationService} from "../../../services/formations/formation.service";

@Component({
  selector: 'app-employee-status-demande-formation',
  templateUrl: './employee-status-demande-formation.component.html',
  styleUrl: './employee-status-demande-formation.component.css'
})
export class EmployeeStatusDemandeFormationComponent implements OnInit{
  userId : any;
  trainingRequest : any;
  statusPending:any;
  statusAccepted:any;
  statusRejected:any;
  constructor(private authService: AuthService, private employeeService:EmployeeService, private formationService:FormationService) {
  }
  ngOnInit(): void {
    this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        setTimeout(() => {
          this.getdemandeFormation(this.userId);
          this.getTrainingRequestNbr(this.userId);
        }, 500);
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getdemandeFormation(id:any){
    this.formationService.getdemandeFormationById(id).subscribe(
      response => {
        this.trainingRequest = response
      },
      error => {
        console.error(error);
      }
    );
  }

  getTrainingRequestNbr(id:number){
    this.formationService.getTrainingRequestNbr(id).subscribe(
      response => {
        this.statusAccepted = response.accepted;
        this.statusPending = response.pending;
        this.statusRejected = response.rejected;
      },
      error => {
        console.error(error);
      }
    );
  }

}

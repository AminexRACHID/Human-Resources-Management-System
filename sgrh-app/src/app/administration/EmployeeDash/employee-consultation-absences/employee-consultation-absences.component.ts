import {Component, OnInit} from '@angular/core';
import {AbsenceService} from "../../../services/absences/absence.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {AuthService} from "../../../services/authentification/auth.service";

@Component({
  selector: 'app-employee-consultation-absences',
  templateUrl: './employee-consultation-absences.component.html',
  styleUrl: './employee-consultation-absences.component.css'
})
export class EmployeeConsultationAbsencesComponent implements OnInit{

  userId:any;
  absences:any;
  nbrAbsencejusti:any;
  nbrAbsenceNonjusti:any;
  constructor(private absenceService:AbsenceService, private authService: AuthService, private employeeService:EmployeeService) {
  }
  ngOnInit(): void {
    this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        console.log(this.userId);
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

  getAbsence(id:number){
    this.absenceService.getAbsenceByEmployeeId(id).subscribe(
      response => {
        this.absences = response;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getNbrAbsence(id:any){
    this.absenceService.getNbrAbsenceEmployee(id).subscribe(
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

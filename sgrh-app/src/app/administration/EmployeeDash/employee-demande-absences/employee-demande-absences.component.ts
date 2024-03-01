import {Component, OnInit} from '@angular/core';
import {AbsenceService} from "../../../services/absences/absence.service";
import {AuthService} from "../../../services/authentification/auth.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-employee-demande-absences',
  templateUrl: './employee-demande-absences.component.html',
  styleUrl: './employee-demande-absences.component.css'
})
export class EmployeeDemandeAbsencesComponent implements OnInit{

  userId : any;
  fileToUpload: any=null;

  formData = {
    Duree: '',
    Motif: '',
    DateDebut: '',
    employee:true
  };

  constructor(private absenceService: AbsenceService, public authService: AuthService, private employeeService:EmployeeService, private alertService: AlertluncherService) {
  }

  ngOnInit(): void {
      this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
        response => {
          this.userId = response.id;

          // console.log(this.userId);
        },
        error => {
          // console.error('API Error:', error);
        }
      );
    }

  onSubmit(): void {
    const formData2 = new FormData();

    formData2.append('duration', this.formData.Duree);
    formData2.append('absenceNature', "Justifier");
    formData2.append('absenceDate', this.formData.DateDebut);
    formData2.append('colaborateurId', this.userId);
    formData2.append('employee', true.toString());
    formData2.append('justifie', this.formData.Motif);

    if (this.fileToUpload !== null) {
      formData2.append('justificationFile', this.fileToUpload);
    } else {
      // Create an empty file if fileToUpload is null
      const emptyFile = new File([], 'emptyFile.txt');
      formData2.append('justificationFile', emptyFile);
    }

    this.absenceService.sentDemandeAbsence(formData2).subscribe(
      response => {
        // console.log('API Response:', response);
        this.alertService.successAlertService("Votre demande d'absence a été envoyée.")
      },
      error => {
        // console.error('API Error:', error);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
      }
    );
  }



  // YOUNESS File input Name Change
  updateFileName(event: any): void {
    this.fileToUpload = event.target.files[0];
    const input = event.target;
    const fileName = input.files[0]?.name;

    if (fileName) {
      const label = input.nextElementSibling.querySelector('.form-file-text');
      label.textContent = fileName;
    }
  }
  //End File input Name Change
}

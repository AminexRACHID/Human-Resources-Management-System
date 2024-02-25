import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient} from "@angular/common/http";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-demande-absences',
  templateUrl: './demande-absences.component.html',
  styleUrl: './demande-absences.component.css'
})
export class DemandeAbsencesComponent implements OnInit {

  demandes:Array<any> = [];
  doc:any;
  constructor(private renderer: Renderer2, private el: ElementRef, private absenceService: AbsenceService, private http:HttpClient, private alertService:AlertluncherService) {}

  ngOnInit(): void {
    this.getEmployees();
  }

  getEmployees(){
    this.absenceService.getDemandesAbsences()
      .subscribe({
        next: (data) => {
          this.demandes = data;
        },
        error: (err) => {
          // console.log(err);
        }
      });
  }

  viewJustification(id: number): void {
    this.http.get(`http://localhost:8090/api/absences/${id}/demende/justification`, { responseType: 'arraybuffer' })
      .subscribe(
        (response: any) => {
          this.doc = response;
          // Create a Blob from the PDF data
          const blob = new Blob([response], { type: 'application/pdf' });

          // Create a URL for the Blob
          const pdfUrl = URL.createObjectURL(blob);

          // Open the PDF in a new window or tab
          window.open(pdfUrl, '_blank');
        },
        (error) => {
          // console.error('Error fetching document:', error);
        },

      );
  }

  rejectDemande(absenceId: any) {
    this.absenceService.rejectDemandeAbsence(absenceId).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Demande d'absence refusée.")
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error('Error:', err);
        this.ngOnInit();
      }
    });
  }

  accepterDemande(id: any) {
    this.absenceService.accepterDemandeAbsence(id).subscribe({
      next: (data) => {
        this.alertService.successAlertService("La demande d'absence a été acceptée.")
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error('Error:', err);
        this.ngOnInit();
      }
    });

  }



  saveBlobToFile(blob: Blob, filename: string) {
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = filename;

    // Trigger a click on the link element to initiate the download
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
}

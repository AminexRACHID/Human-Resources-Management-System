import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-demande-stage',
  templateUrl: './demande-stage.component.html',
  styleUrl: './demande-stage.component.css'
})
export class DemandeStageComponent implements OnInit{

  condidates:any;
  constructor(private renderer: Renderer2, private el: ElementRef, private http:HttpClient, private stagiaireService: StagiaireService, private alertService:AlertluncherService) {}

  ngOnInit(): void {
    this.getCondidatesPending();
  }

  getCondidatesPending(){
    this.stagiaireService.getCondidatesPending()
      .subscribe({
        next: (data) => {
          this.condidates = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  viewCV(stagiaireId: number): void {
    this.http.get(`http://localhost:8090/manage/stagiaire/cv/${stagiaireId}`, { responseType: 'arraybuffer' })
      .subscribe(
        (response: any) => {
          // Create a Blob from the PDF data
          const blob = new Blob([response], { type: 'application/pdf' });

          // Create a URL for the Blob
          const pdfUrl = URL.createObjectURL(blob);

          // Open the PDF in a new window or tab
          window.open(pdfUrl, '_blank');
        },
        (error) => {
          console.error('Error fetching document:', error);
        },

      );
  }

  AcceptCondidate(intershipApplyId: number) {
    this.stagiaireService.interviewCondidate(intershipApplyId).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Candidat accepté pour passer l'entretien.")
        this.ngOnInit();
      },
      error: (err) => {
        // console.error('Error:', err);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        this.ngOnInit();
      }
    });

  }


  rejectCondidate(intershipApplyId: number) {
    this.stagiaireService.rejectCondidate(intershipApplyId).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Demande de stage supprimée.")
        this.ngOnInit();
      },
      error: (err) => {
        // console.error('Error:', err);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        this.ngOnInit();
      }
    });
  }
}

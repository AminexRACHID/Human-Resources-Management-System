import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {AbsenceService} from "../../services/absences/absence.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-all-absence',
  templateUrl: './all-absence.component.html',
  styleUrl: './all-absence.component.css'
})
export class AllAbsenceComponent implements OnInit {

  demandes:Array<any> = [];
  constructor(private renderer: Renderer2, private el: ElementRef, private absenceService: AbsenceService, private http:HttpClient) {}

  ngOnInit(): void {
    this.getAbsence();
  }

  getAbsence(){
    this.absenceService.getAbsencesJustifier()
      .subscribe({
        next: (data) => {
          this.demandes = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  viewJustification(id: number): void {
    this.http.get(`http://localhost:8090/api/absences/${id}/absence/justification`, { responseType: 'arraybuffer' })
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



}

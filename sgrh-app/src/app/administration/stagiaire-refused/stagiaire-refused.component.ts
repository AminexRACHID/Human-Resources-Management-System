import {Component, ElementRef, Renderer2} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";

@Component({
  selector: 'app-stagiaire-refused',
  templateUrl: './stagiaire-refused.component.html',
  styleUrl: './stagiaire-refused.component.css'
})
export class StagiaireRefusedComponent {
  condidates: any;
  constructor(private renderer: Renderer2, private el: ElementRef, private http: HttpClient, private stagiaireService: StagiaireService) {
  }

  ngOnInit(): void {
    this.getCondidatesRefused();
  }

  getCondidatesRefused() {
    this.stagiaireService.getCondidatesRejected()
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
    this.http.get(`http://localhost:8090/manage/stagiaire/cv/${stagiaireId}`, {responseType: 'arraybuffer'})
      .subscribe(
        (response: any) => {
          // Create a Blob from the PDF data
          const blob = new Blob([response], {type: 'application/pdf'});

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

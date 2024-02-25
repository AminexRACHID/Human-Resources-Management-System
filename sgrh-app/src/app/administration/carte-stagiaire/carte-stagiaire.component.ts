import {Component, ElementRef, ViewChild, AfterViewInit, Renderer2, OnInit, input} from '@angular/core';
import html2canvas from 'html2canvas';
// import document from "$GLOBAL$";
// import HTMLInputElement from "$GLOBAL$";
// import HTMLDivElement from "$GLOBAL$";
// import FileReader from "$GLOBAL$";
// import HTMLElement from "$GLOBAL$";
import {HttpClient} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";
@Component({
  selector: 'app-carte-stagiaire',
  templateUrl: './carte-stagiaire.component.html',
  styleUrls: ['./carte-stagiaire.component.css']
})
export class CarteStagiaireComponent implements OnInit {

  condidates: any;


  constructor(private renderer: Renderer2, private el: ElementRef, private http: HttpClient, private stagiaireService: StagiaireService) {
  }

  ngOnInit(): void {
    this.getCondidatesAccepted();
  }

  getCondidatesAccepted() {
    this.stagiaireService.getCondidatesAccepted()
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
  previewImageModal() {
    const input = document.getElementById('imageUploadModal') as HTMLInputElement;
    // @ts-ignore
    const img = document.getElementById('ac-card-modal').querySelector('.ac-card-image') as HTMLDivElement;
    const reader = new FileReader();

    reader.onload = function (e) {
      // @ts-ignore
      img.style.backgroundImage = `url('${e.target.result}')`;
    };

    // @ts-ignore
    reader.readAsDataURL(input.files[0]);
  }

  openImageUploadModal() {
    // @ts-ignore
    document.getElementById('imageUploadModal').click();
  }



  downloadCardModal() {
    html2canvas(document.getElementById('ac-card-modal') as HTMLElement, {
      useCORS: true,
      logging: true,
      scale: 2 // Adjust the scale to improve the image quality
    }).then(function (canvas) {
      const link = document.createElement('a');
      document.body.appendChild(link);
      link.download = 'card.png';
      link.href = canvas.toDataURL('image/png');
      link.target = '_blank';
      link.click();
      document.body.removeChild(link);
    });
  }


}

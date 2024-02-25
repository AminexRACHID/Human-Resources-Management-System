import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-stagiaire-accepted',
  templateUrl: './stagiaire-accepted.component.html',
  styleUrl: './stagiaire-accepted.component.css'
})
export class StagiaireAcceptedComponent implements OnInit {


  public justificationValue: string = '';
  fileToUpload: any=null;
  absence: any = {
    colaborateurId : null,
    employee : false,
    absenceDate: null,
    duration: '',
    justifie: null,
    absenceNature: null
  };

  condidates: any;
  updatedStagiaire = {
    id: '',
    email: '',
    lastName: '',
    firstName: '',
    city: '',
    levelStudies: '',
    linkedin: '',
    cv: null,
    gender: '',
    phone: ''
  };

  constructor(private absenceService: AbsenceService, private renderer: Renderer2, private el: ElementRef, private http: HttpClient, private stagiaireService: StagiaireService, private alertService:AlertluncherService) {
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


  deleteCondidate(intershipApplyId: number) {
    this.stagiaireService.deleteCondidate(intershipApplyId).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Suppression du stagiaire effectuée.")
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        this.ngOnInit();
      }
    });
  }

  onSubmit(id:any) {
    console.error('updated',this.updatedStagiaire);
    this.stagiaireService.updateCondidate(id, this.updatedStagiaire).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Les données du stagiaire ont été modifiées avec succès.")
        // console.error('updated',data);
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error('Error:', err);
      }
    });
  }

  viewStagiaireDetails(id : any) {
    this.stagiaireService.getCondidate(id)
      .subscribe({
        next: (data) => {

          this.updatedStagiaire = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  changeFile(evt:any) {
    this.fileToUpload = evt.target.files[0];
    const input = evt.target;
    const fileName = input.files[0]?.name;

    if (fileName) {
      const label = input.nextElementSibling.querySelector('.form-file-text');
      label.textContent = fileName;
    }
  }

  onSubmit2(id:any){

    this.absence.colaborateurId = id
    if (this.justificationValue === "Non"){
      this.absence.justifie = null;
      this.fileToUpload = null;
      this.absence.absenceNature = 'Non Justifier';
    } else {
      this.absence.absenceNature = 'Justifier';
    }

    const formData = new FormData();
    Object.keys(this.absence).forEach((key) => {
      formData.append(key, this.absence[key]);
    });

    if (this.fileToUpload !== null) {
      formData.append('file', this.fileToUpload);
    } else {
      // Create an empty file if fileToUpload is null
      const emptyFile = new File([], 'emptyFile.txt');
      formData.append('file', emptyFile);
    }

    console.log(formData);

    this.absenceService.addAbsence(formData)
      .subscribe({
        next: (data) => {
          this.justificationValue = "Non"
          this.absence.absenceNature = null;
          this.absence.file = null;
          this.absence.justifie = null;
          this.absence.file = null;
          this.absence.colaborateurId = null
          this.absence.absenceDate = null;
          this.absence.duration = null;
          // console.log("absence add");
          this.alertService.successAlertService("L'absence a été assignée au stagiaire.")

        },
        error: (err) => {
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          // console.log(err);
        }
      });

  }
}

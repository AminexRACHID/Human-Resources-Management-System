import {AfterViewInit, Component, input, OnInit} from '@angular/core';
import {AuthService} from "../../../services/authentification/auth.service";
import {StagiaireService} from "../../../services/stagiaires/stagiaire.service";
import {OffreStageService} from "../../../services/stages/offre-stage.service";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";
import {data} from "jquery";

@Component({
  selector: 'app-postuler-stage',
  templateUrl: './postuler-stage.component.html',
  styleUrl: './postuler-stage.component.css'
})
export class PostulerStageComponent implements OnInit, AfterViewInit{

  userId:any;
  idStage:any = null;
  offers:any;
  offer:any=null;
  keyword:any;
  fileToUpload: any=null;
  stagiaireInfo:any;

  stagiaire: any = {
    city: '',
    levelStudies: '',
    linkedin: '',
    firstName: '',
    lastName: '',
    gender: '',
    phone: ''
  };

  constructor(private authService: AuthService, private stagiaireService:StagiaireService, private stageService:OffreStageService, private http:HttpClient, private alertService : AlertluncherService) {
  }

  ngOnInit(): void {

  }

  getOffers(){
    this.stageService.getOffers().subscribe(
      response => {
        this.offers = response;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }

  searchOffers() {
    // console.log(this.keyword);
    if(this.keyword == ""){
      this.getOffers();
    } else {
      this.stageService.searchOffer(this.keyword)
        .subscribe({
          next: (data) => {
            this.offers = data;
          },
          error: (err) => {
            console.log(err);
          }
        });
    }
  }


  // By Y.ZYADI File Upload Input Update File Name Function :
  updateFileName(event: any): void {
    this.fileToUpload = event.target.files[0];
    const input = event.target;
    const fileName = input.files[0]?.name;

    if (fileName) {
      const label = input.nextElementSibling.querySelector('.form-file-text');
      label.textContent = fileName;
    }
  }


  // END File Upload Input Update File Name Function :
  oppenModal(id :any) {
    this.stageService.getOfferById(id).subscribe(
      response => {
        this.offer = response;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }

  getStagiaireInfo(){
    this.stagiaireService.getCondidate(this.userId).subscribe(
      response => {
        this.stagiaireInfo = response;
        this.stagiaire = response;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }

  saveId(id:any) {
    this.idStage = id;
    this.getStagiaireInfo();
  }

  onSubmit() {
    const formData = new FormData();
    Object.keys(this.stagiaire).forEach((key) => {
      if ( key === "email"){

      }else {
        formData.append(key, this.stagiaire[key]);
      }
    });

    if (this.fileToUpload !== null) {
      formData.append('cv', this.fileToUpload);
    } else {
      // Create an empty file if fileToUpload is null
      const emptyFile = new File([], 'emptyFile.txt');
      formData.append('cv', emptyFile);
    }

    formData.append('id', this.userId);
    formData.append('stageId', this.idStage);
    formData.append('email', this.authService.username);
    console.log(formData.get("email"));
    console.log(this.stagiaire);

    this.http.post('http://localhost:8090/api/stage/applyIntership', formData)
      .subscribe(
        (response:any) => {
          this.alertService.successAlertService("Votre candidature pour ce stage a été soumise avec succès.");
          // console.log(response.message);
          // Optionally, reset the form after successful submission
          // this.resetForm();
          this.ngOnInit();
        },
        (error) => {
          this.alertService.errorAlertService("Vous avez déjà soumis une candidature pour ce stage.");
          this.ngOnInit();
          // console.error('Vous avez deja postuler a ce stage');
          // Handle error, display a message, etc.
        }
      );
  }

  ngAfterViewInit(): void {
    this.getOffers()
    this.stagiaireService.getCondidateByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
      },
      error => {
        // console.error('API Error:', error);
      }
    );
  }
}

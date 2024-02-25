import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-addplanformation',
  templateUrl: './addplanformation.component.html',
  styleUrl: './addplanformation.component.css'
})
export class AddplanformationComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient, private alertService:AlertluncherService) {
    this.form = this.fb.group({
      name: [''],
      date: [''],
      responsable: [''],
      formations: this.fb.array([]),
    });
  }

  ngOnInit() {
    // this.addFormation(); // Add an initial empty formation
  }

  get formations() {
    return (this.form.get('formations') as FormArray).controls;
  }

  addFormation() {
    const formation = this.fb.group({
      formationname: [''],
      formationobjectif: [''],
      collaborateurs: [''],
      formationdate: [''],
      formationduree: [''],
    });
    (this.form.get('formations') as FormArray).push(formation);
  }

  removeFormation(index: number) {
    (this.form.get('formations') as FormArray).removeAt(index);
  }

  onSubmit() {
    const formData = this.form.value;

    // Adjust the data structure if needed
    const apiData = {
      nom: formData.name,
      date: formData.date,
      responsable: formData.responsable,
      formations: formData.formations.map((formation: { formationname: any; formationobjectif: any; collaborateurs: any; formationdate: any; formationduree: any; }) => ({
        nomFormation: formation.formationname,
        objectif: formation.formationobjectif,
        collaborateurs: formation.collaborateurs,
        date: formation.formationdate,
        duree: formation.formationduree,
      })),
    };

    // Now you can send the `apiData` to your API using HttpClient
    this.http.post('http://localhost:8090/api/miaad/plans-formation', apiData).subscribe(
      response => {
        // Handle success
        this.alertService.successAlertService("Le plan de formation a été créé avec succès.")
        // console.log("success");
        // console.log(response);
      },
      error => {
        // Handle error
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error(error);
      }
    );
  }
}

import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {FormationService} from "../../services/formations/formation.service";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-manageplanformation',
  templateUrl: './manageplanformation.component.html',
  styleUrl: './manageplanformation.component.css'
})
export class ManageplanformationComponent implements OnInit{

  plansFormations:any;


  form:FormGroup=this.fb.group({
    nom:[''],
    date:[''],
    responsable:[''],
    formations:this.fb.array([
      this.fb.group({
        id:[],
        nomFormation:[''],
        objectif:[''],
        collaborateurs:[''],
        date:[''],
        duree:[''],
      })
    ]),
  });
  constructor(private fb:FormBuilder,private formationService: FormationService, private http:HttpClient, private alertService:AlertluncherService) {}

  ngOnInit() {
    this.loadPlansFormations();
    console.log(this.form);
  }

  loadPlansFormations() {

    this.formationService.getPlansFormations().subscribe(plans => {
      this.plansFormations = plans;

    });
  }

  populateForm(data: any): void {
    // Assuming your data structure is an object
    this.form.patchValue({
      nom: data.nom,
      date: data.date,
      responsable: data.responsable,
    });

    const formationsArray = this.form.get('formations') as FormArray;
    formationsArray.clear(); // Clear existing formations before adding new ones

    data.formations.forEach((formation: any) => {
      formationsArray.push(
        this.fb.group({
          id: formation.id,
          nomFormation: formation.nomFormation,
          objectif: formation.objectif,
          collaborateurs: formation.collaborateurs,
          date: formation.date,
          duree: formation.duree,
        })
      );
    });
  }


  gatPlan(id : any){
    this.formationService.getPlanById(id).subscribe(
      (data) => {
        this.form.reset();
        this.populateForm(data);
      },
      (error) => {
        this.ngOnInit();
        console.error('Error deleting employee:', error);
      }
    );
  }

  mapFormationsArray(formations: any[]): any[] {
    return formations.map(formation => {
      return this.fb.group({
        id: formation.id,
        nomFormation: formation.nomFormation,
        objectif: formation.objectif,
        collaborateurs: formation.collaborateurs,
        date: formation.date,
        duree: formation.duree,
      });
    });
  }

  deletePlan(id: any): void {
    console.log(id)
    this.formationService.deletePlansFormations(id).subscribe(
      (data) => {
        this.alertService.successAlertService("Suppression du programme de formation réalisée.")
        this.ngOnInit();
      },
      (error) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        this.ngOnInit();
        // console.error('Error deleting employee:', error);
      }
    );

  }


  get formations(){
    return this.form.get('formations') as FormArray;
  }
  addFormation(): void {
    const formationsArray = this.form.get('formations') as FormArray;
    formationsArray.push(this.fb.group({
      id: '',
      nomFormation: '',
      objectif: '',
      collaborateurs: '',
      date: '',
      duree: '',
    }));
  }

  // Remove a formation group from the form
  removeFormation(index: number): void {
    const formationsArray = this.form.get('formations') as FormArray;
    formationsArray.removeAt(index);
  }
  onSubmit(id:any){
    const updatedData = this.form.value;
    console.log(updatedData)

    // Replace 'yourApiEndpoint' with the actual API endpoint
    this.http.put(`http://localhost:8090/api/miaad/plans-formation/${id}`, updatedData)
      .subscribe((response) => {
          this.alertService.successAlertService("Le plan de formation a été modifié avec succès.")
          this.ngOnInit();
      // console.log('Data updated successfully:', response);
    },(error) => {
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          this.ngOnInit();
          // console.error('Error deleting employee:', error);
        }
        );
    // alert(JSON.stringify(this.form.value,null,2));
  }



}

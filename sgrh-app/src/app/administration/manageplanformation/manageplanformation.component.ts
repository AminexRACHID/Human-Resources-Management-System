import {AfterViewInit, Component, OnInit,ViewChild} from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {FormationService} from "../../services/formations/formation.service";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
@Component({
  selector: 'app-manageplanformation',
  templateUrl: './manageplanformation.component.html',
  styleUrl: './manageplanformation.component.css'
})
export class ManageplanformationComponent implements OnInit,AfterViewInit{

  plansFormations:any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;

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
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.initializeTable();
    } , 500);
  }
  public initializeTable(): void {
    const tableElement: any = $(this.table.nativeElement);
    const dataTable = tableElement.DataTable();
    $('[id^="dt-search"]').hide();
    $('label[for^="dt-search"]').hide();
    // Add search input with Bootstrap classes
    const searchContainer = $('<div class="input-group mb-3"></div>');
    searchContainer.appendTo($('.dt-search'));

    const searchInput = $('<input type="search" class="form-control" placeholder="Search..."/>')
      .appendTo(searchContainer)
      .css({ 'max-width': '300px' });

    // Add Font Awesome icon inside search input
    const icon =
      $('<button type="button" class="btn btn-primary" data-mdb-ripple-init>\n' +
        '  <i class="fa fa-search"></i>\n' +
        '</button>');
    icon.appendTo(searchContainer);

    const downloadCsvButton =
      $('<button type="button" class="btn btn-success">Download CSV</button>');
    downloadCsvButton.appendTo(searchContainer);

    // Add Bootstrap classes to DataTable elements
    tableElement.addClass('table table-hover');
    $('.dataTables_length, .dataTables_filter, .dataTables_info, .dataTables_paginate').addClass('d-flex');

    // Adjust DataTable styles
    $('.dataTables_filter').css('margin-left', 'auto');
    $('.dt-paging-button').addClass('btn btn-outline-secondary');
    $('.dt-info').addClass('mt-2 mb-2');
    $('.dataTable').css('width', '100%')
      .addClass('text-center');

    dataTable.on('draw', function () {
      // Reapply your custom styles here
      $('.dt-paging-button').addClass('btn btn-outline-secondary');
    });
    // Apply the search to all columns
    searchInput.on('keyup change', function () {
      // @ts-ignore
      dataTable.search(this.value).draw();
      $('.dt-paging-button').addClass('btn btn-outline-secondary');
    });
    downloadCsvButton.on('click', function () {
      // Get the DataTable data
      const allData = dataTable.rows().data().toArray();

      // Specify the columns you want to include in the CSV
      const columnsToInclude = [
        { index: 0, name: 'Nom' },
        { index: 1, name: 'Date-De-Création' },
        { index: 2, name: 'Responsable' },
      ];
      // Replace with the actual column indices you want

      // Map the data to include only the specified columns
      // @ts-ignore
      const filteredData = allData.map(row => columnsToInclude.reduce((acc, column) => {
        // @ts-ignore
        acc[column.name] = row[column.index];
        return acc;
      }, {}));

      // Extract header names from columnsToInclude
      const headerNames = columnsToInclude.map(column => column.name);

      // Convert filtered data and header names to CSV format
      // @ts-ignore
      const csvContent = Papa.unparse([headerNames, ...filteredData.map(row => headerNames.map(header => row[header]))], { header: false });

      // Create a Blob with the CSV content
      const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });

      // Use FileSaver to save the Blob as a file
      FileSaver.saveAs(blob, 'Les-Plans.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    this.loadPlansFormations();
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
    console.log(id.value)
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
  removeFormation(index: number, id :any): void {
    const formationsArray = this.form.get('formations') as FormArray;
    formationsArray.removeAt(index);
    // console.log(id.value);
    this.formationService.deleteFormationById(id.value).subscribe(
      (data) => {
        this.alertService.successAlertService("Suppression du formation réalisée.");
      },
      (error) => {
        // this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
      }
    );
  }
  onSubmit(id:any){
    const updatedData = this.form.value;
    // console.log(updatedData);

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

  deleteFormation(idFormation :any){

  }



}

import {AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
import {AuthService} from "../../services/authentification/auth.service";
import {OffreStageService} from "../../services/stages/offre-stage.service";
@Component({
  selector: 'app-stagiaire-accepted',
  templateUrl: './stagiaire-accepted.component.html',
  styleUrl: './stagiaire-accepted.component.css'
})
export class StagiaireAcceptedComponent implements OnInit,AfterViewInit {

  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
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

  constructor(private authService : AuthService, private absenceService: AbsenceService, private renderer: Renderer2, private el: ElementRef, private http: HttpClient, private stagiaireService: StagiaireService, private alertService:AlertluncherService, private stageService: OffreStageService) {
  }

  ngOnInit(): void {
    this.getCondidatesAccepted();
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
        { index: 1, name: 'Prenom' },
        { index: 2, name: 'Email' },
        { index: 3, name: 'Ville' },
        { index: 4, name: "Phone" },
        { index: 5, name: 'Niveau' },
        { index: 6, name: 'Gender' },
        { index: 7, name: 'Stage' },
        { index: 8, name: 'Type' },
        { index: 9, name: "Remuneration" },
        { index: 10, name: 'Date-Debut-Stage' }
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
      FileSaver.saveAs(blob, 'Les-Stagiaires-Accepter.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
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

  generateAttestation(id : any, email :any) {

    const infos = {
      idStage: id,
      email : email
    }

    console.log(infos);
    this.stageService.generateAttestation(infos).subscribe(
      response => {
        const blob = new Blob([response], { type: 'application/pdf' });

        // Create a URL for the Blob
        const pdfUrl = URL.createObjectURL(blob);

        // Open the PDF in a new window or tab
        window.open(pdfUrl, '_blank');
      },
      error => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
        console.log(error)
      }
    );

  }
}

import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FormationService} from "../../services/formations/formation.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
@Component({
  selector: 'app-manage-demande-formation',
  templateUrl: './manage-demande-formation.component.html',
  styleUrl: './manage-demande-formation.component.css'
})
export class ManageDemandeFormationComponent implements OnInit,AfterViewInit{

  trainingRequest:any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
  constructor(private formationService: FormationService, private alertService: AlertluncherService) {
  }
  ngOnInit(): void {
    this.getInfoRequestTraining();
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
        { index: 2, name: 'CIN' },
        { index: 3, name: 'Date Demande' },
        { index: 4, name: "Formation" },
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
      FileSaver.saveAs(blob, 'Demandes-Formations.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    this.getInfoRequestTraining();
  }
  getInfoRequestTraining(){
    this.formationService.getTrainingRequest()
      .subscribe({
        next: (data) => {
          this.trainingRequest = data;
        },
        error: (err) => {
          // console.log(err);
        }
      });
  }

  acceptRequest(id : number) {
    this.formationService.acceptTrainingRequest(id)
      .subscribe({
        next: (data) => {
          this.alertService.successAlertService("La demande de formation a été acceptée.");
          this.ngOnInit();
        },
        error: (err) => {
          // console.log(err);
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          this.ngOnInit();
        }
      });
  }

  rejectRequest(id : number) {
    this.formationService.rejectTrainingRequest(id)
      .subscribe({
        next: (data) => {
          this.alertService.successAlertService("La demande de formation a été refusée.");
          this.ngOnInit();
        },
        error: (err) => {
          // console.log(err);
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
          this.ngOnInit();
        }
      });
  }
}

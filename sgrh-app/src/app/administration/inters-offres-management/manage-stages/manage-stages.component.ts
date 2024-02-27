import {AfterViewInit, Component, OnInit,ViewChild} from '@angular/core';
import {AlertluncherService} from "../../../services/alerts/alertluncher.service";
import {OffreStageService} from "../../../services/stages/offre-stage.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
@Component({
  selector: 'app-manage-stages',
  templateUrl: './manage-stages.component.html',
  styleUrl: './manage-stages.component.css'
})
export class ManageStagesComponent implements OnInit,AfterViewInit{

  offers : any;
  stage : any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
  constructor(private alertService:AlertluncherService, private offeStageService:OffreStageService) {
  }

  ngOnInit(): void {
    this.getOffers();
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
        { index: 0, name: 'Titre' },
        { index: 1, name: 'Type' },
        { index: 2, name: 'Date Debut' },
        { index: 3, name: 'Durre' },
        { index: 4, name: "Niveau" },
        { index: 5, name: 'Rémunération' },
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
      FileSaver.saveAs(blob, 'List-Des-Stages.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    this.getOffers();
  }
  getOffers(){
    this.offeStageService.getOffers().subscribe(
      (response) => {
        // console.log('Employee created successfully', response);
        // this.resetForm();
        this.offers = response;
      },
      (error) => {
        // console.error('Error creating employee', error);
        }
    );
  }

  validateDuration() {
    const durationInput = document.getElementById('duration');
    // @ts-ignore
    if (durationInput.value < 0) {
      // @ts-ignore
      durationInput.value = '';
    } else {
      // @ts-ignore
      durationInput.setCustomValidity('');
    }
  }


  onSubmit(id : any) {
    console.log(this.stage);
    this.offeStageService.updateOffreStage(this.stage, id).subscribe(
      (response) => {
        // console.log('Employee created successfully', response);
        this.ngOnInit();
        this.alertService.successAlertService("Les modifications du stage ont été appliquées avec succès.");
      },
      (error) => {
        // console.error('Error creating employee', error);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
      }
    );
  }

  getdata(o: any) {
    this.stage = o;
  }

  deleteStage(id : any) {
    this.offeStageService.deleteStageById(id).subscribe(
      (response) => {
        // console.log('Employee created successfully', response);
        this.ngOnInit();
        this.alertService.successAlertService("La suppression du stage a été effectuée correctement..");
      },
      (error) => {
        // console.error('Error creating employee', error);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
      }
    );
  }
}

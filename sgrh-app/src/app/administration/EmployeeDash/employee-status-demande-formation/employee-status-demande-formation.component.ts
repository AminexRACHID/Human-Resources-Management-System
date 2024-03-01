import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AuthService} from "../../../services/authentification/auth.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {FormationService} from "../../../services/formations/formation.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
@Component({
  selector: 'app-employee-status-demande-formation',
  templateUrl: './employee-status-demande-formation.component.html',
  styleUrl: './employee-status-demande-formation.component.css'
})
export class EmployeeStatusDemandeFormationComponent implements OnInit,AfterViewInit{
  userId : any;
  trainingRequest : any;
  statusPending:any;
  statusAccepted:any;
  statusRejected:any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
  constructor(public authService: AuthService, private employeeService:EmployeeService, private formationService:FormationService) {
  }
  ngOnInit(): void {
    this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        setTimeout(() => {
          this.getdemandeFormation(this.userId);
          this.getTrainingRequestNbr(this.userId);
        }, 500);
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }
  ngAfterViewInit(): void {
    setTimeout(() => {
      this.initializeTable();
    } , 1000);
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
        { index: 0, name: 'Nom-Prenom' },
        { index: 1, name: 'Date demande' },
        { index: 2, name: 'Formation' },
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
      FileSaver.saveAs(blob, 'Mes-Demandes-Des-Formations.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
  }

  getdemandeFormation(id:any){
    this.formationService.getdemandeFormationById(id).subscribe(
      response => {
        this.trainingRequest = response
      },
      error => {
        console.error(error);
      }
    );
  }

  getTrainingRequestNbr(id:number){
    this.formationService.getTrainingRequestNbr(id).subscribe(
      response => {
        this.statusAccepted = response.accepted;
        this.statusPending = response.pending;
        this.statusRejected = response.rejected;
      },
      error => {
        console.error(error);
      }
    );
  }

}

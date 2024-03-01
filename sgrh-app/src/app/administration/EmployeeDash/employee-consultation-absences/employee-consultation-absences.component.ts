import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {AbsenceService} from "../../../services/absences/absence.service";
import {EmployeeService} from "../../../services/employees/employee.service";
import {AuthService} from "../../../services/authentification/auth.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
import {ChatService} from "../../../services/messagerie/chat.service";
@Component({
  selector: 'app-employee-consultation-absences',
  templateUrl: './employee-consultation-absences.component.html',
  styleUrl: './employee-consultation-absences.component.css'
})
export class EmployeeConsultationAbsencesComponent implements OnInit,AfterViewInit{

  userId:any;
  absences:any;
  nbrAbsencejusti:any;
  nbrAbsenceNonjusti:any;
  nickname:any;
  fullname:any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
  constructor(private absenceService:AbsenceService, public authService: AuthService, private employeeService:EmployeeService, private websocketService:ChatService) {
  }
  ngOnInit(): void {
    this.employeeService.getEmployeeByEmail(this.authService.username).subscribe(
      response => {
        this.userId = response.id;
        console.log(this.userId);
        this.getName();
        setTimeout(() => {
          this.websocketService.connect(this.nickname, this.fullname);
          this.getAbsence(this.userId);
          this.getNbrAbsence(this.userId);
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
        { index: 0, name: 'Motif' },
        { index: 1, name: 'Durée' },
        { index: 2, name: 'Date d\'absence' },
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
      FileSaver.saveAs(blob, 'Mes-Absences.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    //this.getAbsence(this.userId);
  }

  getAbsence(id:number){
    this.absenceService.getAbsenceByEmployeeId(id).subscribe(
      response => {
        this.absences = response;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getNbrAbsence(id:any){
    this.absenceService.getNbrAbsenceEmployee(id).subscribe(
      response => {
        this.nbrAbsenceNonjusti = response.nbrNonJustifier;
        this.nbrAbsencejusti = response.nbrJustifier;
      },
      error => {
        console.error('API Error:', error);
      }
    );
  }

  getName(){
      this.employeeService.getEmployeeByEmail(this.authService.username).subscribe({
        next: (data) => {
          this.fullname = data.firstName +' '+ data.lastName;
          this.nickname = data.firstName + data.lastName;
        },
        error: (err) => {
        }
      });
    }

}

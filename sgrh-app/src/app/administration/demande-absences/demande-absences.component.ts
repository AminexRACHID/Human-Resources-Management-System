import {AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient} from "@angular/common/http";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
@Component({
  selector: 'app-demande-absences',
  templateUrl: './demande-absences.component.html',
  styleUrl: './demande-absences.component.css'
})
export class DemandeAbsencesComponent implements OnInit,AfterViewInit {

  demandes:Array<any> = [];
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;
  doc:any;
  constructor(private renderer: Renderer2, private el: ElementRef, private absenceService: AbsenceService, private http:HttpClient, private alertService:AlertluncherService) {}

  ngOnInit(): void {
    this.getEmployees();
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
        { index: 2, name: 'Date d\'absence' },
        { index: 3, name: 'Durée' },
        { index: 4, name: "Nature-Justification" }
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
      FileSaver.saveAs(blob, 'Demandes-Absences.csv');
    });
    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    this.getEmployees();
  }

  getEmployees(){
    this.absenceService.getDemandesAbsences()
      .subscribe({
        next: (data) => {
          this.demandes = data;
        },
        error: (err) => {
          // console.log(err);
        }
      });
  }

  viewJustification(id: number): void {
    this.http.get(`http://localhost:8090/api/absences/${id}/demende/justification`, { responseType: 'arraybuffer' })
      .subscribe(
        (response: any) => {
          this.doc = response;
          // Create a Blob from the PDF data
          const blob = new Blob([response], { type: 'application/pdf' });

          // Create a URL for the Blob
          const pdfUrl = URL.createObjectURL(blob);

          // Open the PDF in a new window or tab
          window.open(pdfUrl, '_blank');
        },
        (error) => {
          // console.error('Error fetching document:', error);
        },

      );
  }

  rejectDemande(absenceId: any) {
    this.absenceService.rejectDemandeAbsence(absenceId).subscribe({
      next: (data) => {
        this.alertService.successAlertService("Demande d'absence refusée.")
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error('Error:', err);
        this.ngOnInit();
      }
    });
  }

  accepterDemande(id: any) {
    this.absenceService.accepterDemandeAbsence(id).subscribe({
      next: (data) => {
        this.alertService.successAlertService("La demande d'absence a été acceptée.")
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        // console.error('Error:', err);
        this.ngOnInit();
      }
    });

  }



  saveBlobToFile(blob: Blob, filename: string) {
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = filename;

    // Trigger a click on the link element to initiate the download
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }
}

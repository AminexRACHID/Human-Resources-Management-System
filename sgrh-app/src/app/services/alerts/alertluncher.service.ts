import {AfterViewInit, ElementRef, Injectable, OnInit, ViewChild} from '@angular/core';
// import 'datatables.net';
import $ from 'jquery';

import {NgToastService} from "ng-angular-popup";
@Injectable({
  providedIn: 'root'
})
export class AlertluncherService implements OnInit, AfterViewInit {

  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;

  constructor(private toastService: NgToastService) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
  }

  successAlertService(content: string) {
    // @ts-ignore
    this.toastService.success({detail:"SUCCESS",summary:content,duration:'5000', position:'topCenter'});}
  infoAlertService(content: string) {
    // @ts-ignore
    this.toastService.info({detail:"INFO",summary:content,duration:'9000', position:'topCenter'});}
  warningAlertService(content: string) {
    // @ts-ignore
    this.toastService.warning({detail:"WARNING",summary:content,duration:'5000', position:'topCenter'});}
  errorAlertService(content: string) {
    // @ts-ignore
    this.toastService.error({detail:"ERROR",summary:content,duration:'5000', position:'topCenter'});
  }
  // public initializeTable(): void {
  //   // // @ts-ignore
  //   // const tableElement: any = $(this.table.nativeElement);
  //   // const dataTable = tableElement.DataTable();
  //   //
  //   // // Add search input with Font Awesome icon
  //   // const searchInput = $('<input type="search" class="form-control" placeholder="Search..."/>')
  //   //   .appendTo($('.dataTables_filter'));
  //   //
  //   // // Add Font Awesome icon
  //   // $('<i class="fa fa-search"></i>').prependTo(searchInput.parent());
  //   //
  //   // // Apply the search to all columns
  //   // searchInput.on('keyup change', function () {
  //   //   // @ts-ignore
  //   //   dataTable.search(this.value).draw();
  //   // });
  //   const tableElement: any = $(this.table.nativeElement);
  //   const dataTable = tableElement.DataTable();
  //
  //   // Add search input with Bootstrap classes
  //   const searchContainer = $('<div class="input-group mb-3"></div>');
  //   searchContainer.appendTo($('.dt-search'));
  //
  //   const searchInput = $('<input type="search" class="form-control" placeholder="Search..."/>')
  //     .appendTo(searchContainer)
  //     .css({ 'max-width': '300px' });
  //
  //   // Add Font Awesome icon inside search input
  //   const icon =
  //     $('<button type="button" class="btn btn-primary" data-mdb-ripple-init>\n' +
  //       '  <i class="fa fa-search"></i>\n' +
  //       '</button>');
  //   icon.appendTo(searchContainer);
  //
  //   const downloadCsvButton =
  //     $('<button type="button" class="btn btn-success">Download CSV</button>');
  //   downloadCsvButton.appendTo(searchContainer);
  //
  //   // Add Bootstrap classes to DataTable elements
  //   tableElement.addClass('table table-hover');
  //   $('.dataTables_length, .dataTables_filter, .dataTables_info, .dataTables_paginate').addClass('d-flex');
  //
  //   // Adjust DataTable styles
  //   $('.dataTables_filter').css('margin-left', 'auto');
  //   $('.dt-paging-button').addClass('btn btn-outline-secondary');
  //   $('.dt-info').addClass('mt-2 mb-2');
  //   $('.dataTable').css('width', '100%')
  //     .addClass('text-center');
  //
  //   dataTable.on('draw', function () {
  //     // Reapply your custom styles here
  //     $('.dt-paging-button').addClass('btn btn-outline-secondary');
  //     // ... (other styles)
  //   });
  //   // Apply the search to all columns
  //   searchInput.on('keyup change', function () {
  //     // @ts-ignore
  //     dataTable.search(this.value).draw();
  //     $('.dt-paging-button').addClass('btn btn-outline-secondary');
  //   });
  //   // Download all the table :
  //   // downloadCsvButton.on('click', function () {
  //   //   // Get the DataTable data
  //   //   const data = dataTable.rows().data().toArray();
  //   //
  //   //   // Convert data to CSV format
  //   //   const csvContent = Papa.unparse(data, { header: true });
  //   //
  //   //   // Create a Blob with the CSV content
  //   //   const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
  //   //
  //   //   // Use FileSaver to save the Blob as a file
  //   //   FileSaver.saveAs(blob, 'table_data.csv');
  //   // });
  //   //
  //   downloadCsvButton.on('click', function () {
  //     // Get the DataTable data
  //     const allData = dataTable.rows().data().toArray();
  //
  //     // Specify the columns you want to include in the CSV
  //     const columnsToInclude = [0, 1, 2 ,3 ,4 ,5]; // Replace with the actual column indices you want
  //
  //     // Map the data to include only the specified columns
  //     // @ts-ignore
  //     const filteredData = allData.map(row => columnsToInclude.map(index => row[index]));
  //
  //     // Convert filtered data to CSV format
  //     const csvContent = Papa.unparse(filteredData, { header: true });
  //
  //     // Create a Blob with the CSV content
  //     const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
  //
  //     // Use FileSaver to save the Blob as a file
  //     FileSaver.saveAs(blob, 'table_data.csv');
  //   });
  //   // Hide the element with id dt-search-0 and its label
  //   $('#dt-search-0, label[for="dt-search-0"]').hide();
  //   // Hide the element with id dt-search-0 and its label
  //   $('.dt-length').hide();
  // }
}


import {AfterViewInit, Component, OnInit,ViewChild} from '@angular/core';
import {FormationService} from "../../services/formations/formation.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';

@Component({
  selector: 'app-consulter-employee-formation',
  templateUrl: './consulter-employee-formation.component.html',
  styleUrl: './consulter-employee-formation.component.css'
})
export class ConsulterEmployeeFormationComponent implements OnInit,AfterViewInit{

  infos:any;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;

  constructor(private formationService: FormationService) {
  }
  ngOnInit(): void {
    this.getFormationByCollaborators();
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

    // Hide the element with id dt-search-0 and its label
    $('.dt-length').hide();
    this.getFormationByCollaborators();
  }


  getFormationByCollaborators(){
    this.formationService.getFormationsByCollaborateurs()
      .subscribe({
        next: (data) => {
          this.infos = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

}

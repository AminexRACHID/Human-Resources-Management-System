import {AfterViewInit, Component, ElementRef, HostBinding, OnInit, Renderer2, ViewChild} from '@angular/core';
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import 'datatables.net';
import $ from 'jquery';
import * as FileSaver from 'file-saver';
import * as Papa from 'papaparse';
import {display} from "html2canvas/dist/types/css/property-descriptors/display";

@Component({
  selector: 'app-allemployees',
  templateUrl: './allemployees.component.html',
  styleUrl: './allemployees.component.css'
})
export class AllemployeesComponent implements OnInit,AfterViewInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;
  // @ts-ignore
  @ViewChild('dataTable', { static: false }) table: ElementRef;

  public justificationValue: string = '';
  fileToUpload: any=null;
  absence: any = {
    colaborateurId : null,
    employee : true,
    absenceDate: null,
    duration: '',
    justifie: null,
    absenceNature: null
  };
  emplyees:Array<any> = [];
  updatedEmployee = {
    id: '',
    email: '',
    lastName: '',
    firstName: '',
    cin: '',
    birthDay: '',
    hireDate: '',
    service: '',
    post: '',
    role: ''
  };
  @ViewChild('exampleModal') modal: any;


  constructor(private absenceService:AbsenceService, private renderer: Renderer2, private el: ElementRef,private employee:EmployeeService, private http:HttpClient, private alertService:AlertluncherService) {}


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
        { index: 2, name: 'CIN' },
        { index: 3, name: 'Email' },
        { index: 4, name: 'Birth Day' },
        { index: 5, name: 'Service' },
        { index: 6, name: 'Poste' },
        { index: 7, name: 'Hire Date' },
        // Add more columns as needed
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
      FileSaver.saveAs(blob, 'Tous-Les-Employees.csv');
    });
    // Hide the element with id dt-search-0 and its label

    $('.dt-length').hide();
    this.getEmployees();
  }
  getEmployees(){
    this.employee.getEmployees()
      .subscribe({
        next: (data) => {
          this.emplyees = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

  viewEmployeeDetails(e: any): void {
    this.employee.getEmployeeById(e)
      .subscribe({
        next: (data) => {

          this.updatedEmployee = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }


  viewDocument(employeeId: number): void {
    this.http.get(`http://localhost:8090/api/employees/${employeeId}/documents`, { responseType: 'arraybuffer' })
      .subscribe(
        (response: any) => {
          // Create a Blob from the PDF data
          const blob = new Blob([response], { type: 'application/pdf' });

          // Create a URL for the Blob
          const pdfUrl = URL.createObjectURL(blob);

          // Open the PDF in a new window or tab
          window.open(pdfUrl, '_blank');
        },
        (error) => {
            this.alertService.warningAlertService("Employee ne contient aucun document");
                    },

      );
  }

  generateAttestation(employ:any) {
    const requestBody = {
      "nomPrenom": employ.lastName+' '+employ.firstName,
      "cin": employ.cin,
      "poste": employ.post,
      "dateOccupation": employ.hireDate,
      "nomEtablissement": "fsm"
    };


    this.http.post("http://localhost:8090/api/administration/generateAttestation", requestBody, { responseType: 'arraybuffer' })
      .subscribe(
        (response) => {
          const blob = new Blob([response], { type: 'application/pdf' });

          // Create a URL for the Blob
          const pdfUrl = URL.createObjectURL(blob);

          // Open the PDF in a new window or tab
          window.open(pdfUrl, '_blank');
        },
        (error) => {
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
        }
      );
  }

  onSubmit(id:any) {
    this.http.put(`http://localhost:8090/api/employees/${id}`, this.updatedEmployee).subscribe({
      next: (data) => {
        // console.log('Employee updated successfully.');
        this.alertService.successAlertService("Les informations de l'employé ont été modifiées avec succès.")
        this.closeModal();
        this.ngOnInit();
      },
      error: (err) => {
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
      }
    });
  }

  deleteEmployee(id: any): void {

    this.http.delete(`http://localhost:8090/api/employees/${id}`).subscribe(
      (data) => {
        this.alertService.successAlertService("Employé supprimé avec succès.");
        this.closeModal();
        this.ngOnInit();
      },
      (error) => {
        // console.error('Error deleting employee:', error);
        this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.");
      }
    );

  }

  closeModal() {
    const modalElement = this.modal.nativeElement;

    // Use Renderer2 to manipulate the DOM
    this.renderer.removeClass(modalElement, 'show');
    this.renderer.setStyle(modalElement, 'display', 'none');

    // Remove modal-open class from body to handle overlay
    this.renderer.removeClass(document.body, 'modal-open');
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

    // this.absence.justifie = this.;

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
          this.alertService.successAlertService("L'absence a été attribuée à l'employé.")
        },
        error: (err) => {
          this.alertService.errorAlertService("Une erreur est survenue. Veuillez réessayer ultérieurement.")
        }
      });

  }

}

import {Component, ElementRef, HostBinding, OnInit, Renderer2, ViewChild} from '@angular/core';
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AbsenceService} from "../../services/absences/absence.service";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-allemployees',
  templateUrl: './allemployees.component.html',
  styleUrl: './allemployees.component.css'
})
export class AllemployeesComponent implements OnInit {
  @HostBinding('attr.ngSkipHydration') skipHydration = true;

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
          console.error('Error fetching document:', error);
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

    console.log(requestBody);

    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        // Add any additional headers if needed
      })
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
          console.error('Error in POST request', error);
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

import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {EmployeeService} from "../../services/employees/employee.service";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-addemployee',
  templateUrl: './addemployee.component.html',
  styleUrl: './addemployee.component.css'
})
export class AddemployeeComponent implements OnInit {

  employeeForm: FormGroup;
  employees: any = {
    firstName: '',
    lastName: '',
    email: '',
    cin: '',
    service: '',
    post: '',
    role: '',
    birthDay: '',
    hireDate: '',
  };

  fileToUpload: any=null;


  constructor(private fb: FormBuilder,private renderer: Renderer2, private el: ElementRef,private employee:EmployeeService, private http:HttpClient, private alertService:AlertluncherService) {
    this.employeeForm = this.fb.group({
      firstName: [''],
      lastName: [''],
      email: [''],
      birthDay: [''],
      cin: [''],
      hireDate: [''],
      service: [''],
      post: [''],
      role: [''],
    });
  }

  ngOnInit(): void {
  }


  onSubmit() {
    const formData = new FormData();
    Object.keys(this.employees).forEach((key) => {
      formData.append(key, this.employees[key]);
    });

    if (this.fileToUpload !== null) {
      formData.append('file', this.fileToUpload);
    } else {
      // Create an empty file if fileToUpload is null
      const emptyFile = new File([], '');
      formData.append('file', emptyFile);
    }

    // Assuming you have a service method to handle the HTTP request to your backend
    this.http.post('http://localhost:8090/api/employees', formData)
      .subscribe(
        (response) => {
          // console.log('Employee created successfully', response);
          this.alertService.successAlertService("Nouvel employé enregistré avec succès.")
          // this.resetForm();
        },
        (error) => {
          // console.error('Error creating employee', error);
          this.alertService.errorAlertService("Adresse E-mail ou CIN déjà utilisée. Veuillez choisir une adresse E-mail ou CIN différente.")
        }
      );
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
}

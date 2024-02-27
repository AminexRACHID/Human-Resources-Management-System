import {AfterViewInit, Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/authentification/auth.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";
import {StagiaireService} from "../../services/stagiaires/stagiaire.service";
import {EmployeeService} from "../../services/employees/employee.service";
import {data, type} from "jquery";
import {ChatService} from "../../services/messagerie/chat.service";
declare var feather: any;
@Component({
  selector: 'app-adminheader',
  templateUrl: './adminheader.component.html',
  styleUrl: './adminheader.component.css'
})
export class AdminheaderComponent implements AfterViewInit, OnInit {
  changePasswordForm: FormGroup;
  nicknamee : any;
  fullName : any;
  firstNameH : any;
  lastNameH : any;
  constructor(private renderer: Renderer2, private el: ElementRef, private fb: FormBuilder, public authService : AuthService, private router:Router, private http:HttpClient, private alertService:AlertluncherService, private stagiaireService:StagiaireService, private employeeService:EmployeeService, private websocketService:ChatService) {
    this.changePasswordForm = this.fb.group({
      oldpassword: ['', Validators.required],
      newpassword: ['', [Validators.pattern(/^(?=.*\d)(?=.*[a-zA-Z])(?=.*[@#$%^&*!.])([0-9a-zA-Z@#$%^&*!.]|[^ ]){8,}$/)
      ]
      ],
      passwordconfirmation: ['', Validators.required]
    });
  }
  getName(){
    if (this.authService.roles === "Stagiaire"){
      this.stagiaireService.getCondidateByEmail(this.authService.username).subscribe({
        next: (data) => {
          this.firstNameH = data.firstName.charAt(0).toUpperCase() + data.firstName.slice(1);
          this.lastNameH =  data.lastName.toUpperCase();

        },
        error: (err) => {
        }
      });
    } else {
      this.employeeService.getEmployeeByEmail(this.authService.username).subscribe({
        next: (data) => {
          this.firstNameH = data.firstName.charAt(0).toUpperCase() + data.firstName.slice(1);
          this.lastNameH = data.lastName.toUpperCase();
          this.fullName = data.firstName +' '+ data.lastName;
          this.nicknamee = data.firstName + data.lastName;
        },
        error: (err) => {
        }
      });
    }
  }

  ngOnInit(): void {
    this.getName();
  }
  ngAfterViewInit() {
    feather.replace(); // Replace feather icons
    const togglePassword = (passwordInput: any, togglePasswordIcon: any) => {
      const type = passwordInput.type === 'password' ? 'text' : 'password';
      passwordInput.type = type;
      let i = 1;
      const iconFeather = this.getSVGIcon(type);
      togglePasswordIcon.innerHTML = iconFeather;

    };

    const passwordInputold: any = this.el.nativeElement.querySelector('#oldpassword');
    const togglePasswordIconold: any = this.el.nativeElement.querySelector('#toggleOldPasswordIcon');

    const passwordInput1: any = this.el.nativeElement.querySelector('#newpassword');
    const togglePasswordIcon1: any = this.el.nativeElement.querySelector('#toggleNewPasswordIcon');

    const passwordInput2: any = this.el.nativeElement.querySelector('#passwordconfirmation');
    const togglePasswordIcon2: any = this.el.nativeElement.querySelector('#togglePasswordConfirmIcon');


    const verifyPasswordsMatch = () => {
      const password1 = passwordInput1.value;
      const password2 = passwordInput2.value;

      if (password1 !== password2) {
        // Passwords do not match, add red border class
        this.renderer.addClass(passwordInput1, 'border-danger');
        this.renderer.addClass(passwordInput2, 'border-danger');
      } else {
        // Passwords match, add green border class
        this.renderer.addClass(passwordInput1, 'border-success');
        this.renderer.addClass(passwordInput2, 'border-success');
      }
    };

    const resetBorderClasses = () => {
      this.renderer.removeClass(passwordInput1, 'border-success');
      this.renderer.removeClass(passwordInput1, 'border-danger');
      this.renderer.removeClass(passwordInput2, 'border-success');
      this.renderer.removeClass(passwordInput2, 'border-danger');
    };

    this.renderer.listen(togglePasswordIcon1, 'click', () => {
      togglePassword(passwordInput1, togglePasswordIcon1);

    });

    this.renderer.listen(togglePasswordIcon2, 'click', () => {
      togglePassword(passwordInput2, togglePasswordIcon2);
    });

    this.renderer.listen(passwordInput1, 'input', () => {
      resetBorderClasses();
      verifyPasswordsMatch();

    });

    this.renderer.listen(passwordInput2, 'input', () => {
      resetBorderClasses();
      verifyPasswordsMatch();
    });
    this.renderer.listen(togglePasswordIconold, 'click', () => {
      togglePassword(passwordInputold, togglePasswordIconold);
    });

  }
  getSVGIcon(type: string): string {
    return type === 'password'
      ? `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"></path><circle cx="12" cy="12" r="3"></circle></svg>`
      : `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-eye-off"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"></path><line x1="1" y1="1" x2="23" y2="23"></line></svg>`;
  }
  verifyPasswordsMatch = () => {
    // @ts-ignore
    const password1 = this.changePasswordForm.get('newpassword').value;
    // @ts-ignore
    const password2 = this.changePasswordForm.get('passwordconfirmation').value;

    return password1 === password2;
  };
  toggleDropdown(): void {
    // Toggle your main dropdown here
    const mainDropdown = document.getElementById('mainDropdown') as HTMLElement;
    mainDropdown.style.display = (mainDropdown.style.display === 'none' || mainDropdown.style.display === '') ? 'block' : 'none';
  }
  toggleUserDropdown(): void {
    // Toggle your user dropdown here
    const userDropdown = document.getElementById('userDropdown') as HTMLElement;
    userDropdown.style.display = (userDropdown.style.display === 'none' || userDropdown.style.display === '') ? 'block' : 'none';
  }
  toggleNotificationsDropdown(): void {
    // Toggle your notifications dropdown here
    const notificationsDropdown = document.getElementById('notificationsDropdown') as HTMLElement;
    notificationsDropdown.style.display = (notificationsDropdown.style.display === 'none' || notificationsDropdown.style.display === '') ? 'block' : 'none';
  }
  onSubmit() {
    let body = {
      "oldPassword": this.changePasswordForm.get('oldpassword')?.value,
      "newPassword": this.changePasswordForm.get('newpassword')?.value
    }
    if (this.changePasswordForm.valid && this.verifyPasswordsMatch()) {
      this.http.post(`http://localhost:8090/test/employee/change-password/${this.authService.username}`, body)
        .subscribe({
          next: (data) => {
            this.alertService.successAlertService("Votre mot de passe a été changé avec succès.");
          },
          error: (err) => {
            // console.error('Error:', err);
            this.alertService.errorAlertService("Le mot de passe actuel n'est pas correct.")
          }
        });

      // Form is valid, proceed with submission
      // alert('Form submitted');
      // console.log('Form submitted:', this.changePasswordForm.value);
    } else {
      this.alertService.errorAlertService("Votre mot de passe doit contenir des lettres, des chiffres, et un caractère spécial, et doit avoir une longueur supérieure à 8 caractères.")
    }
  }

  handleLogout() {
    if (this.authService.roles !== "Stagiaire"){
      this.websocketService.disconnect(this.nicknamee, this.fullName);
    }
    // console.log(this.fullName+"   "+this.nicknamee);
    setTimeout(() => {
      this.authService.logout();
    },500);
  }


}

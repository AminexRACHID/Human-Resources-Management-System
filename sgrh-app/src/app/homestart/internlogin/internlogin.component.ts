import {AfterViewInit, Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/authentification/auth.service";
import {Router} from "@angular/router";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-internlogin',
  templateUrl: './internlogin.component.html',
  styleUrl: './internlogin.component.css'
})
export class InternloginComponent implements AfterViewInit, OnInit {
  loginForm: FormGroup;

  constructor(private toastService: AlertluncherService,private renderer: Renderer2, private el: ElementRef, private fb: FormBuilder, private authService:AuthService, private router : Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      // Perform your login logic here
      // console.log('Form submitted:', this.loginForm.value);

      let username = this.loginForm.value.email;
      let pwd = this.loginForm.value.password;

      this.authService.login(username,pwd).subscribe({
        next: data => {
          this.authService.loadProfile(data);
          setTimeout(() => {
            if (this.authService.roles === 'Admin'){
              this.router.navigateByUrl("admin/all-employees")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            } else if(this.authService.roles === 'Employee'){
              this.router.navigateByUrl("employee/consulter-absences")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            }else if(this.authService.roles === 'Stagiaire'){
              this.router.navigateByUrl("stagiaire/postuler-stage")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            }else {
              this.router.navigateByUrl("interns/login")
            }
          }, 500);
        },
        error : err => {
          console.log(err);
          this.toastService.errorAlertService(err.error);
        }
      })

    } else {
      // Form is invalid, handle accordingly
      console.log('Form is invalid');
      this.toastService.infoAlertService('Form is invalid');
    }
  }

  ngAfterViewInit() {
    const passwordInput = this.el.nativeElement.querySelector('#password');
    const togglePasswordIcon = this.el.nativeElement.querySelector('#togglePasswordIcon');

    this.renderer.listen(togglePasswordIcon, 'click', () => {
      const type = passwordInput.type === 'password' ? 'text' : 'password';
      passwordInput.type = type;

      // Change the eye icon based on the password visibility
      if (type === 'password') {
        this.renderer.removeClass(togglePasswordIcon, 'fa-eye-slash');
        this.renderer.addClass(togglePasswordIcon, 'fa-eye');
      } else {
        this.renderer.removeClass(togglePasswordIcon, 'fa-eye');
        this.renderer.addClass(togglePasswordIcon, 'fa-eye-slash');
      }
    });
  }

    ngOnInit(): void {
    this.authService.deleteToken();
    }
}

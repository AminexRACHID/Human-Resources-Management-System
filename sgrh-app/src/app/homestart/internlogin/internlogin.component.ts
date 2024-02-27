import {AfterViewInit, Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
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
  emailrecovery : any;
  recoveryForm : FormGroup;

  constructor(private toastService: AlertluncherService,private renderer: Renderer2, private el: ElementRef, private fb: FormBuilder, private authService:AuthService, private router : Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.recoveryForm = this.fb.group({
      emailrecoverpass: ['', [Validators.required, Validators.email]],
    });

  }

  onSubmit() {
    // if (this.loginForm.valid) {
      // Perform your login logic here
      // console.log('Form submitted:', this.loginForm.value);

      let username = this.loginForm.value.email;
      let pwd = this.loginForm.value.password;

      this.authService.login(username,pwd).subscribe({
        next: data => {
          this.authService.loadProfile(data);
          setTimeout(() => {
            this.authService.roles;
            if (this.authService.roles === 'Admin'){
              this.router.navigateByUrl("employee/consulter-absences")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            } else if(this.authService.roles === 'Employee'){
              this.router.navigateByUrl("employee/consulter-absences")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            }else if(this.authService.roles === 'Stagiaire'){
              this.router.navigateByUrl("stagiaire/postuler-stage")
              this.toastService.successAlertService("Authentification réussie. Bienvenue!");
            }else {
              this.toastService.infoAlertService('Form is invalid');
            }
          }, 1000);
        },
        error : err => {
          // console.log(err);
          this.toastService.errorAlertService(err.error);
        }
      })

    // } else {
    //   // Form is invalid, handle accordingly
    //   console.log('Form is invalid');
    //   this.toastService.infoAlertService('Form is invalid');
    // }
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
      // this.loginForm = new FormGroup({
      //   firstName: new FormControl()
      // });
    }
  // ---------------------Recover Password------------------------
  openModal(): void {
    const modal: HTMLElement | null = document.getElementById('myModal')
    const chatcontainer: HTMLElement | null = document.getElementById('hero');
    if (modal) {
      modal.style.display = 'block';
    }
    if (modal && chatcontainer) {
      modal.style.display = 'block';
      chatcontainer.classList.add('blurred');
    }
  }

  closeModal(): void {
    const modal: HTMLElement | null = document.getElementById('myModal');
    const chatContainer: HTMLElement | null = document.getElementById('hero');
    if (modal) {
      modal.style.display = 'none';
    }


    if (modal && chatContainer) {
      modal.style.display = 'none';
      chatContainer.classList.remove('blurred');
    }
  }
  RecoverPassword(): void {
    if (this.isValidEmail(this.emailrecovery)) {
      this.authService.recoverPassword(this.emailrecovery).subscribe({
        next: data => {
          this.toastService.infoAlertService("Un courriel a été envoyé pour récupérer votre mot de passe.");
          this.closeModal();
        },
        error: err => {
          this.toastService.errorAlertService("L'e-mail spécifié n'existe pas.");
        }
      });
    } else {
      this.toastService.errorAlertService("Veuillez fournir une adresse e-mail valide.");
    }
  }

  isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  }

  // ------------------------------------Recover Password------------------------
}

import {AfterViewInit, Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
// @ts-ignore
// import alert from "$GLOBAL$";
import {AuthService} from "../../services/authentification/auth.service";
import {Router} from "@angular/router";
import {AlertluncherService} from "../../services/alerts/alertluncher.service";

@Component({
  selector: 'app-interncreateaccount',
  templateUrl: './interncreateaccount.component.html',
  styleUrl: './interncreateaccount.component.css'
})
export class InterncreateaccountComponent implements AfterViewInit, OnInit {
  registrationForm: FormGroup;

  constructor(private renderer: Renderer2, private el: ElementRef, private fb: FormBuilder, private authService: AuthService, private router : Router, private alertService: AlertluncherService) {
    this.registrationForm = this.fb.group({
      lastName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*')]],
      firstName: ['', [Validators.required, Validators.pattern('[a-zA-Z ]*')]],
      phone: ['', [Validators.required, Validators.pattern('[0-9]*')]],
      gender: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.pattern(/^(?=.*\d)(?=.*[a-zA-Z])(?=.*[@#$%^&*!.])([0-9a-zA-Z@#$%^&*!.]|[^ ]){8,}$/)
      ]
  ],
      passwordconfirmation: ['', Validators.required]
    });
  }
  ngAfterViewInit() {
    const passwordInput1: any = this.el.nativeElement.querySelector('#password');
    // const button: any = this.el.nativeElement.querySelector('#buttonValidation');
    // button.classList.remove("btn-get-started");
    const togglePasswordIcon1: any = this.el.nativeElement.querySelector('#togglePasswordIcon');

    const passwordInput2: any = this.el.nativeElement.querySelector('#passwordconfirmation');
    const togglePasswordIcon2: any = this.el.nativeElement.querySelector('#togglePasswordConfirmIcon');

    const togglePassword = (passwordInput: any, togglePasswordIcon: any) => {
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
    };

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

    // Reset border classes when focusing on input fields
        const resetBorderClasses = () => {
        this.renderer.removeClass(passwordInput1, 'border-success');
        this.renderer.removeClass(passwordInput1,  'border-danger');
        this.renderer.removeClass(passwordInput2, 'border-success');
        this.renderer.removeClass(passwordInput2, 'border-danger');
      };

    this.renderer.listen(togglePasswordIcon1, 'click', () => {
      togglePassword(passwordInput1, togglePasswordIcon1);
      verifyPasswordsMatch();
    });

    this.renderer.listen(togglePasswordIcon2, 'click', () => {
      togglePassword(passwordInput2, togglePasswordIcon2);
      verifyPasswordsMatch();
    });

    // Listen for input changes and verify passwords match
    this.renderer.listen(passwordInput1, 'input', () => {
      resetBorderClasses();
      verifyPasswordsMatch();
    });

    this.renderer.listen(passwordInput2, 'input', () => {
      resetBorderClasses();
      verifyPasswordsMatch();
    });
  }
  verifyPasswordsMatch = () => {
    // @ts-ignore
    const password1 = this.registrationForm.get('password').value;
    // @ts-ignore
    const password2 = this.registrationForm.get('passwordconfirmation').value;

    return password1 === password2;
  };

  onSubmit() {
    if (this.registrationForm.valid && this.verifyPasswordsMatch()) {
      // Form is valid, proceed with submission
      // alert('Form submitted');
      // console.log('Form submitted:', this.registrationForm.value);

      const formData = this.registrationForm.value;

      this.authService.create(formData).subscribe(
        (response) => {
          this.router.navigateByUrl("interns/login")
          this.alertService.infoAlertService("Un courriel sera envoyé à votre boîte de messagerie pour confirmer votre compte")
          // this.resetForm();
        },
        (error) => {
          console.error(error);
          this.alertService.errorAlertService("Adresse e-mail déjà utilisée. Veuillez choisir une adresse e-mail différente.")

        }
      );


    } else {
      this.alertService.warningAlertService("Vous devez remplir tous les champs.")
    }
  }

  ngOnInit(): void {
    this.authService.deleteToken();
  }
}

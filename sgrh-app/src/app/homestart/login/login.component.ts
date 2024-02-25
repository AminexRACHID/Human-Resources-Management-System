import {AfterViewInit, Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/authentification/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements AfterViewInit, OnInit {
  loginForm: FormGroup;

  constructor(private renderer: Renderer2, private el: ElementRef, private fb: FormBuilder, private router: Router,private authService:AuthService) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      // Perform your login logic here
      console.log('Form submitted:', this.loginForm.value);
    } else {
      // Form is invalid, handle accordingly
      console.log('Form is invalid');
    }
  }
  // navigateToAllEmployees() {
  //   this.router.navigate(['']).then(r => console.log(r));
  // }
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

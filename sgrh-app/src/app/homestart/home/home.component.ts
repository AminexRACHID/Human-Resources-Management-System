import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../services/authentification/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  constructor(private authService:AuthService) {
  }
  ngOnInit(): void {
    this.authService.deleteToken();
  }

}

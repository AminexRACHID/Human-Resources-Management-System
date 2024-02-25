import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {AbsenceService} from "../../services/absences/absence.service";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-absences-non-justifier',
  templateUrl: './absences-non-justifier.component.html',
  styleUrl: './absences-non-justifier.component.css'
})
export class AbsencesNonJustifierComponent implements OnInit {

  demandes:Array<any> = [];
  constructor(private absenceService: AbsenceService) {}

  ngOnInit(): void {
    this.getAbsences();
  }

  getAbsences(){
    this.absenceService.getAbsencesNonJustifier()
      .subscribe({
        next: (data) => {
          this.demandes = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
}

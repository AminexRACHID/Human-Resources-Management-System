import {Component, OnInit} from '@angular/core';
import {FormationService} from "../../services/formations/formation.service";

@Component({
  selector: 'app-consulter-employee-formation',
  templateUrl: './consulter-employee-formation.component.html',
  styleUrl: './consulter-employee-formation.component.css'
})
export class ConsulterEmployeeFormationComponent implements OnInit{

  infos:any;

  constructor(private formationService: FormationService) {
  }
  ngOnInit(): void {
    this.getFormationByCollaborators();
  }

  getFormationByCollaborators(){
    this.formationService.getFormationsByCollaborateurs()
      .subscribe({
        next: (data) => {
          this.infos = data;
        },
        error: (err) => {
          console.log(err);
        }
      });
  }

}

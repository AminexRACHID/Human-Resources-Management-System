import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./homestart/home/home.component";
import {LoginComponent} from "./homestart/login/login.component";
import {InterncreateaccountComponent} from "./homestart/interncreateaccount/interncreateaccount.component";
import {InternloginComponent} from "./homestart/internlogin/internlogin.component";
import {InternoffersComponent} from "./homestart/internoffers/internoffers.component";
import {InternsDetailsModalComponent} from "./homestart/interns-details-modal/interns-details-modal.component";
import {AllemployeesComponent} from "./administration/allemployees/allemployees.component";
import {AddemployeeComponent} from "./administration/addemployee/addemployee.component";
import {DemandeStageComponent} from "./administration/demande-stage/demande-stage.component";
import {EntretienStagiaireComponent} from "./administration/entretien-stagiaire/entretien-stagiaire.component";
import {AddplanformationComponent} from "./administration/addplanformation/addplanformation.component";
import {ManageplanformationComponent} from "./administration/manageplanformation/manageplanformation.component";
import {StagiaireAcceptedComponent} from "./administration/stagiaire-accepted/stagiaire-accepted.component";
import {StagiaireRefusedComponent} from "./administration/stagiaire-refused/stagiaire-refused.component";
import {AddAbsenceComponent} from "./administration/add-absence/add-absence.component";
import {AllAbsenceComponent} from "./administration/all-absence/all-absence.component";
import {StagiaireAtestationComponent} from "./administration/stagiaire-atestation/stagiaire-atestation.component";
import {CarteStagiaireComponent} from "./administration/carte-stagiaire/carte-stagiaire.component";
import {DemandeAbsencesComponent} from "./administration/demande-absences/demande-absences.component";
import {AbsencesNonJustifierComponent} from "./administration/absences-non-justifier/absences-non-justifier.component";
import {
  ManageDemandeFormationComponent
} from "./administration/manage-demande-formation/manage-demande-formation.component";
import {
  ConsulterEmployeeFormationComponent
} from "./administration/consulter-employee-formation/consulter-employee-formation.component";
import {AuthenticationGuard} from "./guards/authentication.guard";
import {NotAuthorizedComponent} from "./not-authorized/not-authorized.component";
import {AdminHomeComponent} from "./administration/admin-home/admin-home.component";
import {AuthorizationGuard} from "./guards/authorization.guard";
import {
  DemandeAttestationComponent
} from "./administration/StagiaireDash/demande-attestation/demande-attestation.component";
import {PostulerStageComponent} from "./administration/StagiaireDash/postuler-stage/postuler-stage.component";
import {
  ConsulterAbsencesComponent
} from "./administration/StagiaireDash/consulter-absences/consulter-absences.component";
import {
  EmployeeDemandeAbsencesComponent
} from "./administration/EmployeeDash/employee-demande-absences/employee-demande-absences.component";
import {
  EmployeeConsultationAbsencesComponent
} from "./administration/EmployeeDash/employee-consultation-absences/employee-consultation-absences.component";
import {
  EmployeeDemandeFormationComponent
} from "./administration/EmployeeDash/employee-demande-formation/employee-demande-formation.component";
import {
  EmployeeStatusDemandeFormationComponent
} from "./administration/EmployeeDash/employee-status-demande-formation/employee-status-demande-formation.component";
import {NotFoundPageComponent} from "./not-found-page/not-found-page.component";
import {AuthoriStagiaireGuard} from "./guards/authori-stagiaire.guard";
import {AuthoriEmployeeGuard} from "./guards/authori-employee.guard";
import {AddNewStageComponent} from "./administration/inters-offres-management/add-new-stage/add-new-stage.component";
import {ManageStagesComponent} from "./administration/inters-offres-management/manage-stages/manage-stages.component";
import {ChatappComponent} from "./chat/chatapp/chatapp.component";



const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent},
  { path: 'interns/create-account', component: InterncreateaccountComponent },
  { path: 'interns/login', component: InternloginComponent},
  { path: 'interns/offers', component: InternoffersComponent},
  { path: 'interns/offers-details', component: InternsDetailsModalComponent},
  { path: 'notAuthorized', component: NotAuthorizedComponent},
  { path: 'admin', component: AllemployeesComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"}},
  { path: 'admin/add-employee', component: AddemployeeComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/all-employees', component: AllemployeesComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/demandes-Stage', component: DemandeStageComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/entretien', component: EntretienStagiaireComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/add-plan', component: AddplanformationComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/manage-plan', component: ManageplanformationComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/stagiaire', component: StagiaireAcceptedComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/demandes-refusees', component: StagiaireRefusedComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/ajouter-absence', component: AddAbsenceComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/absences-justifier', component: AllAbsenceComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/demandes-attestation', component: StagiaireAtestationComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/stagiaire-carte', component: CarteStagiaireComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/demandes-absences', component: DemandeAbsencesComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/absences-non-justifier', component: AbsencesNonJustifierComponent , canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"}},
  { path: 'admin/manage-demande-formation', component: ManageDemandeFormationComponent , canActivate : [AuthenticationGuard], data : {role:"Admin"}},
  { path: 'admin/employee-formation', component: ConsulterEmployeeFormationComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'stagiaire/demande-attestation', component: DemandeAttestationComponent, canActivate : [AuthenticationGuard,AuthoriStagiaireGuard], data : {role:"Stagiaire"} },
  { path: 'stagiaire/postuler-stage', component: PostulerStageComponent , canActivate : [AuthenticationGuard,AuthoriStagiaireGuard], data : {role:"Stagiaire"}},
  { path: 'stagiaire/consulter-absences', component: ConsulterAbsencesComponent , canActivate : [AuthenticationGuard,AuthoriStagiaireGuard], data : {role:"Stagiaire"}},
  { path: 'employee/demande-absence', component: EmployeeDemandeAbsencesComponent, canActivate : [AuthenticationGuard,AuthoriEmployeeGuard], data : {role:"Employee"}},
  { path: 'employee/consulter-absences', component: EmployeeConsultationAbsencesComponent, canActivate : [AuthenticationGuard,AuthoriEmployeeGuard], data : {role:"Employee"}},
  { path: 'employee/demande-formation', component: EmployeeDemandeFormationComponent, canActivate : [AuthenticationGuard,AuthoriEmployeeGuard], data : {role:"Employee"}},
  { path: 'employee/status-demande-formation', component: EmployeeStatusDemandeFormationComponent, canActivate : [AuthenticationGuard,AuthoriEmployeeGuard], data : {role:"Employee"}},
  //Not Yet Finshed :
  { path: 'admin/stages/add-stage', component: AddNewStageComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'admin/stages/manage-stages', component: ManageStagesComponent, canActivate : [AuthenticationGuard,AuthorizationGuard], data : {role:"Admin"} },
  { path: 'chat', component:ChatappComponent, canActivate : [AuthenticationGuard]},
  //Wild Card Route for 404 request
  { path: '**', pathMatch: 'full',
    component: NotFoundPageComponent },



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

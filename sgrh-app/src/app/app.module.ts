import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './homestart/home/home.component';
import { HeaderComponent } from './homestart/header/header.component';
import { FooterComponent } from './homestart/footer/footer.component';
import { LoginComponent } from './homestart/login/login.component';
import { InterncreateaccountComponent } from './homestart/interncreateaccount/interncreateaccount.component';
import { InternloginComponent } from './homestart/internlogin/internlogin.component';
import { InternoffersComponent } from './homestart/internoffers/internoffers.component';
import { HomestartassetsComponent } from './homestart/homestartassets/homestartassets.component';
import {RouterLink, RouterLinkActive} from "@angular/router";
import { InternsDetailsModalComponent } from './homestart/interns-details-modal/interns-details-modal.component';
import { AddAbsenceComponent } from './administration/add-absence/add-absence.component';
import { AddemployeeComponent } from './administration/addemployee/addemployee.component';
import { AddplanformationComponent } from './administration/addplanformation/addplanformation.component';
import { AdminfooterComponent } from './administration/adminfooter/adminfooter.component';
import { AdminheaderComponent } from './administration/adminheader/adminheader.component';
import { AllAbsenceComponent } from './administration/all-absence/all-absence.component';
import { AllemployeesComponent } from './administration/allemployees/allemployees.component';
import { DemandeStageComponent } from './administration/demande-stage/demande-stage.component';
import { EntretienStagiaireComponent } from './administration/entretien-stagiaire/entretien-stagiaire.component';
import { ManageplanformationComponent } from './administration/manageplanformation/manageplanformation.component';
import { StagiaireAcceptedComponent } from './administration/stagiaire-accepted/stagiaire-accepted.component';
import { StagiaireRefusedComponent } from './administration/stagiaire-refused/stagiaire-refused.component';
import {EmployeeService} from "./services/employees/employee.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { AdminassetsComponent } from './administration/adminassets/adminassets.component';
import { StagiaireAtestationComponent } from './administration/stagiaire-atestation/stagiaire-atestation.component';
import { CarteStagiaireComponent } from './administration/carte-stagiaire/carte-stagiaire.component';
import { DemandeAbsencesComponent } from './administration/demande-absences/demande-absences.component';
import { AbsencesNonJustifierComponent } from './administration/absences-non-justifier/absences-non-justifier.component';
import { ManageDemandeFormationComponent } from './administration/manage-demande-formation/manage-demande-formation.component';
import { ConsulterEmployeeFormationComponent } from './administration/consulter-employee-formation/consulter-employee-formation.component';
import { AdminHomeComponent } from './administration/admin-home/admin-home.component';
import {AuthService} from "./services/authentification/auth.service";
import {AppHttpInterceptor} from "./interceptors/app-http.interceptor";
import { NotAuthorizedComponent } from './not-authorized/not-authorized.component';
import {StagiaireService} from "./services/stagiaires/stagiaire.service";
import {OffreStageService} from "./services/stages/offre-stage.service";
import {AbsenceService} from "./services/absences/absence.service";
import {FormationService} from "./services/formations/formation.service";
import { PostulerStageComponent } from './administration/StagiaireDash/postuler-stage/postuler-stage.component';
import { ConsulterAbsencesComponent } from './administration/StagiaireDash/consulter-absences/consulter-absences.component';
import { DemandeAttestationComponent } from './administration/StagiaireDash/demande-attestation/demande-attestation.component';
import { EmployeeDemandeFormationComponent } from './administration/EmployeeDash/employee-demande-formation/employee-demande-formation.component';
import { EmployeeDemandeAbsencesComponent } from './administration/EmployeeDash/employee-demande-absences/employee-demande-absences.component';
import { EmployeeConsultationAbsencesComponent } from './administration/EmployeeDash/employee-consultation-absences/employee-consultation-absences.component';
import { EmployeeStatusDemandeFormationComponent } from './administration/EmployeeDash/employee-status-demande-formation/employee-status-demande-formation.component';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
import {NgToastModule} from "ng-angular-popup";
import { ManageStagesComponent } from './administration/inters-offres-management/manage-stages/manage-stages.component';
import { AddNewStageComponent } from './administration/inters-offres-management/add-new-stage/add-new-stage.component';
import { ChatappComponent } from './chat/chatapp/chatapp.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    InterncreateaccountComponent,
    InternloginComponent,
    InternoffersComponent,
    HomestartassetsComponent,
    InternsDetailsModalComponent,
    AddAbsenceComponent,
    AddemployeeComponent,
    AddplanformationComponent,
    AdminfooterComponent,
    AdminheaderComponent,
    AllAbsenceComponent,
    AllemployeesComponent,
    DemandeStageComponent,
    EntretienStagiaireComponent,
    ManageplanformationComponent,
    StagiaireAcceptedComponent,
    StagiaireRefusedComponent,
    AdminassetsComponent,
    StagiaireAtestationComponent,
    CarteStagiaireComponent,
    DemandeAbsencesComponent,
    AbsencesNonJustifierComponent,
    ManageDemandeFormationComponent,
    ConsulterEmployeeFormationComponent,
    AdminHomeComponent,
    NotAuthorizedComponent,
    PostulerStageComponent,
    ConsulterAbsencesComponent,
    DemandeAttestationComponent,
    EmployeeDemandeFormationComponent,
    EmployeeDemandeAbsencesComponent,
    EmployeeConsultationAbsencesComponent,
    EmployeeStatusDemandeFormationComponent,
    NotFoundPageComponent,
    ManageStagesComponent,
    AddNewStageComponent,
    ChatappComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterLink, RouterLinkActive, FormsModule, ReactiveFormsModule,
    HttpClientModule,NgToastModule
  ],
  providers: [
    EmployeeService,
    AuthService,
    StagiaireService,
    AbsenceService,
    OffreStageService,
    FormationService,
    {provide: HTTP_INTERCEPTORS, useClass: AppHttpInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

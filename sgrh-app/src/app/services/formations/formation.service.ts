import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class FormationService {

  private apiUrl = 'http://localhost:8090/api/miaad/plans-formation';

  constructor(private http: HttpClient) { }

  addPlanFormation(planFormation: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, planFormation);
  }

  getPlansFormations(): Observable<any[]> {
    return this.http.get<any>(this.apiUrl);
  }

  deletePlansFormations(id: any): Observable<any> {
    return this.http.delete(`http://localhost:8090/api/miaad/plans-formation/${id}`);
  }

  getTrainingRequest(): Observable<any[]> {
    return this.http.get<any>("http://localhost:8090/test/trainingRequest");
  }

  acceptTrainingRequest(id : number): Observable<any> {
    return this.http.put("http://localhost:8090/test/trainingRequest/accept", id);
  }

  rejectTrainingRequest(id : number): Observable<any> {
    return this.http.put("http://localhost:8090/test/trainingRequest/reject", id);
  }

  getFormationsByCollaborateurs(): Observable<any> {
    return this.http.get<any>("http://localhost:8090/api/miaad/formations/allformation/collaborateurs");
  }

  getPlanById(id : number): Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/miaad/plans-formation/planFormation/${id}`);
  }

  getFormations(): Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/miaad/formations`);
  }

  submitFormation(body: any): Observable<any> {
    return this.http.post<any>("http://localhost:8090/test/trainingRequest/submit", body);
  }

  searchFormation(title:any): Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/miaad/formations/search/${title}`);
  }

  getdemandeFormationById(id : number): Observable<any>{
    return this.http.get<any>(`http://localhost:8090/test/trainingRequest/formationByEmployee/${id}`);
  }

  getTrainingRequestNbr(id : number): Observable<any>{
    return this.http.get<any>(`http://localhost:8090/test/trainingRequest/nbrStatus/${id}`);
  }

  getFormationById(id:any){
    return this.http.get<any>(`http://localhost:8090/api/miaad/formations/formation/${id}`);
  }

  deleteFormationById(id:any){
    return this.http.delete(`http://localhost:8090/api/miaad/formations/${id}`)
  }


}

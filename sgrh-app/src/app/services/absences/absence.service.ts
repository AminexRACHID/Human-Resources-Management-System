import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AbsenceService {

  constructor(private http: HttpClient) {}

  public getDemandesAbsences():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/absences/allDemandes');
  }

  public getAbsenceByEmployeeId(id:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/api/absences/employeeAbsence/${id}`);
  }

  public getAbsenceByStagiaireId(id:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/api/absences/stagiaireAbsence/${id}`);
  }

  public getNbrAbsenceEmployee(id:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/api/absences/getAbsencesJustifierNonJustiifer/${id}`);
  }

  public getNbrAbsenceStagiaire(id:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/api/absences/getAbsencesJustifierNonJustiiferStagaire/${id}`);
  }


  public getAbsencesJustifier():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/absences/allJustified');
  }

  public getAbsencesNonJustifier():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/absences/allNonJustified');
  }


  public rejectDemandeAbsence(id:any):Observable<any>{
    return this.http.delete<any>(`http://localhost:8090/api/absences/demandes/${id}`);
  }

  public accepterDemandeAbsence(id:any):Observable<any>{
    // const formData = new FormData();
    // formData.append('id', id);

    return this.http.post<any>(`http://localhost:8090/api/absences/accepterDemande`, id);
  }

  public addAbsence(absence:any):Observable<any>{
    return this.http.post<any>(`http://localhost:8090/api/absences`,absence);
  }

  public sentDemandeAbsence(absence:any):Observable<any>{
    return this.http.post<any>(`http://localhost:8090/api/absences/demandeAbsence`,absence);
  }
}

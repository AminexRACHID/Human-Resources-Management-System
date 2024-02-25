import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class StagiaireService {

  constructor(private http: HttpClient) {}


  public getCondidatesPending():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/stage/stagiaire/pending');
  }

  public getCondidatesInterview():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/stage/stagiaire/entretien');
  }

  public getCondidatesAccepted():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/stage/stagiaire/accepted');
  }

  public getCondidatesRejected():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/stage/stagiaire/rejected');
  }

  public acceptCondidate(intershipApplyId:number):Observable<any>{
    return this.http.put(`http://localhost:8090/api/stage/accept/${intershipApplyId}`, undefined);
  }

  public interviewCondidate(intershipApplyId:number):Observable<any>{
    return this.http.put(`http://localhost:8090/api/stage/interview/${intershipApplyId}`, undefined);
  }

  public rejectCondidate(intershipApplyId:number):Observable<any>{
    return this.http.put(`http://localhost:8090/api/stage/reject/${intershipApplyId}`, undefined);
  }

  public deleteCondidate(intershipApplyId:number):Observable<any>{
    return this.http.put(`http://localhost:8090/api/stage/delete/${intershipApplyId}`, undefined);
  }

  public updateCondidate(id:number,updatedInfo:any):Observable<any>{
    return this.http.put(`http://localhost:8090/manage/stagiaire/${id}`, updatedInfo);
  }

  public getCondidate(id:number):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/manage/stagiaire/${id}`);
  }

  public getCondidateByEmail(email:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/manage/stagiaire/search/email/${email}`);
  }
}

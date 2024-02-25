import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class OffreStageService {

  constructor(private http: HttpClient) {}

  public getOffers():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/stage/offers');
  }

  public getOfferById(id:any):Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/stage/offers/${id}`);
  }

  saveOffreStage(stage : any):Observable<any>{
    return this.http.post<any>(`http://localhost:8090/api/stage`,stage);

  }

  public searchOffer(term:any):Observable<any>{
    return this.http.get<Array<any>>(`http://localhost:8090/api/stage/offers/title/${term}`);
  }

  public getStageCondidate(id : any){
    return this.http.get<any>(`http://localhost:8090/api/stage/intershipStagiaire/${id}`);
  }
}

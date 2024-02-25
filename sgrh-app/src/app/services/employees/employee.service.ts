import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Employee} from "../../model/employee.model";

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  constructor(private http: HttpClient) {}

  public getEmployees():Observable<any>{
    return this.http.get<Array<any>>('http://localhost:8090/api/employees');
  }

  public getEmployeeById(employee:any):Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/employees/${employee.id}`);
  }

  public getEmployeeByEmail(email:any):Observable<any>{
    return this.http.get<any>(`http://localhost:8090/api/employees/search/email/${email}`);
  }

  saveEmployee(employee : Employee):Observable<any>{
    return this.http.post<any>(`http://localhost:8090/api/employees`,employee);

  }

}

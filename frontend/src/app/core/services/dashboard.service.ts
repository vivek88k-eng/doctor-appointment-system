import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private apiUrl = 'http://localhost:8080/api/v1/admin/dashboard';

  constructor(private http: HttpClient) {}

  getDashboard() {
    return this.http.get<any>(this.apiUrl);
  }
}
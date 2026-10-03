import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) {
  }

  login(email: string, password: string) {
    return this.http.post(`${this.apiUrl}/login`, {
      email: email,
      password: password
    });
  }

  register(
    fullName: string,
    email: string,
    password: string,
    phone: string
  ) {
    return this.http.post(`${this.apiUrl}/register`, {
      fullName: fullName,
      email: email,
      password: password,
      phone: phone
    });
  }
  logout() {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  localStorage.removeItem('fullName');
  localStorage.removeItem('email');
  localStorage.removeItem('patientId');
}
}
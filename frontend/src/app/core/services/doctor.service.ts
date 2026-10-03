import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DoctorService {

  private apiUrl = 'http://localhost:8080/api/v1/doctors';
  private adminApiUrl = 'http://localhost:8080/api/v1/admin/doctors';

  constructor(private http: HttpClient) {}

  getDoctors() {
    return this.http.get<any[]>(this.apiUrl);
  }

  getDoctorsBySpecialization(specialization: string) {
    return this.http.get<any[]>(
      `${this.apiUrl}?specialization=${encodeURIComponent(specialization)}`
    );
  }
  getDoctorById(id: number){
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  updateDoctor(
    id: number,
    fullName: string,
    specialization: string,
    fee: number,
    active: boolean
  ) {
    return this.http.put(
      `${this.adminApiUrl}/${id}`,
      {
        fullName: fullName,
        specialization: specialization,
        consultationFee: fee,
        active: active
      }
    );
  }
  addDoctor(
  fullName: string,
  specialization: string,
  fee: number,
  active: boolean
) {
  return this.http.post(
    this.adminApiUrl,
    {
      fullName: fullName,
      specialization: specialization,
      consultationFee: fee,
      active: active
    }
  );
}

}
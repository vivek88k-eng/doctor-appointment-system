import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SlotService {

  private apiUrl = 'http://localhost:8080/api/v1/doctors';

  constructor(private http: HttpClient) {}

  getSlots(doctorId: number, date: string) {
    return this.http.get<any[]>(
      `${this.apiUrl}/${doctorId}/slots?date=${date}`
    );
  }
}
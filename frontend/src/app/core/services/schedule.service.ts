import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {

  private adminApiUrl = 'http://localhost:8080/api/v1/admin';
  private doctorApiUrl = 'http://localhost:8080/api/v1/doctors';

  constructor(private http: HttpClient) {}

  createSchedule(
    doctorId: number,
    dayOfWeek: number,
    startTime: string,
    endTime: string,
    slotMinutes: number
  ) {
    return this.http.post(
      `${this.adminApiUrl}/doctors/${doctorId}/schedules`,
      {
        dayOfWeek: dayOfWeek,
        startTime: startTime,
        endTime: endTime,
        slotMinutes: slotMinutes
      }
    );
  }

  getSchedules(doctorId: number) {
    return this.http.get<any[]>(
      `${this.doctorApiUrl}/${doctorId}/schedules`
    );
  }

  updateSchedule(
    id: number,
    dayOfWeek: number,
    startTime: string,
    endTime: string,
    slotMinutes: number
  ) {
    return this.http.put(
      `${this.adminApiUrl}/schedules/${id}`,
      {
        dayOfWeek: dayOfWeek,
        startTime: startTime,
        endTime: endTime,
        slotMinutes: slotMinutes
      }
    );
  }

  deleteSchedule(id: number) {
    return this.http.delete(
      `${this.adminApiUrl}/schedules/${id}`
    );
  }
}
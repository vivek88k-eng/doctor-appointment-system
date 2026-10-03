import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  private apiUrl = 'http://localhost:8080/api/v1/appointments';

  constructor(private http: HttpClient) {}

  bookAppointment(
    patientId: number,
    doctorId: number,
    apptDate: string,
    slotStart: string
  ) {
    return this.http.post(this.apiUrl, {
      patientId: patientId,
      doctorId: doctorId,
      apptDate: apptDate,
      slotStart: slotStart
    });
  }

  getMyAppointments(patientId: number) {
    return this.http.get<any[]>(
      `${this.apiUrl}/my?patientId=${patientId}`
    );
  }
  cancelAppointment(appointmentId: number, patientId: number){
    return this.http.put(`${this.apiUrl}/${appointmentId}/cancel?patientId=${patientId}`,{});
  }
  getAdminAppointments(date: string, doctorId: number) {
  return this.http.get<any[]>(
    `http://localhost:8080/api/v1/admin/appointments?date=${date}&doctorId=${doctorId}`
  );
}

completeAppointment(appointmentId: number) {
  return this.http.put(
    `http://localhost:8080/api/v1/admin/appointments/${appointmentId}/complete`,
    {}
  );
}

markNoShow(appointmentId: number) {
  return this.http.put(
    `http://localhost:8080/api/v1/admin/appointments/${appointmentId}/no-show`,
    {}
  );
}
createPrescription(appointmentId: number, notes: string) {
  return this.http.post(
    'http://localhost:8080/api/v1/prescriptions',
    {
      appointmentId: appointmentId,
      notes: notes
    }
  );
}
checkPrescriptionExists(appointmentId: number) {
  return this.http.get<boolean>(
    `http://localhost:8080/api/v1/prescriptions/${appointmentId}/exists`
  );
}
getPrescription(appointmentId: number) {
  return this.http.get<any>(
    `http://localhost:8080/api/v1/prescriptions/${appointmentId}`
  );
}
}
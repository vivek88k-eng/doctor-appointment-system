package clinic.service;

import java.time.LocalDate;
import java.util.List;

import clinic.dto.CreateAppointmentRequest;
import clinic.entity.Appointment;

public interface AppointmentService {
    Appointment
    bookAppointment(CreateAppointmentRequest request);
    void cancelAppointment(Long appointmentId);
    List<Appointment>getMyAppointments(Long patientId);
    List<Appointment> getAdminAppointments(LocalDate date, Long doctorId);
    void completeAppointment(Long appointmentId);
    void markNoShow(Long appointmentId);
    
}

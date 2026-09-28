package clinic.service;

import java.time.LocalDate;
import java.util.List;

import clinic.dto.CreateAppointmentRequest;
import clinic.entity.Appointment;

public interface AppointmentService {
    Appointment
    bookAppointment(CreateAppointmentRequest request, Long patientId);
    void cancelAppointment(Long appointmentId, Long patientId);
    List<Appointment>getMyAppointments(Long patientId);
    List<Appointment> getAdminAppointments(LocalDate date, Long doctorId);
    void completeAppointment(Long appointmentId);
    void markNoShow(Long appointmentId);
    
}

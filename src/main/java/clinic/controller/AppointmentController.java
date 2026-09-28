package clinic.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clinic.dto.CreateAppointmentRequest;
import clinic.entity.Appointment;
import clinic.service.AppointmentService;
import clinic.service.AuthService;

@RestController
@RequestMapping("/api/v1")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AuthService authService;

    public AppointmentController(
            AppointmentService appointmentService,
            AuthService authService) {

        this.appointmentService = appointmentService;
        this.authService = authService;
    }

    // Book appointment
  @PostMapping("/appointments")
public ResponseEntity<Appointment> bookAppointment(
        @RequestBody CreateAppointmentRequest request,
        Authentication authentication) {

    String email = authentication.getName();

    Long patientId = authService
            .getUserByEmail(email)
            .getId();

    Appointment appointment =
            appointmentService.bookAppointment(
                    request,
                    patientId);

    return new ResponseEntity<>(
            appointment,
            HttpStatus.CREATED
    );
}
    // Cancel appointment
    @PutMapping("/appointments/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        Long patientId = authService.getUserByEmail(email).getId();

        appointmentService.cancelAppointment(id, patientId);

        return ResponseEntity.noContent().build();
    }

    // Get patient's appointments
    @GetMapping("/appointments/my")
    public ResponseEntity<List<Appointment>> getMyAppointments(
            Authentication authentication) {
        String email = authentication.getName();
        Long patientId = authService.getUserByEmail(email).getId();

        List<Appointment> appointments = appointmentService.getMyAppointments(patientId);

        return ResponseEntity.ok(appointments);
    }

    // Get admin appointments
    @GetMapping("/admin/appointments")
    public ResponseEntity<List<Appointment>> getAdminAppointments(
            @RequestParam LocalDate date,
            @RequestParam Long doctorId) {

        List<Appointment> appointments = appointmentService.getAdminAppointments(
                date,
                doctorId);

        return ResponseEntity.ok(appointments);
    }

    // Mark appointment as completed
    @PutMapping("/admin/appointments/{id}/complete")
    public ResponseEntity<Void> completeAppointment(
            @PathVariable Long id) {

        appointmentService.completeAppointment(id);

        return ResponseEntity.noContent().build();
    }

    // Mark appointment as no-show
    @PutMapping("/admin/appointments/{id}/no-show")
    public ResponseEntity<Void> markNoShow(
            @PathVariable Long id) {

        appointmentService.markNoShow(id);

        return ResponseEntity.noContent().build();
    }
}
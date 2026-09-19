package clinic.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    // Book appointment
    @PostMapping
    public ResponseEntity<Appointment> bookAppointment(
            @RequestBody CreateAppointmentRequest request) {

        Appointment appointment =
                appointmentService.bookAppointment(request);

        return new ResponseEntity<>(
                appointment,
                HttpStatus.CREATED
        );
    }

    // Cancel appointment
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(
            @PathVariable Long id) {

        appointmentService.cancelAppointment(id);

        return ResponseEntity.noContent().build();
    }

    // Get patient's appointments
    @GetMapping("/my")
    public ResponseEntity<List<Appointment>> getMyAppointments(
            @RequestParam Long patientId) {

        List<Appointment> appointments =
                appointmentService.getMyAppointments(patientId);

        return ResponseEntity.ok(appointments);
    }

    // Get admin appointments
    @GetMapping("/admin")
    public ResponseEntity<List<Appointment>> getAdminAppointments(
            @RequestParam LocalDate date,
            @RequestParam Long doctorId) {

        List<Appointment> appointments =
                appointmentService.getAdminAppointments(
                        date,
                        doctorId);

        return ResponseEntity.ok(appointments);
    }

    // Mark appointment as completed
    @PutMapping("/admin/{id}/complete")
    public ResponseEntity<Void> completeAppointment(
            @PathVariable Long id) {

        appointmentService.completeAppointment(id);

        return ResponseEntity.noContent().build();
    }

    // Mark appointment as no-show
    @PutMapping("/admin/{id}/no-show")
    public ResponseEntity<Void> markNoShow(
            @PathVariable Long id) {

        appointmentService.markNoShow(id);

        return ResponseEntity.noContent().build();
    }
}
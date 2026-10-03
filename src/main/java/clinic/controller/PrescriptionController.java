package clinic.controller;

import clinic.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinic.dto.CreatePrescriptionRequest;
import clinic.entity.Prescription;
import clinic.service.PrescriptionService;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;
    private final AuthService authService;

    public PrescriptionController(
            PrescriptionService prescriptionService, AuthService authService) {

        this.prescriptionService = prescriptionService;
        this.authService=authService;

    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Prescription> createPrescription(
            @RequestBody CreatePrescriptionRequest request) {

        Prescription prescription =
                prescriptionService.createPrescription(request);

        return new ResponseEntity<>(
                prescription,
                HttpStatus.CREATED
        );
    }
    @GetMapping("/{appointmentId}/exists")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Boolean> prescriptionExists(
        @PathVariable Long appointmentId) {

    return ResponseEntity.ok(
            prescriptionService.prescriptionExists(appointmentId)
    );
}
@GetMapping("/{appointmentId}")
@PreAuthorize("hasRole('PATIENT')")
public ResponseEntity<Prescription> getPrescription(
        @PathVariable Long appointmentId,
        Authentication authentication) {

    String email = authentication.getName();

    Long patientId = authService
            .getUserByEmail(email)
            .getId();

    Prescription prescription =
            prescriptionService.getPrescriptionForPatient(
                    appointmentId,
                    patientId
            );

    return ResponseEntity.ok(prescription);
}
}
package clinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import clinic.entity.Doctor;
import clinic.service.DoctorService;

@RestController
@RequestMapping("/api/v1")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // Patient/Public: Get all active doctors
    // Optional specialization filter
    @GetMapping("/doctors")
    public List<Doctor> getDoctors(
            @RequestParam(required = false) String specialization) {

        if (specialization != null && !specialization.isBlank()) {
            return doctorService.getDoctorBySpecialization(specialization);
        }

        return doctorService.getAllActiveDoctors();
    }

    // Admin: Add doctor
    @PostMapping("/admin/doctors")
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody Doctor doctor) {

        Doctor createdDoctor = doctorService.createDoctor(doctor);

        return new ResponseEntity<>(
                createdDoctor,
                HttpStatus.CREATED);
    }

    // Admin: Update doctor
    @PutMapping("/admin/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor doctor) {

        Doctor updatedDoctor =
                doctorService.updateDoctor(id, doctor);

        return ResponseEntity.ok(updatedDoctor);
    }

    // Admin: Deactivate doctor
    @DeleteMapping("/admin/doctors/{id}")
    public ResponseEntity<Void> deactivateDoctor(
            @PathVariable Long id) {

        doctorService.deactivateDoctor(id);

        return ResponseEntity.noContent().build();
    }
}
package clinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public PrescriptionController(
            PrescriptionService prescriptionService) {

        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(
            @RequestBody CreatePrescriptionRequest request) {

        Prescription prescription =
                prescriptionService.createPrescription(request);

        return new ResponseEntity<>(
                prescription,
                HttpStatus.CREATED
        );
    }
}
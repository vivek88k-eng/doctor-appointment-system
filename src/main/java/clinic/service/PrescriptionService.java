package clinic.service;

import clinic.dto.CreatePrescriptionRequest;
import clinic.entity.Prescription;

public interface PrescriptionService {
    Prescription createPrescription(CreatePrescriptionRequest request);
    boolean prescriptionExists(Long appointmentId);
    Prescription getPrescriptionForPatient(Long appointmentId, Long patientId);
    
}

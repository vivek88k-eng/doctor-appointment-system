package clinic.service;

import org.springframework.stereotype.Service;

import clinic.dto.CreatePrescriptionRequest;
import clinic.entity.Appointment;
import clinic.entity.ApptStatus;
import clinic.entity.Prescription;
import clinic.repository.AppointmentRepository;
import clinic.repository.PrescriptionRepository;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public PrescriptionServiceImpl(
            PrescriptionRepository prescriptionRepository,
            AppointmentRepository appointmentRepository) {

        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Prescription createPrescription(
            CreatePrescriptionRequest request) {

        Appointment appointment =
                appointmentRepository.findById(
                        request.getAppointmentId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Appointment not found"));

        if (appointment.getStatus() !=
                ApptStatus.COMPLETED) {

            throw new RuntimeException(
                    "Prescription can only be created for completed appointments");
        }

        Prescription prescription = new Prescription();

        prescription.setAppointment(appointment);
        prescription.setNotes(request.getNotes());

        return prescriptionRepository.save(prescription);
    }
}
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
                if (request == null) {
                        throw new RuntimeException("Prescription request is requried");
                }
                if (request.getAppointmentId() == null) {
                        throw new RuntimeException("Appointment ID is requried");

                }
                if (request.getNotes() == null || request.getNotes().isBlank()) {
                        throw new RuntimeException("Prescription notes are requried");

                }

                Appointment appointment = appointmentRepository.findById(
                                request.getAppointmentId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Appointment not found"));

                if (appointment.getStatus() != ApptStatus.COMPLETED) {

                        throw new RuntimeException(
                                        "Prescription can only be created for completed appointments");
                }
                if (prescriptionRepository.findByAppointmentId(request.getAppointmentId()).isPresent()) {
                        throw new RuntimeException("Prescription alredy exists for this appomtment");

                }

                Prescription prescription = new Prescription();

                prescription.setAppointment(appointment);
                prescription.setNotes(request.getNotes());

                return prescriptionRepository.save(prescription);
        }

        @Override
        public boolean prescriptionExists(Long appointmentId) {
                if (appointmentId == null) {
                        return false;
                }

                return prescriptionRepository
                                .findByAppointmentId(appointmentId)
                                .isPresent();
        }

        @Override
        public Prescription getPrescriptionForPatient(
                        Long appointmentId,
                        Long patientId) {

                if (appointmentId == null || patientId == null) {
                        throw new RuntimeException("Appointment ID and patient ID are required");
                }

                return prescriptionRepository
                                .findByAppointmentIdAndAppointmentPatientId(
                                                appointmentId,
                                                patientId)
                                .orElseThrow(() -> new RuntimeException(
                                                "Prescription not found for this appointment"));
        }
}
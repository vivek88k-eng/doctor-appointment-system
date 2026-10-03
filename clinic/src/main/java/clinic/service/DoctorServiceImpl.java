package clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import clinic.entity.Doctor;
import clinic.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllActiveDoctors() {
        return doctorRepository.findByActiveTrue();
    }

    @Override
    public List<Doctor> getDoctorBySpecialization(String specialization) {
        return doctorRepository
                .findBySpecializationIgnoreCaseAndActiveTrue(specialization);
    }
    @Override
public Doctor getDoctorById(Long id) {

    if (id == null) {
        throw new RuntimeException("Doctor ID is required");
    }

    return doctorRepository.findById(id)
            .filter(Doctor::isActive)
            .orElseThrow(() ->
                    new RuntimeException("Doctor not found"));
}

    @Override
    public Doctor createDoctor(Doctor doctor) {

        if (doctor == null) {
            throw new RuntimeException("Doctor details are required");
        }

        if (doctor.getFullName() == null
                || doctor.getFullName().isBlank()) {
            throw new RuntimeException("Doctor name is required");
        }

        if (doctor.getSpecialization() == null
                || doctor.getSpecialization().isBlank()) {
            throw new RuntimeException("Doctor specialization is required");
        }

        if (doctor.getConsultationFee() == null
                || doctor.getConsultationFee().signum() < 0) {
            throw new RuntimeException(
                    "Consultation fee cannot be negative");
        }

        doctor.setActive(true);

        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Long id, Doctor doctor) {

        if (id == null) {
            throw new RuntimeException("Doctor ID is required");
        }

        if (doctor == null) {
            throw new RuntimeException("Doctor details are required");
        }

        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        if (doctor.getFullName() == null
                || doctor.getFullName().isBlank()) {
            throw new RuntimeException("Doctor name is required");
        }

        if (doctor.getSpecialization() == null
                || doctor.getSpecialization().isBlank()) {
            throw new RuntimeException(
                    "Doctor specialization is required");
        }

        if (doctor.getConsultationFee() == null
                || doctor.getConsultationFee().signum() < 0) {
            throw new RuntimeException(
                    "Consultation fee cannot be negative");
        }

        existingDoctor.setFullName(doctor.getFullName());
        existingDoctor.setSpecialization(doctor.getSpecialization());
        existingDoctor.setConsultationFee(
                doctor.getConsultationFee());
         existingDoctor.setActive(doctor.isActive());
        return doctorRepository.save(existingDoctor);
    }

    @Override
    public void deactivateDoctor(Long id) {

        if (id == null) {
            throw new RuntimeException("Doctor ID is required");
        }

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Doctor not found"));

        doctor.setActive(false);

        doctorRepository.save(doctor);
    }
}
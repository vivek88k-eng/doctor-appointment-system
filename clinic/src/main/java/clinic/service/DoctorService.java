package clinic.service;

import java.util.List;

import clinic.entity.Doctor;

public interface DoctorService {
    List<Doctor>getAllActiveDoctors();
    List<Doctor> getDoctorBySpecialization(String specialization);
    Doctor getDoctorById(Long id);
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Long id, Doctor doctor);
    void deactivateDoctor(Long id);
    
}

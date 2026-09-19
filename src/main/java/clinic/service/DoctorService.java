package clinic.service;

import java.util.List;

import clinic.entity.Doctor;

public interface DoctorService {
    List<Doctor>getAllActiveDoctors();
    List<Doctor> getDoctorBySpecialization(String specialization);
    
}

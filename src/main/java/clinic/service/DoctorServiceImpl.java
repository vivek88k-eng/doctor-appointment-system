package clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import clinic.entity.Doctor;
import clinic.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    public DoctorServiceImpl(DoctorRepository doctorRepository){
        this.doctorRepository=doctorRepository;
    }
    @Override
    public List<Doctor>getAllActiveDoctors(){
        return doctorRepository.findByActiveTrue();
    }
    @Override
    public List<Doctor> getDoctorBySpecialization(String specialization){
        return doctorRepository.findBySpecializationIgnoreCaseAndActiveTrue(specialization);
    }
}

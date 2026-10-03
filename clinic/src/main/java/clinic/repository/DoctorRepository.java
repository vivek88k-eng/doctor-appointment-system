package clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clinic.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    List<Doctor>findBySpecializationIgnoreCaseAndActiveTrue(String specialization);
    List<Doctor> findByActiveTrue();
    
}

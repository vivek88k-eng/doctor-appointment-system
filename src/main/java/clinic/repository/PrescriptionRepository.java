package clinic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import clinic.entity.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    Optional<Prescription> findByAppointmentId(Long appointmentId);
    
}

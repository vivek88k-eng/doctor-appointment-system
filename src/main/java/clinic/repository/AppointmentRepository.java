package clinic.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clinic.entity.Appointment;
import clinic.entity.Doctor;
import clinic.entity.User;
import clinic.entity.ApptStatus;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
     List<Appointment>findByPatientOrderByApptDateDescSlotStartDesc(User patient);
    List<Appointment>findByDoctorAndApptDateOrderBySlotStart(Doctor doctor, LocalDate apptDate);
    boolean existsByDoctorAndApptDateAndSlotStartAndStatus(Doctor doctor, LocalDate apptDate, LocalTime slotStart, ApptStatus status);
    
}

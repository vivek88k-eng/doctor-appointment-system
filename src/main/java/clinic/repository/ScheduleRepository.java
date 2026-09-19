package clinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import clinic.entity.Doctor;
import clinic.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule>findByDoctorAndDayOfWeek(Doctor doctor, int dayOfWeek);
    List<Schedule>findByDoctorId(Long doctorId);
    List <Schedule> findByDoctorIdAndDayOfWeek(Long doctorId, int dayOfWeek);
}

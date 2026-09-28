package clinic.service;

import java.util.List;

import org.springframework.stereotype.Service;

import clinic.entity.Doctor;
import clinic.entity.Schedule;
import clinic.repository.DoctorRepository;
import clinic.repository.ScheduleRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorRepository doctorRepository;
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, DoctorRepository doctorRepository){
        this.scheduleRepository=scheduleRepository;
        this.doctorRepository=doctorRepository;
    }
    @Override
    
public Schedule createSchedule(Long doctorId, Schedule schedule) {

    if (schedule.getDayOfWeek() < 1 || schedule.getDayOfWeek() > 7) {
        throw new RuntimeException("Day of week must be between 1 and 7");
    }

    if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
        throw new RuntimeException("Start time and end time are required");
    }

    if (!schedule.getStartTime().isBefore(schedule.getEndTime())) {
        throw new RuntimeException("Start time must be before end time");
    }

    if (schedule.getSlotMinutes() <= 0) {
        throw new RuntimeException("Slot minutes must be greater than 0");
    }
    long totalMinutes = java.time.Duration.between(
        schedule.getStartTime(),
        schedule.getEndTime()
).toMinutes();

if (totalMinutes % schedule.getSlotMinutes() != 0) {
    throw new RuntimeException(
            "Schedule duration must be evenly divisible by slot minutes"
    );
}

    Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    schedule.setDoctor(doctor);

    return scheduleRepository.save(schedule);
}

    @Override
    public List<Schedule> getScheduleByDoctor(Long doctorId){
        return scheduleRepository.findByDoctorId(doctorId);
    }
    @Override
public Schedule updateSchedule(Long id, Schedule schedule) {

    if (schedule.getDayOfWeek() < 1 || schedule.getDayOfWeek() > 7) {
        throw new RuntimeException("Day of week must be between 1 and 7");
    }

    if (schedule.getStartTime() == null || schedule.getEndTime() == null) {
        throw new RuntimeException("Start time and end time are required");
    }

    if (!schedule.getStartTime().isBefore(schedule.getEndTime())) {
        throw new RuntimeException("Start time must be before end time");
    }

    if (schedule.getSlotMinutes() <= 0) {
        throw new RuntimeException("Slot minutes must be greater than 0");
    }
    long totalMinutes = java.time.Duration.between(
        schedule.getStartTime(),
        schedule.getEndTime()
).toMinutes();

if (totalMinutes % schedule.getSlotMinutes() != 0) {
    throw new RuntimeException(
            "Schedule duration must be evenly divisible by slot minutes"
    );
}

    Schedule existingSchedule = scheduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Schedule not found"));

    existingSchedule.setDayOfWeek(schedule.getDayOfWeek());
    existingSchedule.setStartTime(schedule.getStartTime());
    existingSchedule.setEndTime(schedule.getEndTime());
    existingSchedule.setSlotMinutes(schedule.getSlotMinutes());

    return scheduleRepository.save(existingSchedule);
}
    @Override
    public void deleteSchedule(Long id){
        Schedule existingSchedule=scheduleRepository.findById(id).orElseThrow(()-> new RuntimeException("Schedule not found"));
 scheduleRepository.delete(existingSchedule);       
    }

    
}

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
    public Schedule createSchedule(Schedule schedule){
        Long doctorId=schedule.getDoctor().getId();
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()-> new RuntimeException("Doctor not found"));
        schedule.setDoctor(doctor);
        return scheduleRepository.save(schedule);
    }
    @Override
    public List<Schedule> getScheduleByDoctor(Long doctorId){
        return scheduleRepository.findByDoctorId(doctorId);
    }
    @Override
    public Schedule updateSchedule(Long id, Schedule schedule){
        Schedule existingSchedule=scheduleRepository.findById(id).orElseThrow(()-> new RuntimeException("Schedule not found"));
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

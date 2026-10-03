package clinic.service;

import java.util.List;

import clinic.entity.Schedule;

public interface ScheduleService {
    Schedule createSchedule(Long doctorId, Schedule schedule);
    List<Schedule> getScheduleByDoctor(Long doctorId);
    Schedule updateSchedule(Long id, Schedule schedule);
    void deleteSchedule(Long id);
    
}

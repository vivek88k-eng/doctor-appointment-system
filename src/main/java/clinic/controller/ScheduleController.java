package clinic.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import clinic.entity.Doctor;
import clinic.entity.Schedule;
import clinic.service.ScheduleService;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Create schedule for a doctor
    @PostMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<Schedule> createSchedule(
            @PathVariable Long doctorId,
            @RequestBody Schedule schedule) {

       if(schedule.getDoctor()==null){
        schedule.setDoctor(new Doctor());
       }
       schedule.getDoctor().setId(doctorId);
       Schedule createdSchedule=scheduleService.createSchedule(schedule);
        return new ResponseEntity<>(
                createdSchedule,
                HttpStatus.CREATED
        );
    }

    // Get all schedules of a doctor
    @GetMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                scheduleService.getScheduleByDoctor(doctorId)
        );
    }

    // Update schedule
    @PutMapping("/schedule/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long id,
            @RequestBody Schedule schedule) {

        Schedule updatedSchedule =
                scheduleService.updateSchedule(id, schedule);

        return ResponseEntity.ok(updatedSchedule);
    }

    // Delete schedule
    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id) {

        scheduleService.deleteSchedule(id);

        return ResponseEntity.noContent().build();
    }
}
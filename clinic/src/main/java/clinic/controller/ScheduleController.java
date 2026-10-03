package clinic.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import clinic.entity.Schedule;
import clinic.service.ScheduleService;

@RestController
@RequestMapping("/api/v1")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/admin/doctors/{doctorId}/schedules")
    public ResponseEntity<Schedule> createSchedule(
            @PathVariable Long doctorId,
            @RequestBody Schedule schedule) {

        return ResponseEntity.ok(
                scheduleService.createSchedule(doctorId, schedule));
    }

    @GetMapping("/doctors/{doctorId}/schedules")
    public ResponseEntity<List<Schedule>> getSchedules(
            @PathVariable Long doctorId) {

        return ResponseEntity.ok(
                scheduleService.getScheduleByDoctor(doctorId));
    }

    @PutMapping("/admin/schedules/{id}")
    public ResponseEntity<Schedule> updateSchedule(
            @PathVariable Long id,
            @RequestBody Schedule schedule) {

        return ResponseEntity.ok(
                scheduleService.updateSchedule(id, schedule));
    }

    @DeleteMapping("/admin/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id) {

        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
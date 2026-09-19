package clinic.service;

import clinic.dto.SlotDto;
import clinic.entity.ApptStatus;
import clinic.entity.Doctor;
import clinic.entity.Schedule;
import clinic.repository.AppointmentRepository;
import clinic.repository.DoctorRepository;
import clinic.repository.ScheduleRepository;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotServiceImpl implements SlotService {

    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;
    private final AppointmentRepository appointmentRepository;

    public SlotServiceImpl(
            DoctorRepository doctorRepository,
            ScheduleRepository scheduleRepository,
            AppointmentRepository appointmentRepository) {

        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<SlotDto> getAvailableSlots(Long doctorId, LocalDate date) {

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        int dayNumber = dayOfWeek.getValue();

        List<Schedule> schedules =
                scheduleRepository.findByDoctorIdAndDayOfWeek(
                        doctorId, dayNumber);

        List<SlotDto> slots = new ArrayList<>();

        for (Schedule schedule : schedules) {

            LocalTime currentTime = schedule.getStartTime();

            while (currentTime
                    .plusMinutes(schedule.getSlotMinutes())
                    .compareTo(schedule.getEndTime()) <= 0) {

                LocalTime slotEnd =
                        currentTime.plusMinutes(schedule.getSlotMinutes());

                boolean booked =
                        appointmentRepository
                                .existsByDoctorAndApptDateAndSlotStartAndStatus(
                                        doctor,
                                        date,
                                        currentTime, ApptStatus.BOOKED);

                SlotDto slot = new SlotDto(
                        currentTime,
                        slotEnd,
                        !booked
                );

                slots.add(slot);

                currentTime = slotEnd;
            }
        }

        return slots;
    }
}
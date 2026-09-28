package clinic.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import clinic.dto.CreateAppointmentRequest;
import clinic.entity.ApptStatus;
import clinic.entity.Appointment;
import clinic.entity.Doctor;
import clinic.entity.Role;
import clinic.entity.Schedule;
import clinic.entity.User;
import clinic.exception.DoubleBookingException;
import clinic.repository.AppointmentRepository;
import clinic.repository.DoctorRepository;
import clinic.repository.ScheduleRepository;
import clinic.repository.UserRepository;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            DoctorRepository doctorRepository,
            ScheduleRepository scheduleRepository,
            UserRepository userRepository) {

        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Appointment bookAppointment(CreateAppointmentRequest request, Long patientId) {

        // Validate request
        if (request == null) {
            throw new RuntimeException("Appointment request is required");
        }

        if (patientId == null) {
            throw new RuntimeException("Patient ID is required");
        }

        if (request.getDoctorId() == null) {
            throw new RuntimeException("Doctor ID is required");
        }

        if (request.getApptDate() == null) {
            throw new RuntimeException("Appointment date is required");
        }

        if (request.getSlotStart() == null) {
            throw new RuntimeException("Appointment time is required");
        }

        // 1. Find doctor
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // 2. Check doctor is active
        if (!doctor.isActive()) {
            throw new RuntimeException("Doctor is not active");
        }

        // 3. Find patient
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // 4. Check patient role
        if (patient.getRole() != Role.PATIENT) {
            throw new RuntimeException(
                    "Only patients can book appointments");
        }

        // 5. Appointment date cannot be in the past
        if (request.getApptDate().isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "Appointment date cannot be in the past");
        }

        // 6. Appointment time cannot be in the past
        if (request.getApptDate().isEqual(LocalDate.now())
                && request.getSlotStart().isBefore(LocalTime.now())) {

            throw new RuntimeException(
                    "Appointment time has already passed");
        }

        // 7. Get day of week
        DayOfWeek dayOfWeek = request.getApptDate().getDayOfWeek();

        int dayNumber = dayOfWeek.getValue();

        // 8. Find doctor's schedule for that day
        List<Schedule> schedules = scheduleRepository.findByDoctorIdAndDayOfWeek(
                doctor.getId(),
                dayNumber);

        if (schedules.isEmpty()) {
            throw new RuntimeException(
                    "Doctor is not available on this day");
        }

        // 9. Check whether selected slot is valid
        boolean validSlot = false;

        for (Schedule schedule : schedules) {

            LocalTime currentTime = schedule.getStartTime();

            while (currentTime
                    .plusMinutes(schedule.getSlotMinutes())
                    .compareTo(schedule.getEndTime()) <= 0) {

                if (currentTime.equals(request.getSlotStart())) {
                    validSlot = true;
                    break;
                }

                currentTime = currentTime.plusMinutes(
                        schedule.getSlotMinutes());
            }

            if (validSlot) {
                break;
            }
        }
       

        if (!validSlot) {
            throw new RuntimeException(
                    "Invalid appointment slot");
        }

        // 10. Check existing appointment for this slot
        Optional<Appointment> existingAppointment = appointmentRepository.findByDoctorAndApptDateAndSlotStart(doctor,
                request.getApptDate(), request.getSlotStart());
        // if an appointment already exists
        if (existingAppointment.isPresent()) {
            Appointment appointment = existingAppointment.get();
            // Slot is available again if previous appointment was cancelled
            if (appointment.getStatus() == ApptStatus.CANCELLED) {
                appointment.setPatient(patient);
                appointment.setStatus(ApptStatus.BOOKED);
                try {
                    return appointmentRepository.save(appointment);
                } catch (DataIntegrityViolationException ex) {
                    throw new DoubleBookingException("This slot is already booked");
                }
            }
            // BOOKED, COMPLETED or NO_SHOW means slot cannot be reused
            throw new DoubleBookingException("This slot is already booked");
        }

        // 11. Create appointment
        Appointment appointment = new Appointment();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setApptDate(request.getApptDate());
        appointment.setSlotStart(request.getSlotStart());
        appointment.setStatus(ApptStatus.BOOKED);

        // 12. Save appointment
        try {
            return appointmentRepository.save(appointment);

        } catch (DataIntegrityViolationException ex) {
            throw new DoubleBookingException(
                    "This slot is already booked");
        }
    }

    // Cancel appointment
    @Override
    public void cancelAppointment(Long appointmentId, Long patientId) {
        if (appointmentId == null) {
            throw new RuntimeException("Appointment ID is required");
        }
        if (patientId == null) {
            throw new RuntimeException("PatientId is required ");
        }
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        // Check that this appointment belongs to the patient
        if (!appointment.getPatient().getId().equals(patientId)) {
            throw new RuntimeException("You can only cancel Your own appointment");

        }
        // Only booked appointment can be cancelled
        if (appointment.getStatus() != ApptStatus.BOOKED) {
            throw new RuntimeException("Only booked appointment can be cancelled");

        }
        // cancelled must be at least 2 hours before appointment
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getApptDate(), appointment.getSlotStart());
        if (LocalDateTime.now().plusHours(2).isAfter(appointmentDateTime)) {
            throw new RuntimeException("Appointment can only be cancelled at least 2 hours before");

        }
        appointment.setStatus(ApptStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getMyAppointments(Long patientId) {

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (patient.getRole() != Role.PATIENT) {
            throw new RuntimeException(
                    "Only patients can view their appointments");
        }

        return appointmentRepository
                .findByPatientOrderByApptDateDescSlotStartDesc(patient);
    }

    @Override
    public List<Appointment> getAdminAppointments(
            LocalDate date,
            Long doctorId) {
        if (date == null) {
            throw new RuntimeException("Appointment date is required");
        }
        if (doctorId == null) {
            throw new RuntimeException("Doctor ID id requried");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        return appointmentRepository
                .findByDoctorAndApptDateOrderBySlotStart(
                        doctor,
                        date);
    }

    @Override
    public void completeAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Appointment not found"));

        if (appointment.getStatus() != ApptStatus.BOOKED) {
            throw new RuntimeException(
                    "Only booked appointments can be completed");
        }

        appointment.setStatus(ApptStatus.COMPLETED);

        appointmentRepository.save(appointment);
    }

    @Override
    public void markNoShow(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Appointment not found"));

        if (appointment.getStatus() != ApptStatus.BOOKED) {
            throw new RuntimeException(
                    "Only booked appointments can be marked as no-show");
        }

        appointment.setStatus(ApptStatus.NO_SHOW);

        appointmentRepository.save(appointment);
    }
}
package clinic.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import clinic.entity.ApptStatus;
import clinic.repository.AppointmentRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final AppointmentRepository appointmentRepository;

    public DashboardServiceImpl(
            AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Map<String, Object> getDashboard() {

        LocalDate today = LocalDate.now();

        long todayAppointments =
                appointmentRepository.countByApptDate(today);

        long bookedAppointments =
                appointmentRepository.countByStatus(ApptStatus.BOOKED);

        long completedAppointments =
                appointmentRepository.countByStatus(ApptStatus.COMPLETED);

        long cancelledAppointments =
                appointmentRepository.countByStatus(ApptStatus.CANCELLED);

        long noShowAppointments =
                appointmentRepository.countByStatus(ApptStatus.NO_SHOW);

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("todayAppointments", todayAppointments);
        dashboard.put("bookedAppointments", bookedAppointments);
        dashboard.put("completedAppointments", completedAppointments);
        dashboard.put("cancelledAppointments", cancelledAppointments);
        dashboard.put("noShowAppointments", noShowAppointments);

        return dashboard;
    }
}
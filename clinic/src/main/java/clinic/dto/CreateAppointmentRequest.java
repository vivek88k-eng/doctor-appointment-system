package clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentRequest {

    private Long doctorId;
    private LocalDate apptDate;
    private LocalTime slotStart;

    public CreateAppointmentRequest() {
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDate getApptDate() {
        return apptDate;
    }

    public void setApptDate(LocalDate apptDate) {
        this.apptDate = apptDate;
    }

    public LocalTime getSlotStart() {
        return slotStart;
    }

    public void setSlotStart(LocalTime slotStart) {
        this.slotStart = slotStart;
    }
}
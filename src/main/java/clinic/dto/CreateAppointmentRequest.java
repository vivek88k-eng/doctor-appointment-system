package clinic.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateAppointmentRequest {
    private Long patientId;
    private Long doctorId;
    private LocalDate apptDate;
    private LocalTime slotStart;

    public CreateAppointmentRequest(){}
    
    public Long getPatientId(){
        return patientId;
    }
    public void setPatientId(Long patientId){
        this.patientId=patientId;
    }
    public Long getDoctorId(){
        return doctorId;
    }
    public void setDoctorId(Long doctorId){
        this.doctorId=doctorId;
    }
    public LocalDate getApptDate(){
        return apptDate;
    }
    public void setApptDate(LocalDate appDate){
        this.apptDate=appDate;
    }
    public LocalTime getSlotStart(){
        return slotStart;
    }
    public void setSlotStat(LocalTime slotStart){
        this.slotStart=slotStart;
    }
}

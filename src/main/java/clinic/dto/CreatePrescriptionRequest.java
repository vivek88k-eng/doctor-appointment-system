package clinic.dto;

public class CreatePrescriptionRequest {
    private  Long appointmentId;
    private String notes;
    public CreatePrescriptionRequest(){}
    public Long getAppointmentId(){
        return appointmentId;
    }
    public void setAppointmentId(Long appointmentId){
        this.appointmentId=appointmentId;
    }
    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes){
        this.notes=notes;
    }


    
}

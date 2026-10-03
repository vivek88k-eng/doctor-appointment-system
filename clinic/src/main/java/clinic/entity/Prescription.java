package clinic.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "prescriptions")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="appointment_id",nullable=false, unique=true)
    private Appointment appointment;
    @Column(nullable=false, columnDefinition="Text")
    private String notes;
    public Prescription(){}
    public Prescription(Long id, Appointment appointment, String notes){
        this.id=id;
        this.appointment=appointment;
        this.notes=notes;
    }
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id=id;
    }
    public Appointment getAppointment(){
        return appointment;
    }
    public void setAppointment(Appointment appointment){
        this.appointment=appointment;
    }
    public String getNotes(){
        return notes;
    }
    public void setNotes(String notes){
        this.notes=notes;
    }



    
}

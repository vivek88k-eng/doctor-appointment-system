package clinic.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.FetchType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name = "appointments", uniqueConstraints = @UniqueConstraint(columnNames = { "doctor_id", "appt_date",
        "slot_start" }))
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazy Initializer", "handler"} )
    private User patient;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazy Initializer", "handler"} )
    private Doctor doctor;
    @Column(name = "appt_date", nullable = false)
    private LocalDate apptDate;
    @Column(name = "slot_start", nullable = false)
    private LocalTime slotStart;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApptStatus status = ApptStatus.BOOKED;

    public Appointment() {
    }

    public Appointment(Long id, User patient, Doctor doctor, LocalDate apptDate, LocalTime slotStart,
            ApptStatus status) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.apptDate = apptDate;
        this.slotStart = slotStart;
        this.status = status;
    }
    public Long getId(){
        return id; 
    }
    public void setId(Long id){
        this.id=id;

    }
    public User getPatient(){
        return patient;
    }
    public void setPatient(User patient){
        this.patient=patient;
    }
    public Doctor getDoctor(){
        return doctor;
    }
    public  void setDoctor(Doctor doctor){
        this.doctor=doctor;
    }
    public LocalDate getApptDate(){
        return apptDate;
    }
    public void setApptDate(LocalDate apptDate){
        this.apptDate=apptDate;
    }
    public LocalTime getSlotStart(){
        return slotStart;
    }
    public void setSlotStart(LocalTime slotStart){
        this.slotStart=slotStart;
    }
    public  ApptStatus getStatus(){
        return status;
    }
    public void setStatus(ApptStatus status){
        this.status=status;
    }
}

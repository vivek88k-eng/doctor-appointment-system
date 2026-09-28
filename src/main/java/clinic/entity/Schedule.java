package clinic.entity;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="schedules")

public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore 
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="doctor_id",nullable=false)
    private Doctor doctor;
    @Column(nullable=false)
    private int dayOfWeek;
    @Column(nullable=false)
    private LocalTime startTime;
    @Column(nullable=false)
    private LocalTime endTime;
    @Column(nullable=false)
    private int slotMinutes =30;
    public Schedule(){}

    public Schedule(Long id, Doctor doctor, int dayOfWeek, LocalTime startTime, LocalTime endTime, int slotMinutes){
        this.id=id;
        this.doctor=doctor;
        this.dayOfWeek=dayOfWeek;
        this.startTime=startTime;
        this.endTime=endTime;
        this.slotMinutes=slotMinutes;
    }
    public Long getId(){
        return id;

    }
    public void setId(Long id){
        this.id=id;
    }
    public Doctor getDoctor(){
        return doctor;
    }
    public void setDoctor(Doctor doctor){
        this.doctor=doctor;
    }
    public int getDayOfWeek(){
        return dayOfWeek;
    }
    public void setDayOfWeek(int dayOfWeek){
        this.dayOfWeek=dayOfWeek;
        
    }
    public LocalTime getStartTime(){
        return startTime;
    }
    public void setStartTime(LocalTime startTime){
        this.startTime=startTime;
    }
    public LocalTime getEndTime(){
        return endTime;
    }
    public void setEndTime(LocalTime endTime){
        this.endTime=endTime;
    }
    public int getSlotMinutes(){
        return slotMinutes;
    }
    public void setSlotMinutes(int slotMinutes){
        this.slotMinutes=slotMinutes;
    }
    
}

package clinic.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import clinic.entity.Doctor;
import clinic.service.DoctorService;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {
    private final DoctorService doctorService;
    public DoctorController(DoctorService doctorService){
        this.doctorService=doctorService;
    }
    @GetMapping
    public List<Doctor> getDoctors(@RequestParam(required  =false)String specialization){
        if(specialization!= null && !specialization.isBlank()){
            return doctorService.getDoctorBySpecialization(specialization);
        }
        return doctorService.getAllActiveDoctors();
    }
    
}

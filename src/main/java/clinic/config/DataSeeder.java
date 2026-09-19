package clinic.config;

import clinic.entity.Doctor;
import clinic.entity.Role;
import clinic.entity.User;
import clinic.repository.DoctorRepository;
import clinic.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;

    public DataSeeder(UserRepository userRepository,
            DoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public void run(String... args) {

        seedUsers();
        seedDoctors();
    }

    private void seedUsers() {

        if (userRepository.count() == 0) {

            User patient1 = new User(
                    null,
                    "Rahul Kumar",
                    "rahul@gmail.com",
                    "password",
                    Role.PATIENT,
                    "9876543210");

            User patient2 = new User(
                    null,
                    "Priya Sharma",
                    "priya@gmail.com",
                    "password",
                    Role.PATIENT,
                    "9876543211");

            userRepository.save(patient1);
            userRepository.save(patient2);

            System.out.println("Seed users created successfully!");
        }
    }

    private void seedDoctors() {

        if (doctorRepository.count() == 0) {

            Doctor doctor1 = new Doctor(
                    null,
                    "Dr. Amit Sharma",
                    "Cardiology",
                    new BigDecimal("800.00"),
                    true,
                    null);

            Doctor doctor2 = new Doctor(
                    null,
                    "Dr. Neha Singh",
                    "Dermatology",
                    new BigDecimal("600.00"),
                    true,
                    null);

            Doctor doctor3 = new Doctor(
                    null,
                    "Dr. Raj Verma",
                    "General Medicine",
                    new BigDecimal("500.00"),
                    true,
                    null);

            doctorRepository.save(doctor1);
            doctorRepository.save(doctor2);
            doctorRepository.save(doctor3);

            System.out.println("Seed doctors created successfully!");
        }
    }
}
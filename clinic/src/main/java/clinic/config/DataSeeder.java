package clinic.config;

import clinic.entity.Doctor;
import clinic.entity.Role;
import clinic.entity.User;
import clinic.repository.DoctorRepository;
import clinic.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
            DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        seedUsers();
        migrateExistingPasswords();
        seedDoctors();
    }

    private void seedUsers() {

        if (!userRepository.existsByEmail("rahul@gmail.com")) {

            User patient1 = new User(
                    null,
                    "Rahul Kumar",
                    "rahul@gmail.com",
                    passwordEncoder.encode("password"),
                    Role.PATIENT,
                    "9876543210");

            userRepository.save(patient1);
        }

        if (!userRepository.existsByEmail("priya@gmail.com")) {

            User patient2 = new User(
                    null,
                    "Priya Sharma",
                    "priya@gmail.com",
                    passwordEncoder.encode("password"),
                    Role.PATIENT,
                    "9876543211");

            userRepository.save(patient2);
        }

        if (!userRepository.existsByEmail("admin@gmail.com")) {

            User admin = new User(
                    null,
                    "Admin User",
                    "admin@gmail.com",
                    passwordEncoder.encode("admin123"),
                    Role.ADMIN,
                    "9876543212");

            userRepository.save(admin);

            
        }
    }

    private void migrateExistingPasswords() {

        userRepository.findByEmail("rahul@gmail.com").ifPresent(user -> {
            if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {

                user.setPassword(passwordEncoder.encode("password"));
                userRepository.save(user);
            }
        });

        userRepository.findByEmail("priya@gmail.com").ifPresent(user -> {
            if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {

                user.setPassword(passwordEncoder.encode("password"));
                userRepository.save(user);
            }
        });

        userRepository.findByEmail("admin@gmail.com").ifPresent(user -> {
            if (!user.getPassword().startsWith("$2a$")
                    && !user.getPassword().startsWith("$2b$")
                    && !user.getPassword().startsWith("$2y$")) {

                user.setPassword(passwordEncoder.encode("admin123"));
                userRepository.save(user);
            }
        });
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

            
        }
    }
}
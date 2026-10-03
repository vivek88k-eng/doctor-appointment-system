import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppointmentService } from '../../../core/services/appointment.service';
import { DoctorService } from '../../../core/services/doctor.service';

@Component({
  selector: 'app-admin-day',
  imports: [FormsModule],
  templateUrl: './admin-day.html',
  styleUrl: './admin-day.css',
})
export class AdminDay implements OnInit {

  selectedDate = '';
  selectedDoctorId = 0;

  doctors: any[] = [];
  appointments: any[] = [];

  prescriptionExists: { [appointmentId: number]: boolean } = {};

  showPrescriptionForm = false;
  prescriptionAppointmentId: number | null = null;
  prescriptionNotes = '';

  constructor(
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const today = new Date();

    this.selectedDate = today.toISOString().split('T')[0];

    this.doctorService.getDoctors().subscribe({
      next: (response) => {
        console.log('Admin day doctors loaded:', response);

        this.doctors = response;
        this.cdr.detectChanges();

        if (this.doctors.length > 0) {
          this.selectedDoctorId = this.doctors[0].id;
          this.loadAppointments();
        }
      },
      error: (error) => {
        console.error('Failed to load doctors:', error);
      }
    });
  }

  loadAppointments() {
    if (!this.selectedDoctorId) {
      this.appointments = [];
      return;
    }

    this.appointmentService
      .getAdminAppointments(
        this.selectedDate,
        Number(this.selectedDoctorId)
      )
      .subscribe({
        next: (response) => {
          console.log('Admin appointments loaded:', response);

          this.appointments = response;

          this.prescriptionExists = {};

          this.appointments.forEach((appointment) => {
            this.appointmentService
              .checkPrescriptionExists(appointment.id)
              .subscribe({
                next: (exists) => {
                  this.prescriptionExists[appointment.id] = exists;
                  this.cdr.detectChanges();
                },
                error: (error) => {
                  console.error(
                    'Failed to check prescription:',
                    appointment.id,
                    error
                  );

                  this.prescriptionExists[appointment.id] = false;
                }
              });
          });

          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error(
            'Failed to load admin appointments:',
            error
          );

          this.appointments = [];
        }
      });
  }

  completeAppointment(id: number) {
    this.appointmentService.completeAppointment(id).subscribe({
      next: () => {
        console.log('Appointment completed:', id);
        this.loadAppointments();
      },
      error: (error) => {
        console.error(
          'Failed to complete appointment:',
          error
        );
      }
    });
  }

  markNoShow(id: number) {
    this.appointmentService.markNoShow(id).subscribe({
      next: () => {
        console.log(
          'Appointment marked as no-show:',
          id
        );

        this.loadAppointments();
      },
      error: (error) => {
        console.error(
          'Failed to mark no-show:',
          error
        );
      }
    });
  }

  openPrescriptionForm(appointmentId: number) {
    this.showPrescriptionForm = true;
    this.prescriptionAppointmentId = appointmentId;
    this.prescriptionNotes = '';
  }

  savePrescription() {
    if (
      this.prescriptionAppointmentId === null ||
      !this.prescriptionNotes.trim()
    ) {
      alert('Please enter prescription notes.');
      return;
    }

    this.appointmentService
      .createPrescription(
        this.prescriptionAppointmentId,
        this.prescriptionNotes.trim()
      )
      .subscribe({
        next: (response) => {
          console.log(
            'Prescription created:',
            response
          );

          alert(
            'Prescription created successfully.'
          );

          const appointmentId =
            this.prescriptionAppointmentId;

          if (appointmentId !== null) {
            this.prescriptionExists[appointmentId] = true;
          }

          this.showPrescriptionForm = false;
          this.prescriptionAppointmentId = null;
          this.prescriptionNotes = '';

          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error(
            'Failed to create prescription:',
            error
          );

          alert(
            'Failed to create prescription.'
          );
        }
      });
  }
}
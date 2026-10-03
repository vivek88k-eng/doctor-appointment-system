import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { AppointmentService } from '../../../core/services/appointment.service';

@Component({
  selector: 'app-my-appointments',
  imports: [RouterLink],
  templateUrl: './my-appointments.html',
  styleUrl: './my-appointments.css',
})
export class MyAppointments implements OnInit {

  appointments: any[] = [];

  prescriptions: { [appointmentId: number]: any } = {};

  constructor(
    private appointmentService: AppointmentService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    const patientId = Number(localStorage.getItem('patientId'));

    if (!patientId) {
      return;
    }

    this.appointmentService.getMyAppointments(patientId).subscribe({

      next: (response) => {

        console.log('My appointments:', response);

        this.appointments = response;

        this.appointments.forEach((appointment) => {

          if (appointment.status === 'COMPLETED') {

            this.appointmentService
              .getPrescription(appointment.id)
              .subscribe({

                next: (prescription) => {

                  console.log(
                    'Prescription loaded:',
                    prescription
                  );

                  this.prescriptions[appointment.id] =
                    prescription;

                  this.cdr.detectChanges();
                },

                error: (error) => {

                  console.error(
                    'Failed to load prescription:',
                    error
                  );

                }

              });

          }

        });

        console.log(
          'Appointments length:',
          this.appointments.length
        );

        this.cdr.detectChanges();
      },

      error: (error) => {

        console.error(
          'Failed to load appointments:',
          error
        );

      }

    });
  }

  cancelAppointment(appointment: any) {

    const patientId =
      Number(localStorage.getItem('patientId'));

    if (!patientId) {
      alert('Please login again.');
      return;
    }

    this.appointmentService
      .cancelAppointment(
        appointment.id,
        patientId
      )
      .subscribe({

        next: () => {

          alert(
            'Appointment cancelled successfully.'
          );

          this.appointments =
            this.appointments.map((item) =>
              item.id === appointment.id
                ? {
                    ...item,
                    status: 'CANCELLED'
                  }
                : item
            );

          this.cdr.detectChanges();
        },

        error: (error) => {

          console.error(
            'Cancellation failed:',
            error
          );

          alert(
            'Failed to cancel appointment.'
          );
        }

      });
  }
}
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { SlotService } from '../../../core/services/slot.service';
import { AppointmentService } from '../../../core/services/appointment.service';
import { DoctorService } from '../../../core/services/doctor.service';

@Component({
  selector: 'app-slot-picker',
  imports: [CommonModule, FormsModule],
  templateUrl: './slot-picker.html',
  styleUrl: './slot-picker.css'
})
export class SlotPicker implements OnInit {

  doctorId = 0;

  today = new Date().toISOString().split('T')[0];

  selectedDate = '';
  selectedSlot = '';

  doctor = {
    name: '',
    specialization: ''
  };

  slots: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private slotService: SlotService,
    private appointmentService: AppointmentService,
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.doctorId = Number(this.route.snapshot.paramMap.get('id'));

    this.loadDoctor();
    this.loadSlots();
  }

  loadDoctor() {
    this.doctorService.getDoctorById(this.doctorId).subscribe({
      next: (response) => {
        console.log('Doctor loaded:', response);

        this.doctor.name = response.fullName;
        this.doctor.specialization = response.specialization;
      },
      error: (error) => {
        console.error('Failed to load doctor:', error);
      }
    });
  }

  loadSlots() {

    if (!this.selectedDate) {
      return;
    }

    this.slotService.getSlots(this.doctorId, this.selectedDate).subscribe({
      next: (response) => {
        console.log('Slots loaded:', response);
        this.slots = response;
        this.selectedSlot = '';
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Failed to load slots:', error);
        this.slots = [];
      }
    });
  }

  dateChanged() {
    this.selectedSlot = '';
    this.loadSlots();
  }

  selectSlot(time: string) {
    this.selectedSlot = time;
  }

  confirmAppointment() {

    const patientId = Number(localStorage.getItem('patientId'));

    if (!patientId) {
      alert('Please login again.');
      return;
    }

    if (!this.selectedDate || !this.selectedSlot) {
      return;
    }

    this.appointmentService.bookAppointment(
      patientId,
      this.doctorId,
      this.selectedDate,
      this.selectedSlot
    ).subscribe({
      next: (response) => {
        console.log('Appointment booked:', response);
        alert('Appointment booked successfully!');
      },
      error: (error) => {
        console.error('Booking failed:', error);
        alert('Failed to book appointment.');
      }
    });
  }
}
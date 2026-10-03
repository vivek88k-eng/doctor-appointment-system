import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DoctorService } from '../../../core/services/doctor.service';
import { ScheduleService } from '../../../core/services/schedule.service';

@Component({
  selector: 'app-admin-doctors',
  imports: [FormsModule],
  templateUrl: './admin-doctors.html',
  styleUrl: './admin-doctors.css'
})
export class AdminDoctors implements OnInit {

  doctors: any[] = [];
  schedules: any[] = [];

  selectedDoctorId = 0;

  selectedDay = 1;
  startTime = '10:00';
  endTime = '14:00';
  slotMinutes = 30;
  editingDoctorId: number | null = null;
  editFullName='';
  editSpecialization='';
  editFee=0;
  editActive=true;

  editingScheduleId: number | null = null;
  newDoctorName='';
  newDoctorSpecialization='';
  newDoctorFee=0;
  newDoctorActive=true;
  showAddDoctorForm=false;

  constructor(
    private doctorService: DoctorService,
    private scheduleService: ScheduleService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    this.doctorService.getDoctors().subscribe({
      next: (response) => {

        console.log('Admin doctors loaded:', response);

        this.doctors = response;
        this.cdr.detectChanges();

        if (this.doctors.length > 0) {
          this.selectedDoctorId = this.doctors[0].id;
          this.loadSchedules();
        }

      },

      error: (error) => {
        console.error('Failed to load doctors:', error);
      }
    });

  }

  loadSchedules() {

    if (!this.selectedDoctorId) {
      return;
    }

    this.scheduleService
      .getSchedules(Number(this.selectedDoctorId))
      .subscribe({

        next: (response) => {

          console.log('Schedules loaded:', response);

          this.schedules = response;
          this.cdr.detectChanges();

        },

        error: (error) => {

          console.error('Failed to load schedules:', error);

          this.schedules = [];

        }

      });

  }

  addDoctor() {

    this.showAddDoctorForm=true;

  }
  saveNewDoctor() {
  if (!this.newDoctorName.trim() || !this.newDoctorSpecialization.trim()) {
    alert('Please enter doctor name and specialization.');
    return;
  }

  this.doctorService.addDoctor(
    this.newDoctorName.trim(),
    this.newDoctorSpecialization.trim(),
    Number(this.newDoctorFee),
    this.newDoctorActive
  ).subscribe({
    next: (response) => {
      console.log('Doctor added:', response);
      alert('Doctor added successfully.');

      this.showAddDoctorForm = false;

      this.newDoctorName = '';
      this.newDoctorSpecialization = '';
      this.newDoctorFee = 0;
      this.newDoctorActive = true;

      this.doctorService.getDoctors().subscribe({
        next: (doctors) => {
          this.doctors = doctors;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('Failed to reload doctors:', error);
        }
      });
    },
    error: (error) => {
      console.error('Failed to add doctor:', error);
      console.log('Status:', error.status);
      console.log('Response:', error.error);
      alert('Failed to add doctor. Check browser console.');
    }
  });
}

 editDoctor(doctorName: string) {

  const doctor = this.doctors.find(
    item => item.fullName === doctorName
  );

  if (!doctor) {
    return;
  }

  this.editingDoctorId = doctor.id;

  this.editFullName = doctor.fullName;
  this.editSpecialization = doctor.specialization;
  this.editFee = Number(doctor.consultationFee);
  this.editActive = doctor.active;
  

}
updateDoctor() {

  if (this.editingDoctorId === null) {
    return;
  }

  this.doctorService.updateDoctor(
    this.editingDoctorId,
    this.editFullName,
    this.editSpecialization,
    Number(this.editFee),
    this.editActive
  ).subscribe({

    next: (response) => {

      console.log('Doctor updated:', response);

      alert('Doctor updated successfully.');

      this.editingDoctorId = null;

      this.doctorService.getDoctors().subscribe({
        next: (doctors) => {

          this.doctors = doctors;

          this.cdr.detectChanges();

        },

        error: (error) => {
          console.error('Failed to reload doctors:', error);
        }
      });

    },

    error: (error) => {

      console.error('Failed to update doctor:', error);

      alert('Failed to update doctor.');

    }

  });

}

  saveSchedule() {

    if (!this.selectedDoctorId) {

      alert('Please select a doctor.');

      return;

    }

    const dayOfWeek = Number(this.selectedDay);
    const slotDuration = Number(this.slotMinutes);

    if (this.editingScheduleId !== null) {

      this.scheduleService.updateSchedule(
        this.editingScheduleId,
        dayOfWeek,
        this.startTime + ':00',
        this.endTime + ':00',
        slotDuration
      ).subscribe({

        next: (response) => {

          console.log('Schedule updated:', response);

          alert('Schedule updated successfully.');

          this.editingScheduleId = null;

          this.loadSchedules();

        },

        error: (error) => {

          console.error('UPDATE DOCTOR ERROR:', error);
          console.log('Status:', error.status);
          console.log('Response', error.error);

          alert('Failed to update schedule.');

        }

      });

      return;
    }

    this.scheduleService.createSchedule(
      Number(this.selectedDoctorId),
      dayOfWeek,
      this.startTime + ':00',
      this.endTime + ':00',
      slotDuration
    ).subscribe({

      next: (response) => {

        console.log('Schedule created:', response);

        alert('Schedule saved successfully.');

        this.loadSchedules();

      },

      error: (error) => {

        console.error('Failed to save schedule:', error);

        alert('Failed to save schedule.');

      }

    });

  }

  editSchedule(schedule: any) {

    this.editingScheduleId = schedule.id;

    this.selectedDay = Number(schedule.dayOfWeek);

    this.startTime = schedule.startTime.substring(0, 5);

    this.endTime = schedule.endTime.substring(0, 5);

    this.slotMinutes = Number(schedule.slotMinutes);

  }

  deleteSchedule(id: number) {

    const confirmed = confirm(
      'Are you sure you want to delete this schedule?'
    );

    if (!confirmed) {
      return;
    }

    this.scheduleService.deleteSchedule(id).subscribe({

      next: () => {

        console.log('Schedule deleted:', id);

        alert('Schedule deleted successfully.');

        if (this.editingScheduleId === id) {
          this.editingScheduleId = null;
        }

        this.loadSchedules();

      },

      error: (error) => {

        console.error('Failed to delete schedule:', error);

        alert('Failed to delete schedule.');

      }

    });

  }

}
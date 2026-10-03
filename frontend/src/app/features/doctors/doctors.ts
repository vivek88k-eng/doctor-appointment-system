import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { DoctorService } from '../../core/services/doctor.service';
import { ScheduleService } from '../../core/services/schedule.service';

@Component({
  selector: 'app-doctors',
  imports: [RouterLink, FormsModule],
  templateUrl: './doctors.html',
  styleUrl: './doctors.css'
})
export class Doctors implements OnInit {

  searchText = '';

  doctors: any[] = [];
  schedules: { [doctorId: number]: any[] }={};

  constructor(
    private doctorService: DoctorService,
    private scheduleService: ScheduleService,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {

    this.doctorService.getDoctors().subscribe({
      next: (response) => {
        console.log('Doctors loaded:', response);
        this.doctors = response;
        

this.doctors.forEach((doctor) => {
  this.scheduleService.getSchedules(doctor.id).subscribe({
    next: (response) => {
      this.schedules[doctor.id] = response;
      this.cdr.detectChanges();
    },
    error: (error) => {
      console.error(
        'Failed to load schedule for doctor:',
        doctor.id,
        error
      );

      this.schedules[doctor.id] = [];
    }
  });
});

this.cdr.detectChanges();
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Failed to load doctors:', error);
      }
    });

  }

  get filteredDoctors() {
    return this.doctors.filter(doctor =>
      doctor.specialization
        .toLowerCase()
        .includes(this.searchText.toLowerCase())
    );
  }

  searchDoctors() {
    this.searchText = this.searchText.trim();
  }
  getDayName(day: number): string {
  const days: { [key: number]: string } = {
    1: 'Monday',
    2: 'Tuesday',
    3: 'Wednesday',
    4: 'Thursday',
    5: 'Friday',
    6: 'Saturday',
    7: 'Sunday'
  };

  return days[day] || '';
}
getScheduleTime(schedule: any): string {
  const formatTime = (time: string): string => {
    const [hour, minute] = time.substring(0, 5).split(':').map(Number);

    const period = hour >= 12 ? 'PM' : 'AM';
    const displayHour = hour % 12 || 12;

    return `${displayHour}:${minute.toString().padStart(2, '0')} ${period}`;
  };

  return `${formatTime(schedule.startTime)} - ${formatTime(schedule.endTime)}`;
}
}
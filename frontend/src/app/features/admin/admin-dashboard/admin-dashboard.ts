import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';

import { DashboardService } from '../../../core/services/dashboard.service';
import { DoctorService } from '../../../core/services/doctor.service';

@Component({
  selector: 'app-admin-dashboard',
  imports: [RouterLink],
  templateUrl: './admin-dashboard.html',
  styleUrl: './admin-dashboard.css',
})
export class AdminDashboard implements OnInit {

  dashboard: any = {};
  totalDoctors = 0;

  constructor(
    private dashboardService: DashboardService,
    private doctorService: DoctorService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    this.dashboardService.getDashboard().subscribe({

      next: (response) => {

        console.log('Dashboard loaded:', response);

        this.dashboard = response;

        this.cdr.detectChanges();
      },

      error: (error) => {

        console.error(
          'Failed to load dashboard:',
          error
        );

      }
    });

    this.doctorService.getDoctors().subscribe({

      next: (response) => {

        this.totalDoctors = response.length;

        console.log(
          'Active doctors:',
          this.totalDoctors
        );

        this.cdr.detectChanges();
      },

      error: (error) => {

        console.error(
          'Failed to load doctors:',
          error
        );

      }
    });
  }
}
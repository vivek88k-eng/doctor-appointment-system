import { Routes } from '@angular/router';
import { Home } from './features/home/home';
import { Login } from './features/auth/login/login';
import { Register } from './features/auth/register/register';
import { Doctors } from './features/doctors/doctors';
import { SlotPicker } from './features/booking/slot-picker/slot-picker';
import { MyAppointments } from './features/appointments/my-appointments/my-appointments';
import { AdminDoctors } from './features/admin/admin-doctors/admin-doctors';
import { AdminDay } from './features/admin/admin-day/admin-day';
import { AdminDashboard } from './features/admin/admin-dashboard/admin-dashboard';

export const routes: Routes = [
    { path: '', component: Home },
    { path: 'login', component: Login },
    { path: 'register', component: Register },
    { path: 'doctors', component: Doctors },
    { path: 'doctors/:id/book', component: SlotPicker },
    { path: 'my-appointments', component: MyAppointments },
    { path: 'admin', component: AdminDashboard },
    { path: 'admin/doctors', component: AdminDoctors },
    { path: 'admin/day', component: AdminDay }
];
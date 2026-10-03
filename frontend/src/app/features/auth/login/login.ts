import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  email = '';
  password = '';
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {

    if (this.isLoading) {
      return;
    }

    this.isLoading = true;

    this.authService.login(this.email, this.password).subscribe({

      next: (response: any) => {

        console.log('Login successful:', response);

        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('fullName', response.fullName);
        localStorage.setItem('email', response.email);
        localStorage.setItem('patientId', response.id);

        this.isLoading = false;

        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/doctors']);
        }
      },

      error: (error) => {

        console.error('Login failed:', error);

        this.isLoading = false;

        alert(
          error?.error?.message ||
          'Login failed. Please check your email and password.'
        );
      }

    });
  }
}
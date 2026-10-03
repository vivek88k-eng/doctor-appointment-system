import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  imports: [RouterLink, FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrl: './register.css'
})
export class Register {

  name = '';
  email = '';
  password = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {
  }

  register() {

    this.authService.register(
      this.name,
      this.email,
      this.password,
      ''
    ).subscribe({

      next: (response) => {
        console.log('Registration successful:', response);

        alert('Registration successful! Please login.');

        this.router.navigate(['/login']);
      },

      error: (error) => {
        console.error('Registration failed:', error);

        alert('Registration failed. Please check your details.');
      }

    });
  }
}
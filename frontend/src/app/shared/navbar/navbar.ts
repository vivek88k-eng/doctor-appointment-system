import { Component, OnInit } from '@angular/core';
import { Router, RouterLink, NavigationEnd } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css'
})
export class Navbar implements OnInit {

  isLoggedIn = false;
  isAdmin=false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {

    this.checkLoginStatus();

    this.router.events
      .pipe(
        filter(event => event instanceof NavigationEnd)
      )
      .subscribe(() => {
        this.checkLoginStatus();
      });
  }

  checkLoginStatus() {
    const token= localStorage.getItem('token');
    const role = localStorage.getItem('role');
    this.isLoggedIn=!!token;
    this.isAdmin=!!token && role === 'ADMIN';
  }

  logout() {

    this.authService.logout();

    this.isLoggedIn = false;

    this.router.navigate(['/login']);
  }
}
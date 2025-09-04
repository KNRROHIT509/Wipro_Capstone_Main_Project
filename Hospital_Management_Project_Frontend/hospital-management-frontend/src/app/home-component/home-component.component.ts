import { Component, OnInit } from '@angular/core';
import { MaterialModule } from '../material.module';
import { Router, RouterLink, NavigationEnd } from '@angular/router';
import { StorageService } from '../auth/services/storage.service';
import { CommonModule } from '@angular/common';
import { FooterComponent } from "../modules/user/componets/footer/footer.component";
import { HerosectionComponent } from "../auth/components/login/herosection/herosection.component";
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [RouterLink, MaterialModule, CommonModule, FooterComponent, HerosectionComponent],
  templateUrl: './home-component.component.html',
  styleUrl: './home-component.component.css'
})
export class HomeComponentComponent implements OnInit {
  isAdminLoggedIn = false;
  isUserLoggedIn = false;
  isDoctorLoggedIn = false;
  isPatientLoggedIn = false;
  showHeroSection = true;

  constructor(private router: Router) {
    // Listen for route changes
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: any) => {
        this.setLoginFlags();
        const url = event.urlAfterRedirects;
        if (url.includes('/login') || url.includes('/signup')) {
          this.showHeroSection = false;
        } else {
          this.showHeroSection = !this.isUserLoggedIn && !this.isAdminLoggedIn;
        }
      });
  }

  ngOnInit(): void {
    // ✅ Set flags on component load as well
    this.setLoginFlags();
  }

  private setLoginFlags(): void {
    this.isAdminLoggedIn = StorageService.isAdminLoggedIn();
    this.isUserLoggedIn = StorageService.isUserLoggedIn();
    this.isDoctorLoggedIn = StorageService.isDoctorLoggedIn();
    this.isPatientLoggedIn = StorageService.isPatientLoggedIn();
  }

  logout() {
    StorageService.logout();
    this.router.navigateByUrl('/login');
    this.showHeroSection = false; // hide hero after logout
  }
}

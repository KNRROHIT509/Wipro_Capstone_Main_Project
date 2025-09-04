import { Component, OnInit } from '@angular/core';
import { HomeComponentComponent } from "../../../../home-component/home-component.component";
import { FooterComponent } from "../footer/footer.component";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AppointmentService, AppointmentRequest } from '../../services/appointment.service'; 
import { MaterialModule } from '../../../../material.module';
import { DashboardComponent } from '../dashboard/dashboard.component';

@Component({
  selector: 'app-appointment',
  imports: [
    HomeComponentComponent,
    FooterComponent,
    ReactiveFormsModule,
    MaterialModule,
    DashboardComponent
  ],
  templateUrl: './appointment.component.html',
  styleUrl: './appointment.component.css'
})
export class AppointmentComponent implements OnInit {
  appointmentForm!: FormGroup;

  constructor(private fb: FormBuilder, private appointmentService: AppointmentService) {}

  ngOnInit(): void {
    this.appointmentForm = this.fb.group({
      patientId: ['', Validators.required],
      doctorId: ['', Validators.required],
      appointmentDate: ['', Validators.required],
      status: ['PENDING', Validators.required],  // default value
      appointmentType: ['CHECKUP', Validators.required] // default value
    });
  }

  onSubmit() {
    if (this.appointmentForm.valid) {
      const appointmentData: AppointmentRequest = this.appointmentForm.value;
      this.appointmentService.saveAppointment(appointmentData).subscribe({
        next: (res) => {
          console.log('Appointment saved successfully:', res);
          alert('Appointment Request sent successfully!');
          this.appointmentForm.reset();
        },
        error: (err) => {
          console.error('Error saving appointment:', err);
          alert('Failed to save appointment.');
        }
      });
    }
  }
}

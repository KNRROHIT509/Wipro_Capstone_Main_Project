import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface AppointmentRequest {
  patientId: number;
  doctorId: number;
  appointmentDate: string;  // ISO format
  status: string;
  appointmentType: string;
}

export interface AppointmentResponse {
  appointmentId: number;
  patientId: number;
  doctorId: number;
  appointmentDate: string;
  status: string;
  appointmentType: string;
}

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  private apiUrl = 'http://localhost:8083/api/appointments';

  constructor(private http: HttpClient) {}

  saveAppointment(appointmentData: AppointmentRequest): Observable<AppointmentResponse> {
    return this.http.post<AppointmentResponse>(`${this.apiUrl}`, appointmentData);
  }

  getAllAppointments(): Observable<AppointmentResponse[]> {
    return this.http.get<AppointmentResponse[]>(`${this.apiUrl}/allAppointments`);
  }
}

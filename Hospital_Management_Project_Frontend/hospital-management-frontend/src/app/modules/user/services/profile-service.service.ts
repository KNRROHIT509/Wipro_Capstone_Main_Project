import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface MedicalRecord {
  id?: number;
  description: string;
  doctorName: string;
  visitedDate: string;   // ISO string for LocalDate
  medicines: string;
}

export interface Patient {
  id?: number;
  name: string;
  address: string;
  email: string;
  gender: string;
  dob: string;   // ISO string
  weight: number;
  height: number;
  medicalRecord?: MedicalRecord[];
}

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private patientUrl = 'http://localhost:8082/api/patients';

  private medicalUrl = 'http://localhost:8084/api/medicalrecords';

  constructor(private http: HttpClient) {}

  saveProfile(patient: Patient): Observable<Patient> {
    return this.http.post<Patient>(`${this.patientUrl}`, patient);
  }

  addMedicalRecord(record: MedicalRecord): Observable<MedicalRecord> {
    return this.http.post<MedicalRecord>(`${this.medicalUrl}/addMedicalRecord`, record);
  }

  getAllPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.patientUrl}/allPatients`);
  }

  getPatientById(id: number): Observable<Patient> {
    return this.http.get<Patient>(`${this.patientUrl}/getById/${id}`);
  }

  getAllMedicalRecords(): Observable<MedicalRecord[]> {
    return this.http.get<MedicalRecord[]>(`${this.medicalUrl}/allMedicalRecords`);
  }

  updateMedicalRecord(id: number, record: MedicalRecord): Observable<Patient> {
    return this.http.put<Patient>(`${this.medicalUrl}/update/${id}`, record);
  }

  deletePatient(id: number): Observable<string> {
    return this.http.delete(`${this.patientUrl}/delete/${id}`, { responseType: 'text' });
  }
}

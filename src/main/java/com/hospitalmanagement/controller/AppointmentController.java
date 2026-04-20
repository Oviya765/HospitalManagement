package com.hospitalmanagement.controller;

import com.hospitalmanagement.model.Appointment;
import com.hospitalmanagement.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('PATIENT', 'RECEPTION', 'CLINICIAN', 'ADMIN')")
    public ResponseEntity<List<Appointment>> getPatientAppointments(@PathVariable Long patientId) {
        return ResponseEntity.ok(appointmentService.getPatientAppointments(patientId));
    }

    @GetMapping("/clinician/{clinicianId}")
    @PreAuthorize("hasAnyRole('CLINICIAN', 'RECEPTION', 'ADMIN', 'MANAGER')")
    public ResponseEntity<List<Appointment>> getClinicianAppointments(@PathVariable Long clinicianId) {
        return ResponseEntity.ok(appointmentService.getClinicianAppointments(clinicianId));
    }

    @PostMapping("/book")
    @PreAuthorize("hasAnyRole('PATIENT', 'RECEPTION', 'ADMIN')")
    public ResponseEntity<Appointment> bookAppointment(
            @RequestParam Long patientId,
            @RequestParam Long clinicianId,
            @RequestParam Long createdById,
            @RequestBody Appointment appointment) {
        
        return ResponseEntity.ok(appointmentService.bookAppointment(appointment, patientId, clinicianId, createdById));
    }

    @PatchMapping("/{appId}/status")
    @PreAuthorize("hasAnyRole('RECEPTION', 'CLINICIAN', 'ADMIN')")
    public ResponseEntity<Appointment> updateStatus(
            @PathVariable Long appId,
            @RequestParam String status) {
        
        return ResponseEntity.ok(appointmentService.updateStatus(appId, status));
    }
}

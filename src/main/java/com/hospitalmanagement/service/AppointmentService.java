package com.hospitalmanagement.service;

import com.hospitalmanagement.model.Appointment;
import com.hospitalmanagement.model.Patient;
import com.hospitalmanagement.model.User;
import com.hospitalmanagement.repository.AppointmentRepository;
import com.hospitalmanagement.repository.PatientRepository;
import com.hospitalmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public List<Appointment> getPatientAppointments(Long patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    public List<Appointment> getClinicianAppointments(Long clinicianId) {
        return appointmentRepository.findByClinician_UserId(clinicianId);
    }

    @Transactional
    public Appointment bookAppointment(Appointment request, Long patientId, Long clinicianId, Long createdById) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        User clinician = userRepository.findById(clinicianId)
                .orElseThrow(() -> new RuntimeException("Clinician not found"));
        User creator = userRepository.findById(createdById)
                .orElseThrow(() -> new RuntimeException("Creator user not found"));

        // Deterministic allocation check to prevent double booking
        boolean isDoubleBooked = appointmentRepository.isClinicianDoubleBooked(clinicianId, request.getStartAt(), request.getEndAt());
        if (isDoubleBooked) {
            throw new RuntimeException("Clinician is already booked for this time slot.");
        }

        request.setPatient(patient);
        request.setClinician(clinician);
        request.setCreatedBy(creator);
        request.setStatus("SCHEDULED");
        request.setCreatedAt(LocalDateTime.now());

        return appointmentRepository.save(request);
    }

    @Transactional
    public Appointment updateStatus(Long appId, String status) {
        Appointment appointment = appointmentRepository.findById(appId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus(status);
        return appointmentRepository.save(appointment);
    }
}

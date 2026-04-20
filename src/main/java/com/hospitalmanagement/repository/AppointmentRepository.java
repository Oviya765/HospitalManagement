package com.hospitalmanagement.repository;

import com.hospitalmanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatient_PatientId(Long patientId);
    List<Appointment> findByClinician_UserId(Long clinicianId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Appointment a " +
           "WHERE a.clinician.userId = :clinicianId " +
           "AND a.status != 'CANCELLED' " +
           "AND ((a.startAt <= :startAt AND a.endAt > :startAt) OR " +
           "(a.startAt < :endAt AND a.endAt >= :endAt))")
    boolean isClinicianDoubleBooked(@Param("clinicianId") Long clinicianId, 
                                    @Param("startAt") LocalDateTime startAt, 
                                    @Param("endAt") LocalDateTime endAt);
}

package com.scm.controllers;

import com.scm.model.Appointment;
import com.scm.repository.AppointmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "http://localhost:5173")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@Valid @RequestBody Appointment appointment) {
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return ResponseEntity.ok(savedAppointment);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> allAppointments = appointmentRepository.findAll();
        return ResponseEntity.ok(allAppointments);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        if (!appointmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        appointmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}

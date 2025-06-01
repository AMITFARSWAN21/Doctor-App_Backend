package com.scm.controllers;

import com.scm.model.Report;
import com.scm.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/summarize")
    public ResponseEntity<?> uploadReport(@RequestParam("file") MultipartFile file) {
        try {
            Report report = reportService.processAndSaveReport(file);
            return ResponseEntity.ok().body(report);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to process PDF: " + e.getMessage());
        }
    }
}
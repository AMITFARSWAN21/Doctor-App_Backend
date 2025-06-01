package com.scm.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.scm.config.GeminiConfig;
import com.scm.model.Report;
import com.scm.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;

    // Updated API endpoint
    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";



    public Report processAndSaveReport(MultipartFile file) throws IOException {
        String text = extractTextFromPDF(file);
        String summary = callGeminiAPI("Summarize this medical report: " + text);
        String remedies = callGeminiAPI("Suggest remedies for the following medical condition: " + text);

        Report report = Report.builder()
                .pdfText(text)
                .summary(summary)
                .remedies(remedies)
                .build();

        return reportRepository.save(report);
    }

    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        }
    }

    private String callGeminiAPI(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the correct request body
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonArray parts = new JsonArray();
        parts.add(textPart);

        JsonObject content = new JsonObject();
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject requestBody = new JsonObject();
        requestBody.add("contents", contents);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        String url = GEMINI_API_URL + geminiConfig.getApiKey();

        try {
            String response = restTemplate.postForObject(url, request, String.class);

            JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response).getAsJsonObject();

            return jsonResponse.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }



}
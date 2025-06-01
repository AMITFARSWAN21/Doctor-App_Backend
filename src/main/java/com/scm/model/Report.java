package com.scm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reports")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private String pdfText;

    @Lob
    private String summary;

    @Lob
    private String remedies;
}

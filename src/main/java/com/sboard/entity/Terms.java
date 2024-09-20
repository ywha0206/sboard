package com.sboard.entity;

import com.sboard.dto.TermsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="terms")
public class Terms {
    @Id
    @Column(columnDefinition = "text(65535)", nullable = false)
    private String terms;
    @Column(columnDefinition = "text(65535)", nullable = false)
    private String privacy;

    public TermsDTO toDTO() {
        return TermsDTO
                .builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}

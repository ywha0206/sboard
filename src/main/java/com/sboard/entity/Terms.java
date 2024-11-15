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
    @Column(length = 255)
    private String terms;
    @Column(length = 255)
    private String privacy;

    public TermsDTO toDTO() {
        return TermsDTO
                .builder()
                .terms(terms)
                .privacy(privacy)
                .build();
    }
}

package com.sboard.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {

    private int no;
    private int parent;
    private String content;
    private String writer;
    private String regip;
    private LocalDateTime rdate;

    private UserDTO user;

}
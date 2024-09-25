package com.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="comment")
public class Comment {

    @Id
    private int no;

    private int parent;
    private String content;
    //private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;


    @ManyToOne
    @JoinColumn(name = "writer")
    private User user;

}
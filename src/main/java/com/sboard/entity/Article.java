package com.sboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    private String cate;
    private String title;
    private String content;
    private int comment;
    private int file;
    private int hit;
    private String writer;
    private String regip;

    @CreationTimestamp
    private LocalDateTime rdate;
    
    
    //추가필드(조인을 위해 추가하는 필드 > 속성에서 제외시켜야 함 @Transient)
    @Transient //엔티티 속성에서 제외시키는 어노테이션 , 테이블의 컬럼 생성 안 함
    private String nick;

    /*
    DTO 변환 메서드 대신 ModelMapper 사용

    public ArticleDTO toDTO(){
        return ArticleDTO.builder()
                .no(no)
                .cate(cate)
                .title(title)
                .build();
    }
    */

}

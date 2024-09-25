package com.sboard.controller;

import com.sboard.dto.CommentDTO;
import com.sboard.entity.Comment;
import com.sboard.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/comment")
    public ResponseEntity<Comment> insertComment(@RequestBody CommentDTO commentDTO, HttpServletRequest req) {
        String regip = req.getRemoteAddr();
        commentDTO.setRegip(regip);
        Comment comment = commentService.insertComment(commentDTO);

        return ResponseEntity.ok().body(comment);
    }

    @DeleteMapping("/comment/{no}")
    public ResponseEntity deleteComment(@PathVariable("no")int no){

        ResponseEntity<?> result = commentService.deleteComment(no);
        log.info("result : "+result);
        return result;

    }

}

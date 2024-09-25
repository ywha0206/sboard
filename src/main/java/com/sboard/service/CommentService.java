package com.sboard.service;

import com.sboard.dto.CommentDTO;
import com.sboard.entity.Comment;
import com.sboard.entity.User;
import com.sboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public Comment insertComment(CommentDTO commentDTO) {
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        User user = User.builder()
                        .uid(commentDTO.getWriter())
                        .build();
        comment.setUser(user);
        return commentRepository.save(comment);
    }


    public void selectComment() {}
    public List<CommentDTO> selectCommentAll(int parent) {
        List<Comment> comments= commentRepository.findAllByParent(parent);
        List<CommentDTO> commentDTOS = new ArrayList<>();

        for (Comment comment : comments) {
            commentDTOS.add(modelMapper.map(comment, CommentDTO.class));
        }

        return commentDTOS;
    }
    public void updateComment(CommentDTO commentDTO) {}


    public ResponseEntity<?> deleteComment(int no) {

        try {
            commentRepository.deleteById(no);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

package com.lnct.miniblog.controller;

import com.lnct.miniblog.model.Comment;
import com.lnct.miniblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Get all comments for a specific post
    @GetMapping
    public List<Comment> getCommentsByPostId(@PathVariable Integer postId) {
        return commentService.getCommentsByPostId(postId);
    }

    // Create a new comment (only if user and post exist)
    @PostMapping
    public ResponseEntity<String> createComment(@PathVariable Integer postId, @RequestBody Comment comment) {
        try {
            comment.setPostId(postId);  // Set the postId in the comment
            Comment createdComment = commentService.createComment(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing comment
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Integer postId, @PathVariable Integer id, @RequestBody Comment commentDetails) {
        commentDetails.setPostId(postId);  // Ensure postId is set
        Comment updatedComment = commentService.updateComment(id, commentDetails);
        return updatedComment != null ? ResponseEntity.ok(updatedComment) : ResponseEntity.notFound().build();
    }

    // Delete a comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Integer postId, @PathVariable Integer id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}

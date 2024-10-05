package com.lnct.miniblog.controller;

import com.lnct.miniblog.model.Comment;
import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.service.CommentService;
import com.lnct.miniblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    // Get all comments for a specific post
    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Integer postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comments);
    }

    // Create a new comment (automatically map username from token)
    @PostMapping
    public ResponseEntity<Map<String, Object>> createComment(@PathVariable Integer postId, @RequestBody Comment comment) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Ensure the post exists
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Post not found."));
        }

        // Set post reference and username
        comment.setPost(post);
        comment.setUsername(username);

        Comment createdComment = commentService.createComment(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "commentId", createdComment.getId(),
                "postId", createdComment.getPost().getId(),
                "username", createdComment.getUsername(),
                "content", createdComment.getContent()
        ));
    }

    // Update an existing comment
    @PutMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Integer postId, @PathVariable Integer commentId, @RequestBody Comment commentDetails) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Ensure the post exists
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Post not found."));
        }

        // Find the existing comment
        Comment existingComment = commentService.getCommentById(commentId);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }

        // Ensure the comment belongs to the current user
        if (!existingComment.getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not authorized to update this comment."));
        }

        // Update the comment details
        existingComment.setContent(commentDetails.getContent());
        existingComment.setUpdatedAt(commentDetails.getUpdatedAt());

        Comment updatedComment = commentService.updateComment(commentId, existingComment);

        return ResponseEntity.ok(Map.of(
                "commentId", updatedComment.getId(),
                "postId", updatedComment.getPost().getId(),
                "username", updatedComment.getUsername(),
                "content", updatedComment.getContent(),
                "updatedAt", updatedComment.getUpdatedAt()
        ));
    }

    // Get a specific comment by its ID
    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Integer postId, @PathVariable Integer commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment != null && comment.getPost().getId().equals(postId)) {
            return ResponseEntity.ok(comment);
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a comment
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Integer postId, @PathVariable Integer commentId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Ensure the post exists
        Post post = postService.getPostById(postId);
        if (post == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Post not found."));
        }

        // Find the existing comment
        Comment existingComment = commentService.getCommentById(commentId);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }

        // Ensure the comment belongs to the current user
        if (!existingComment.getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "You are not authorized to delete this comment."));
        }

        // Delete the comment
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(Map.of("message", "Comment deleted successfully."));
    }
}

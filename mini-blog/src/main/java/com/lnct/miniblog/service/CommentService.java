package com.lnct.miniblog.service;

import com.lnct.miniblog.model.Comment;
import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.model.User;
import com.lnct.miniblog.repository.CommentRepository;
import com.lnct.miniblog.repository.PostRepository;
import com.lnct.miniblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    // Get all comments for a post
    public List<Comment> getCommentsByPostId(Integer postId) {
        return commentRepository.findByPostId(postId);
    }

    // Create a new comment (only if user and post exist)
    public Comment createComment(Comment comment) throws IllegalArgumentException {
        // Check if the user exists
        User user = userRepository.findByUsername(comment.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("Cannot create comment. User with username '" + comment.getUsername() + "' does not exist.");
        }

        // Check if the post exists
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        if (post == null) {
            throw new IllegalArgumentException("Cannot create comment. Post with ID '" + comment.getPostId() + "' does not exist.");
        }

        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // Update an existing comment
    public Comment updateComment(Integer id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment != null) {
            comment.setContent(commentDetails.getContent());
            comment.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(comment);
        }
        return null;
    }

    // Delete a comment
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}

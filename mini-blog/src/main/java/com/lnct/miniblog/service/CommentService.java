package com.lnct.miniblog.service;

import com.lnct.miniblog.model.Comment;
import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.model.User;
import com.lnct.miniblog.repository.CommentRepository;
import com.lnct.miniblog.repository.PostRepository;
import com.lnct.miniblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("Cannot create comment. User with username '" + username + "' does not exist.");
        }

        Optional<Post> optionalPost = postRepository.findById(comment.getPost().getId());
        if (!optionalPost.isPresent()) {
            throw new IllegalArgumentException("Cannot create comment. Post with ID '" + comment.getPost().getId() + "' does not exist.");
        }

        // Associate the post with the comment
        comment.setPost(optionalPost.get());
        comment.setUsername(username);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // Get a comment by its ID
    public Comment getCommentById(Integer commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        return comment.orElse(null);
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

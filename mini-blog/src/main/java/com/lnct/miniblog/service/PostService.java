package com.lnct.miniblog.service;

import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.model.User;
import com.lnct.miniblog.repository.PostRepository;
import com.lnct.miniblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all posts
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Get post by ID
    public Post getPostById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    // Get all posts for a specific user
    public List<Post> getPostsByUsername(String username) {
        return postRepository.findByUsername(username);
    }

    // Create a new post (automatically set the username from the JWT token)
    public Post createPost(Post post) throws IllegalArgumentException {
        // Get the username from the JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new IllegalArgumentException("Cannot create post. User with username '" + username + "' does not exist.");
        }

        // Set the username and timestamps for the post
        post.setUsername(username);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    // Update an existing post
    public Post updatePost(Integer id, Post postDetails) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post existingPost = optionalPost.get();

            // Update only the fields that are present in the request
            if (postDetails.getTitle() != null) {
                existingPost.setTitle(postDetails.getTitle());
            }
            if (postDetails.getContent() != null) {
                existingPost.setContent(postDetails.getContent());
            }
            if (postDetails.getTags() != null) {
                existingPost.setTags(postDetails.getTags());
            }

            // Update the timestamp
            existingPost.setUpdatedAt(LocalDateTime.now());

            return postRepository.save(existingPost);
        }
        return null;
    }

    // Delete a post
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
}

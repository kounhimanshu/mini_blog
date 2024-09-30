package com.lnct.miniblog.service;

import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.model.User;
import com.lnct.miniblog.repository.PostRepository;
import com.lnct.miniblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    // Create a new post (only if the user exists)
    public Post createPost(Post post) throws IllegalArgumentException {
        User user = userRepository.findByUsername(post.getUsername());
        if (user == null) {
            throw new IllegalArgumentException("Cannot create post. User with username '" + post.getUsername() + "' does not exist.");
        }

        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // Update an existing post
    public Post updatePost(Integer id, Post postDetails) {
        Post post = getPostById(id);
        if (post != null) {
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setTags(postDetails.getTags());
            post.setUpdatedAt(LocalDateTime.now());
            return postRepository.save(post);
        }
        return null;
    }

    // Delete a post
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }
}

package com.lnct.miniblog.controller;

import com.lnct.miniblog.model.Post;
import com.lnct.miniblog.repository.PostRepository;
import com.lnct.miniblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    // Get all posts
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // Get post by ID
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Integer id) {
        Post post = postService.getPostById(id);
        return post != null ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();
    }

    // Create a new post (automatically map username from token)
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // Retrieve the username from the JWT token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Set the username in the post before saving
        post.setUsername(username);

        // Create and save the post
        Post createdPost = postService.createPost(post);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    // Update an existing post
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Integer id, @RequestBody Post postDetails) {
        // Ensure the user is the owner of the post or has permission to update
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Post existingPost = postService.getPostById(id);
        if (existingPost == null || !existingPost.getUsername().equals(username)) {
            return ResponseEntity.status(403).build();  // Forbidden if the post doesn't belong to the user
        }

        Post updatedPost = postService.updatePost(id, postDetails);
        return updatedPost != null ? ResponseEntity.ok(updatedPost) : ResponseEntity.notFound().build();
    }

    // Get all posts by username
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Post>> getPostsByUsername(@PathVariable String username) {
        List<Post> posts = postRepository.findByUsername(username);
        return ResponseEntity.ok(posts);
    }

    // Delete a post
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}

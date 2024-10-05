package com.lnct.miniblog.repository;

import com.lnct.miniblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // Find all posts by a specific username
    List<Post> findByUsername(String username);
}

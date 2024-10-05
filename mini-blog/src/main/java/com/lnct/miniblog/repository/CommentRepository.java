package com.lnct.miniblog.repository;

import com.lnct.miniblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // Find all comments for a specific post by postId
    List<Comment> findByPostId(Integer postId);
}

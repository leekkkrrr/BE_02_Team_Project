package com.b2.supercoding_prj01.repository;

import com.b2.supercoding_prj01.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    Optional<CommentsEntity> findByPostId(Long postId);
}

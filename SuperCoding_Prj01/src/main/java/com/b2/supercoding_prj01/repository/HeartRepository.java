package com.b2.supercoding_prj01.repository;

import com.b2.supercoding_prj01.entity.CommentsEntity;
import com.b2.supercoding_prj01.entity.HeartEntity;
import com.b2.supercoding_prj01.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HeartRepository extends JpaRepository<HeartEntity, Long> {

    Optional<HeartEntity> findByUser(UserEntity user);

    void deleteByUser(UserEntity user); // ID를 사용하여 엔티티 정보 삭제


    List<HeartEntity> findByComments(CommentsEntity comments);
}

package com.b2.supercoding_prj01.web.controller;

import com.b2.supercoding_prj01.entity.CommentsEntity;
import com.b2.supercoding_prj01.entity.HeartEntity;
import com.b2.supercoding_prj01.repository.CommentsRepository;
import com.b2.supercoding_prj01.repository.HeartRepository;
import com.b2.supercoding_prj01.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsRepository commentsRepository;
    private final HeartRepository heartRepository;
    private final HeartService heartService;




    // 해당 댓글에 좋아요 추가
    @Transactional
    @PostMapping("/{postId}/heart")
    public ResponseEntity<String> addHeart(@PathVariable Long postId,
                                        @RequestParam Long userId){
        return heartService.clickHeart(postId, userId);
    }

    // 해당 댓글에 좋아요 정보
    @GetMapping("/{postId}/heart")
    public List<HeartEntity> findHeartToPostId(@PathVariable Long postId){
        Optional<CommentsEntity> comments = commentsRepository.findByPostId(postId);

        return comments.map(heartRepository::findByComments).orElse(null);
//      = return comments.map(commentsEntity -> heartRepository.findByComments(commentsEntity)).orElse(null);
//      = if(comments.isPresent())
//            return heartRepository.findByComments(comments.get());
//        else return null;
    }
}

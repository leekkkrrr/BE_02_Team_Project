package com.b2.supercoding_prj01.web.controller;

import com.b2.supercoding_prj01.dto.CommentsDto;
import com.b2.supercoding_prj01.service.CommentsService;
import com.b2.supercoding_prj01.entity.CommentsEntity;
import com.b2.supercoding_prj01.entity.HeartEntity;
import com.b2.supercoding_prj01.repository.CommentsRepository;
import com.b2.supercoding_prj01.repository.HeartRepository;
import com.b2.supercoding_prj01.service.HeartService;
import com.b2.supercoding_prj01.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsRepository commentsRepository;
    private final HeartRepository heartRepository;
    private final HeartService heartService;
    private final JwtService jwtService;
    private final CommentsService commentsService;

    @GetMapping("")
    public List<CommentsDto> findAll(){
        List<CommentsEntity> commentsEntities = commentsService.findAll();
        return commentsEntities.stream()
                .map(CommentsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{boardId}")
    public List<CommentsDto> findByBoardId(@PathVariable long boardId){
        List<CommentsEntity> commentsEntities = commentsService.findByAllBoardId(boardId);
        return commentsEntities.stream()
                .map(CommentsDto::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto, @RequestHeader("TOKEN") String token) {
         String author = jwtService.extractUserId(token);
//       String author = commentsDto.getAuthor();
         commentsDto.setAuthor(author);

        commentsService.saveComment(commentsDto);
        return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적으로 작성되었습니다.");
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updateComment(@PathVariable long postId, @RequestBody CommentsDto commentsDto, @RequestHeader("TOKEN") String token) {
        String author = jwtService.extractUserId(token);

        if(author.equals(commentsDto.getAuthor())){
            commentsService.updateComment(commentsDto, postId);
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적으로 수정되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("수정 가능한 댓글이 없습니다.");
        }

    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @RequestBody CommentsDto commentsDto, @RequestHeader("TOKEN") String token) {
        String author = jwtService.extractUserId(token);

        if(author.equals(commentsDto.getAuthor())){
            commentsService.deleteComment(commentsDto, postId);
            return ResponseEntity.status(HttpStatus.OK).body("댓글이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("삭제 가능한 댓글이 없습니다.");
        }
    }
  
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

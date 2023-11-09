package com.b2.supercoding_prj01.service;

import com.b2.supercoding_prj01.dto.CommentsDto;
import com.b2.supercoding_prj01.entity.BoardEntity;
import com.b2.supercoding_prj01.entity.CommentsEntity;
import com.b2.supercoding_prj01.repository.BoardRepository;
import com.b2.supercoding_prj01.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentsService {

    private final CommentsRepository commentsRepository;
    private final BoardRepository boardRepository;

    public List<CommentsEntity> findAll() {
        return commentsRepository.findAll();
    }

    public List<CommentsEntity> findByAllBoardId(long boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시판을 찾을 수 없습니다. boardId=" + boardId));
        return commentsRepository.findByBoard(board);
    }

    public void saveComment(CommentsDto commentsDto) {
        BoardEntity board = boardRepository.findById(commentsDto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시물을 찾을 수 없습니다."));

        CommentsEntity commentsEntity = CommentsEntity.builder()
                .board(board)
                .author(commentsDto.getAuthor())
                .content(commentsDto.getContent())
                .build();

        commentsRepository.save(commentsEntity);
    }
    public void updateComment(CommentsDto commentsDto, long postId) {
        Optional<CommentsEntity> existingComment = commentsRepository.findByPostId(postId);
        if (existingComment.isPresent()) {
            CommentsEntity commentsEntity = existingComment.get();
            commentsEntity.setContent(commentsDto.getContent());
            commentsRepository.save(commentsEntity);
        }
        else {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }
    public void deleteComment(CommentsDto commentsDto, long postId) {
        Optional<CommentsEntity> existingComment = commentsRepository.findByPostId(postId);
        if (existingComment.isPresent()) {
            commentsRepository.deleteById(postId);
        }
        else {
            throw new EntityNotFoundException("댓글을 찾을 수 없습니다.");
        }
    }

}

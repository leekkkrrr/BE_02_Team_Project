package com.b2.supercoding_prj01.dto;

import com.b2.supercoding_prj01.entity.CommentsEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CommentsDto {
    @JsonProperty("id")
    private Long postId;

    @NonNull
    private String content;

    private String author;

    @JsonProperty("board_id")
    private Long boardId;

    @JsonProperty("created_at")
    private Timestamp createAt;


}

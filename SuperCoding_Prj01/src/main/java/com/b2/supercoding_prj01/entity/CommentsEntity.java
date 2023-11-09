package com.b2.supercoding_prj01.entity;

import com.b2.supercoding_prj01.dto.CommentsDto;
import lombok.AllArgsConstructor;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_idx")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_idx")
    private BoardEntity board;

    @OneToOne
    @JoinColumn(name = "user_idx")
    private UserEntity user;

    @Column(name = "content")
    private String content;

    @Column(name = "author")
    private String author;


@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;


    private Integer heart;

    public void setHeart(Integer heart) {
        this.heart = heart;
    }
}

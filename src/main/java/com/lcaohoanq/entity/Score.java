package com.lcaohoanq.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder({ "id", "last_score", "max_score", "created_at", "updated_at", "user"})
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", nullable = false)
    private Long id;

    @Column(name = "last_score", nullable = false)
    private int last_score;

    @Column(name = "max_score", nullable = false)
    private int max_score;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id") // Foreign key column in Score table
    private User user;

    @NotNull
    @Column(name="created_at", nullable = false)
    private String created_at;

    @NotNull
    @Column(name="updated_at", nullable = false)
    private String updated_at;

    public Score(int last_score, int max_score, String created_at, String updated_at, User user) {
        this.last_score = last_score;
        this.max_score = max_score;
        this.created_at = created_at;
        this.updated_at = updated_at;
        if (user != null) {
            this.id = user.getId();
            this.setUser(user);
        }
    }

}

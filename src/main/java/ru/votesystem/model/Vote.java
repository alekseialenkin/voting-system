package ru.votesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;


@Entity
@Table(name = "VOTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "restaurant_id", "voted"}, name = "vote_unique_user_restaurant_idx"), @UniqueConstraint(columnNames = {"user_id", "voted"}, name = "vote_unique_user_date_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString(callSuper = true)
public class Vote extends AbstractBaseEntity {
    @Column(name = "voted", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime voted = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ToString.Exclude
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    @ToString.Exclude
    private User user;

    public Vote(User user, Restaurant restaurant, LocalDateTime voted) {
        this.user = user;
        this.restaurant = restaurant;
        this.voted = voted;
    }

    public Vote(Integer id, LocalDateTime voted) {
        super(id);
        this.voted = voted;
    }
}

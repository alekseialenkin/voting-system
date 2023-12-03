package ru.votesystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "VOTE", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "restaurant_id"}, name = "vote_unique_user_restaurant_idx"))
public class Vote extends AbstractBaseEntity {
    @Column(name = "voted", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime voted = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public Vote() {
    }

    public Vote(User user, Restaurant restaurant, LocalDateTime voted) {
        this.user = user;
        this.restaurant = restaurant;
        this.voted = voted;
    }

    public Vote(Integer id, LocalDateTime voted) {
        super(id);
        this.voted = voted;
    }

    public LocalDateTime getVoted() {
        return voted;
    }

    public void setVoted(LocalDateTime voted) {
        this.voted = voted;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voted=" + voted +
                '}';
    }
}

package ru.votesystem.to;

import lombok.*;
import org.springframework.util.Assert;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public abstract class BaseTo {
    protected Integer id;

    public void setId(Integer id) {
        this.id = id;
    }

    public int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}

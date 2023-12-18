package ru.votesystem;

import ru.votesystem.model.Vote;

import java.time.*;

import static ru.votesystem.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final int VOTE1_ID = START_SEQ + 9;
    public static final Clock BEFORE_DEAD_LINE = Clock.fixed(LocalDateTime.of(LocalDate.now(),
                    LocalTime.of(10, 15))
            .toInstant(ZoneOffset.UTC), ZoneOffset.UTC);

    public static final Clock AFTER_DEAD_LINE = Clock.fixed(LocalDateTime.of(LocalDate.now(),
                    LocalTime.of(11, 15))
            .toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
}

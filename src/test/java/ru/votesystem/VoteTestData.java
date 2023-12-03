package ru.votesystem;

import ru.votesystem.model.Vote;

import java.time.LocalDateTime;

import static ru.votesystem.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");
    public static final int VOTE1_ID = START_SEQ + 9;
    public static final Vote VOTE_DEAD_LINE = new Vote(null, LocalDateTime.parse("2020-01-30T10:00:00"));
    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDateTime.parse("2023-12-01T10:00:00"));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, LocalDateTime.parse("2023-12-01T10:00:00"));

    public static Vote getNew() {
        return new Vote(null, LocalDateTime.parse("2023-12-01T10:00:00"));
    }
}

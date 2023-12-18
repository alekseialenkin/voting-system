package ru.votesystem.error;

public class VoteDeadLineException extends RuntimeException {
    public VoteDeadLineException(String message) {
        super(message);
    }
}

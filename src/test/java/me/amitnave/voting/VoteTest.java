package me.amitnave.voting;

import me.amitnave.voting.databaseObjects.Vote;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VoteTest {

    @Test
    void insert() throws SQLException {
        Vote vote=new Vote(1,1, Vote.AGAINST);
        vote.insert();
        Vote vote1=new Vote(1);
        assertTrue(vote.equals(vote1));
    }
}
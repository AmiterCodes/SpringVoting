package me.amitnave.voting;

import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Vote;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LawTest {

    @Test
    void insert() throws SQLException {
        Law law = new Law("g", 7, Law.failed, Law.dateFormat.format(new Date(System.currentTimeMillis())), true, false);
        law.insert();
    }

    @Test
    void name() throws SQLException, ParseException {
        Law law = new Law("g", 7, Law.failed, Law.dateFormat.format(new Date(System.currentTimeMillis())), true, false);
        law.insert();
        Law law1=new Law(3);
        assertTrue(law1.equals(law));
    }

    @Test
    void updateStatus() throws SQLException, ParseException {
        int id=369;
        Law law=new Law("g",7, Law.inProcess,"2020-08-07 13:13:13",true,true);
        law.insert();
        Vote vote=new Vote(id,23, Vote.FOR);
        vote.insert();
        vote=new Vote(id,1, Vote.FOR);
        vote.insert();
        vote=new Vote(id, 3, Vote.AGAINST);
        vote.insert();
        law.updateStatus();
        assertTrue(law.getStatus()== Law.inProcess);
    }
    @Test
    void updateStatus1() throws SQLException, ParseException {
        Law law=new Law("g",7, Law.inProcess,"2008-07-07 13:13:13",true,true);
        Vote vote=new Vote(355,23, Vote.FOR);
        vote.insert();
        vote=new Vote(355,1, Vote.AGAINST);
        vote.insert();
        vote=new Vote(355, 3, Vote.AGAINST);
        law.updateStatus();
        assertTrue(law.getStatus()== Law.failed);
    }
    @Test
    void cancel() throws SQLException {
        Law law=new Law("g",7, Law.inProcess,"2008-07-07 13:13:13",true,true);
        law.insert();
        law.cancel();
    }
}
package me.amitnave.voting;

import me.amitnave.voting.databaseObjects.Appeal;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class AppealTest {

    @Test
    void insert() throws SQLException, ParseException {
        Appeal appeal=new Appeal(5,5,"g");
        appeal.insert();
        appeal.presidentDecision(true,"sfg");
    }

    @Test
    void presidentDecision() {

    }
}
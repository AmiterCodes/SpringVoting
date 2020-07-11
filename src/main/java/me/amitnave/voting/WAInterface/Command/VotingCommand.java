package me.amitnave.voting.WAInterface.Command;

import me.amitnave.voting.WAInterface.Message.MessageToSend;

import java.sql.SQLException;
import java.text.ParseException;

public interface VotingCommand {
    void DatabaseAction() throws SQLException, ParseException;
    MessageToSend message() throws SQLException, ParseException;
}

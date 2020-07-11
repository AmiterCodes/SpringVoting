package me.amitnave.voting.WAInterface.Command;

import me.amitnave.voting.WAInterface.Message.Message;

import java.sql.SQLException;
import java.text.ParseException;

public interface CommandParser {
    boolean isLegalCommand(Message message) throws SQLException;
    VotingCommand getCommand() throws SQLException, ParseException;
}

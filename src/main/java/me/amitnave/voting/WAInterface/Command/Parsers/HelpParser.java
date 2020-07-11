package me.amitnave.voting.WAInterface.Command.Parsers;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Help;
import me.amitnave.voting.WAInterface.Message.Message;

import java.sql.SQLException;
import java.text.ParseException;

public class HelpParser implements CommandParser {
    private String asker;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        asker=message.getChatID();
        return message.isPrivate()&&message.getContent().equals("#עזרה");
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new Help(asker);
    }
}

package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetPassesLaws;
import me.amitnave.voting.WAInterface.Message.Message;

import java.sql.SQLException;
import java.text.ParseException;

public class GetPassedLawsParser implements CommandParser {
    private String askerID;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        askerID=message.getSender();
        if (!message.isPrivate()) return false;
        return message.getContent().equals("#חוקים שעברו");
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new GetPassesLaws(askerID);
    }
}

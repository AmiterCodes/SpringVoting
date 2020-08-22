package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetStatusLaws;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;

public class GetStatusLawsParser implements CommandParser {
    private String askerID;
    private int lawStatus;
    private String param;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        askerID=message.getSender();
        if (!message.isPrivate()) return false;
        if(!message.getContent().startsWith("#חוקים ")) return false;
        String content = message.getContent().replaceFirst("#חוקים ", "");
        if(content.equals("שעברו")) lawStatus = Law.passed;
        else if(content.equals("שלא עברו")) lawStatus = Law.failed;
        else if(content.equals("שנפסלו")) lawStatus = Law.invalidated;
        else if(content.equals("שעדיין בהצבעה")) lawStatus = Law.inProcess;
        else return false;
        param = content;
        return true;
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new GetStatusLaws(askerID, lawStatus, param);
    }
}

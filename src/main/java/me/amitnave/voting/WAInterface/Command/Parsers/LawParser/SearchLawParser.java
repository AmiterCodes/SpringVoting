package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.SearchLaw;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class SearchLawParser implements CommandParser {
    private String askerNum;
    private String searchTerm;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if (!message.isPrivate()) return false;
        askerNum=message.getChatID();
        String s=message.getContent();
        if (!s.startsWith("#'")) return false;
        try {
            searchTerm = s.substring(2, s.length() - 1);
            if(!s.endsWith("'")) return false;
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new SearchLaw(askerNum, searchTerm);
    }
}

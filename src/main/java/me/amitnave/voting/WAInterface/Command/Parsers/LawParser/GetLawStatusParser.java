package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetLawStatus;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;

public class GetLawStatusParser implements CommandParser {
    private int lawID;
    private String askerNum;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        if (!message.isPrivate()) return false;
        askerNum=message.getChatID();
        String s=message.getContent();
        if (!s.startsWith("מצב חוק #")) return false;
        try {
            lawID=Integer.parseInt(s.substring(s.indexOf('#')+1));
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new  GetLawStatus(new Law(lawID), askerNum);
    }


}

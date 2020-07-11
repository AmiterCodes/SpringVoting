package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.CancelLaw;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;

public class CancelLawParser implements CommandParser {
    private Law law;

    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if (!message.getContent().startsWith("ביטול חוק #")) return false;
        try {
            String content = message.getContent();
            int lawID = Integer.parseInt(content.substring(content.indexOf('#') + 1));
            Law law=new Law(lawID);
            if (!message.isPrivate()) return false;
            return new Member(law.getCreator()).getPhone().equals(message.getSender());
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new CancelLaw(law);
    }
}

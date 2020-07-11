package me.amitnave.voting.WAInterface.Command.Parsers.AppealParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Appeal.AddAppeal;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Appeal;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;

public class AddAppealParser implements CommandParser {
    private int lawID;
    private int memberID;
    private String reason;
    @Override
    public boolean isLegalCommand(Message message) {
        if (!message.getContent().contains("\n")) {
            return false;
        }
        String header = message.getContent().split("\n")[0];
        if (!header.startsWith("ערעור על חוק #")) {
            return false;
        }
        try {
            lawID = Integer.parseInt(header.substring(header.indexOf('#') + 1));
            if (!Member.isMember(message.getSender())) return false;
            memberID=new Member(message.getSender()).getId();
            reason=message.getContent().substring(message.getContent().indexOf('\n'));
            return new Law(lawID).getDescription() != null;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public VotingCommand getCommand() throws SQLException {

        return new AddAppeal(new Appeal(lawID, memberID, reason));
    }
}

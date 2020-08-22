package me.amitnave.voting.WAInterface.Command.Parsers.MemberParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Member.ListMembers;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;

public class ListMembersParser implements CommandParser {
    boolean activeOnly;
    String askerID;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {

        if (!message.isPrivate()) return false;
        if (message.getContent().equals("#חברים")) {
            activeOnly = false;
        } else if(message.getContent().equals("#חברים במועצה")) {
            activeOnly = true;
        } else {
            return false;
        }
        askerID=message.getChatID();

        return true;

    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new ListMembers(askerID, activeOnly);
    }
}

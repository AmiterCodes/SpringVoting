package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.Constitution;
import me.amitnave.voting.WAInterface.Message.Message;

import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;


public class ConstitutionParser implements CommandParser {

    private Member member;


    @Override
    public boolean isLegalCommand(Message message) throws SQLException {
        if (message.getContent().equals("#חוקה") && message.isPrivate() && Member.isMember(message.getSender())) {
            member = new Member(message.getSender());
            return true;
        }
        return false;
    }


    @Override
    public VotingCommand getCommand() {
        return new Constitution(member);
    }
}

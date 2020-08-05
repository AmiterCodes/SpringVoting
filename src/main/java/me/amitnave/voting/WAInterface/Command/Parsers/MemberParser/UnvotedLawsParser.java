package me.amitnave.voting.WAInterface.Command.Parsers.MemberParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Member.UnvotedLaws;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;

public class UnvotedLawsParser implements CommandParser {
    private Member member;

    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if (message.getContent().equals("#תזכורת") && message.isPrivate() && Member.isMember(message.getSender())) {
            member = new Member(message.getSender());
            return true;
        }
        return  false;
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new UnvotedLaws(member);
    }
}

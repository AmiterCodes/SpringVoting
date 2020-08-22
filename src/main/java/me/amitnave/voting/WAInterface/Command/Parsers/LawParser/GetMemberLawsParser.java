package me.amitnave.voting.WAInterface.Command.Parsers.LawParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetMemberLaws;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetStatusLaws;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;

public class GetMemberLawsParser implements CommandParser {
    private String askerID;
    private Member member;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        askerID=message.getSender();
        if (!message.isPrivate()) return false;
        if(!message.getContent().startsWith("#חוקים של")) return false;
        String content = message.getContent().replaceFirst("#חוקים של ", "");
        member= new Member(content, true);
        return member.getName()!=null;
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new GetMemberLaws(askerID, member);
    }
}

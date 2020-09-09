package me.amitnave.voting.WAInterface.Command.Parsers.MemberParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.Member.MemberStats;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;

public class MemberStatsParser implements CommandParser {
    private Member member;
    private String askerID;
    @Override
    public boolean isLegalCommand(Message message) throws SQLException, ParseException {
        if (!message.isPrivate()) return false;
        askerID = message.getSender();
        if(message.getContent().equals("#סטטיסטיקה"))
        {
            member = null;
            return true;
        }
        if (!message.getContent().startsWith("#סטטיסטיקה ")) return false;

        member= new Member(message.getContent().substring(message.getContent().indexOf(' ')+1), true);
        return member.getName()!=null;
    }

    @Override
    public VotingCommand getCommand() throws SQLException, ParseException {
        return new MemberStats(member,askerID);
    }
}

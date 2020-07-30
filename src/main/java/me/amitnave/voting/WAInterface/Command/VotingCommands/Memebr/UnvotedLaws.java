package me.amitnave.voting.WAInterface.Command.VotingCommands.Memebr;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.WAInterface.general.Reminder;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class UnvotedLaws implements VotingCommand {
    private Member member;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        return Reminder.remindMessages(member);
    }

    public UnvotedLaws(Member member) {
        this.member = member;
    }
}

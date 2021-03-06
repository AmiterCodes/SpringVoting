package me.amitnave.voting.WAInterface.Command.VotingCommands.AddVote;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AddAnonymousVote implements VotingCommand {
    private Vote vote;

    public AddAnonymousVote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public void DatabaseAction() throws SQLException, ParseException {
        vote.insert();
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        String approval = new Member(vote.getMemberID())
                .getName()+ ", הצבעתך נקלטה בהצלחה.";
        return List.of(new MessageToSend(approval,(new Member(vote.getMemberID())).getPhone()));
    }
}

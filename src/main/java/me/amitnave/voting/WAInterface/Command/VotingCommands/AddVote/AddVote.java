package me.amitnave.voting.WAInterface.Command.VotingCommands.AddVote;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;

public class AddVote implements VotingCommand {
    private Vote vote;


    @Override
    public void DatabaseAction() throws SQLException {
        vote.insert();
    }

    public AddVote(Vote vote) {
        this.vote = vote;
    }

    @Override
    public MessageToSend message() throws SQLException {
        String vote;
        if (this.vote.getVote()== Vote.FOR) vote="בעד";
        else if (this.vote.getVote()== Vote.AGAINST) vote="נגד";
        else vote="נמנע";
        String approval ="הצבעת "+vote+" על חוק #"+this.vote.getLawID();
        return new MessageToSend(approval, (new Member(this.vote.getMemberID())).getPhone());
    }
}

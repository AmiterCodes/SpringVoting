package me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser;


import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.AddVote.AddAnonymousVote;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;

import static me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser.AddVoteParser.ConvertStatus;

public class AddAnonymousVoteParser implements CommandParser {

    private int vote;
    private int memberID;
    private int lawID;

    public boolean isLegalCommand(Message message) throws SQLException {

        vote = ConvertStatus(message.getContent());
        if (!message.isRepliedToBot() || vote == -1 ||
                !Member.isMember(message.getChatID())) {
            return false;
        }

        if (!Member.isMember(message.getSender())) return false;
        memberID = new Member(message.getSender()).getId();
        String header=message.getRepliedMessage().split("\n")[0];
        if (!header.startsWith("הצעת חוק #")) return false;
        try {
            lawID=Integer.parseInt(header.substring(header.indexOf('#')+1));

            if (new Law(lawID).getDescription()==null) return false;
            if (!new Law(lawID).isAnonymousVoting()) return false;
            return true;
        }
        catch (Exception e){
            return false;
        }

    }


    public VotingCommand getCommand() throws SQLException {

        return new AddAnonymousVote(new Vote(lawID, memberID, vote));
    }
}

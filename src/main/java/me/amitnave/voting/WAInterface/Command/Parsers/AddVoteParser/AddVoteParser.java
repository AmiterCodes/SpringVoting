package me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Command.VotingCommands.AddVote.AddVote;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;

public class AddVoteParser implements CommandParser {
    private int vote;
    private int memberID;
    private int lawID;

    public boolean isLegalCommand(Message message) throws SQLException {

        vote = ConvertStatus(message.getContent());
        if (!message.isRepliedToBot() || vote == -1 ||
                !message.getChatID().equals(Settings.getCouncilChatID())) {
            return false;
        }

        if (!Member.isMember(message.getSender())) return false;
        memberID = new Member(message.getSender()).getId();
        String header=message.getRepliedMessage().split("\n")[0];
        if (!header.startsWith("הצעת חוק #")) return false;
        try {
            lawID=Integer.parseInt(header.substring(header.indexOf('#')+1));

            if (new Law(lawID).getDescription()==null) return false;
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

    @Override
    public VotingCommand getCommand() throws SQLException {

        return new AddVote(new Vote(lawID, memberID, vote));
    }

    public static int ConvertStatus(String s) {
        if (s.startsWith("בעד")) {
            return Vote.FOR;
        }
        if (s.startsWith("נגד")) {
            return Vote.AGAINST;
        }
        if (s.startsWith("נמנע")) {
            return Vote.NEUTRAL;
        }
        return -1;
    }


}

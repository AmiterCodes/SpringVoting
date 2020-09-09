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
    private String info;

    public boolean isLegalCommand(Message message) throws SQLException {

        vote = ConvertStatus(message.getContent());
        if (!message.isRepliedToBot() || vote == -1 ||
                (!message.getChatID().equals(Settings.getCouncilChatID()) && !message.isPrivate())) {
            return false;
        }
        if (!Member.isMember(message.getSender())) return false;
        memberID = new Member(message.getSender()).getId();
        String header = message.getRepliedMessage().split("\n")[0];
        if (!header.startsWith("הצעת חוק #")) return false;
        try {
            lawID = Integer.parseInt(header.substring(header.indexOf('#') + 1));
            Law law = new Law(lawID);
            if(law.getStatus() != Law.inProcess) return false;
            if (law.getDescription() == null) return false;
            info = removeStatus(message.getContent());
            return !law.isAnonymousVoting();
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public VotingCommand getCommand() throws SQLException {

        return new AddVote(new Vote(lawID, memberID, vote, info));
    }

    public static int ConvertStatus(String s) {
        if (s.startsWith("בעד") || s.startsWith("לרוויה")) {
            return Vote.FOR;
        }
        if (s.startsWith("נגד") || s.startsWith("לא קול")) {
            return Vote.AGAINST;
        }
        if (s.startsWith("נמנע") || s.startsWith("זה לא חוק חשוב")) {
            return Vote.NEUTRAL;
        }


        return -1;
    }

    public static String removeStatus(String s) {

        if (s.startsWith("בעד") || s.startsWith("נגד")) {
            if(s.length() > 3)
                return s.substring(3);
        }
        if (s.startsWith("נמנע")) {
            if(s.length() > 4)
                return s.substring(4);
        }
        if (s.startsWith("לרוויה") || s.startsWith("לא קול") || s.startsWith("זה לא חוק חשוב")) {
            return s;
        }
        return "";
    }


}

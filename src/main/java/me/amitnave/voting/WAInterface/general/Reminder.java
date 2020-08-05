package me.amitnave.voting.WAInterface.general;

import me.amitnave.voting.WAInterface.Command.VotingCommands.Law.PassLaw;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.DBHelper;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.*;

public class Reminder {


    public List<MessageToSend> remindMessages() throws SQLException, ParseException {
        List<Law> laws = Law.getLawsByStatus(Law.inProcess);
        List<Member> members = Member.getMembers();
        List<Vote> votes = Vote.votesForLawsByStatus(Law.inProcess);
        List<MessageToSend> mts = new LinkedList<>();
        for (Member member : members) {
            for (Law law : laws) {
                if (!(voted(member.getId(), law.getId(), votes))) {
                    mts.add(new MessageToSend(PassLaw.LowMessage(law), member.getPhone()));
                }
            }
        }
        return mts;
    }

    public static List<MessageToSend> remindMessages(Member member) throws SQLException, ParseException {
        List<Law> laws = Law.getLawsByStatus(Law.inProcess);
        List<Vote> votes = Vote.votesForLawsByStatus(Law.inProcess);
        List<MessageToSend> mts = new LinkedList<>();

        for (Law law : laws) {
            if (!(voted(member.getId(), law.getId(), votes))) {
                mts.add(new MessageToSend(PassLaw.LowMessage(law), member.getPhone()));
            }
        }
        if(mts.size() != 0) {
            mts.add(0, new MessageToSend("אנא הצביע על חוקים אלו:", member.getPhone()));
        } else {
            mts.add(new MessageToSend("הצבעת על כל החוקים שאתה יכול להצביע עליהם, אנשים כמוך עושים את המערכת הדמוקרטית טובה, כל הכבוד צדיק!", member.getPhone()));
        }
        return mts;
    }

    private static boolean voted(int memberID, int lawID, List<Vote> votes) {
        for (Vote vote : votes) {
            if (vote.getMemberID() == memberID && vote.getLawID() == lawID) {
                return true;
            }
        }
        return false;
    }


}

package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GetLawStatus implements VotingCommand {
    private Law law;
    private String askerNum;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    @Override
    public MessageToSend message() throws SQLException, ParseException {
        List<Vote> votes = law.getVotes();
        String status;
        if (law.getStatus() == Law.passed) {
            status = "עבר";
        } else if (law.getStatus() == Law.failed) {
            status = "לא עבר";
        } else {
            status = "בתהליך הצבעה";
        }
        MessageStructure structure=new MessageStructure();
        structure.addToLastRow("מצב החוק:");
        structure.addToLastRow(status);

        if (law.getStatus() == Law.inProcess) {
            structure.addRow("זמן שנותר:");
            structure.addToLastRow(law.hoursLeft());
            structure.addToLastRow("שעות");
            structure.addToLastRow(law.minutesLeft());
            structure.addToLastRow("דקות.");

        }
        if (!law.isAnonymousVoting()) {

            structure.addRow("   בעד");

            for (Vote vote : votes
            ) {
                if (vote.getVote() == Vote.FOR) {
                    structure.addRow((new Member(vote.getMemberID()).getName()));

                }
            }
            structure.addRow("   נגד");

            for (Vote vote : votes
            ) {
                if (vote.getVote() == Vote.AGAINST) {
                    structure.addRow((new Member(vote.getMemberID()).getName()));

                }
            }
            structure.addRow("   נמנע");

            for (Vote vote : votes
            ) {
                if (vote.getVote() == Vote.NEUTRAL) {
                    structure.addRow((new Member(vote.getMemberID()).getName()));

                }
            }
        }


        String res = structure.getString();
        return new MessageToSend(res, askerNum);

    }

    public GetLawStatus(Law law, String askerNum) {
        this.law = law;
        this.askerNum = askerNum;
    }

    public static void main(String[] args) throws SQLException, ParseException {
        Law law=new Law(376);
        GetLawStatus getLawStatus=new GetLawStatus(law, "4tr");
        System.out.println(getLawStatus.message().getContent());
    }
}

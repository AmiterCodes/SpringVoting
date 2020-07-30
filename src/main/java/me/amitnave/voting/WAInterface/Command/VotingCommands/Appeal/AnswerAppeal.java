package me.amitnave.voting.WAInterface.Command.VotingCommands.Appeal;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Appeal;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class AnswerAppeal implements VotingCommand {
    private Appeal appeal;
    private boolean approved;
    private String reason;
    @Override
    public void DatabaseAction() throws SQLException, ParseException {
        appeal.presidentDecision(approved,reason);
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        MessageStructure structure=new MessageStructure();
        structure.addRow("*הגשת ערעור #");
        structure.addToLastRow(appeal.getId()+"*");
        structure.addRow("*מערער:*");
        structure.addToLastRow((new Member(appeal.getAppealer()).getName()));

        Law law=new Law(appeal.getLaw());
        structure.addRow("ערעור על חוק #");
        structure.addToLastRow((law.getId()+""));
        structure.addToLastRow((" שהוצע על ידי "));
        if (new Law(appeal.getLaw()).isAnonymousCreator())
            structure.addToLastRow(("אנונימי"));
        else
            structure.addToLastRow(new Member(law.getCreator()).getName());

        structure.addRow("*תיאור:* ");
        structure.addToLastRow(law.getDescription());

        structure.addRow("*סיבת הערעור:* ");
        structure.addToLastRow(appeal.getReason());

        structure.addRow("*סטטוס הערעור:* ");
        if (appeal.getStatus()== Appeal.approved){
            structure.addToLastRow("הערעור התקבל, והחוק בוטל.");
        }
        else {
            structure.addToLastRow("הערעור נדחה, החוק נשאר.");
        }

        structure.addRow("תגובת הנשיא:");
        structure.addToLastRow(reason);


        String res = structure.getString();
        return List.of(new MessageToSend(res, Settings.getCouncilChatID()));
    }

    public AnswerAppeal(Appeal appeal, boolean approved, String reason) {
        this.appeal = appeal;
        this.approved = approved;
        this.reason = reason;
    }


}

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
import java.util.LinkedList;
import java.util.List;

public class AddAppeal implements VotingCommand {
    private Appeal appeal;

    @Override
    public void DatabaseAction() throws SQLException {
        appeal.insert();
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        Member appealer=new Member(appeal.getAppealer());
        MessageStructure structure=new MessageStructure();
        structure.addToLastRow("הגשת ערעור #");
        structure.addToLastRow(appeal.getId()+"");
        structure.addRow("מערער: ");
        structure.addToLastRow(appealer.getName());
        Law law=new Law(appeal.getLaw());
        structure.addRow("ערעור על חוק #");
        structure.addToLastRow(law.getId()+"");
        structure.addRow("תיאור: ");
        structure.addToLastRow(law.getDescription());
        structure.addRow("סיבת הערעור: ");
        structure.addToLastRow(appeal.getReason());
        String res = structure.getString();
        List<MessageToSend> mts=new LinkedList<>();
        mts.add(new MessageToSend(res, Settings.getPresidentChatID()));
        mts.add(new MessageToSend( "הערעור התקבל בהצלחה",appealer.getPhone()));
        return mts;
    }

    public AddAppeal(Appeal appeal) {
        this.appeal = appeal;
    }


}

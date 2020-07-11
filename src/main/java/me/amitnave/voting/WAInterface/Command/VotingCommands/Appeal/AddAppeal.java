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

public class AddAppeal implements VotingCommand {
    private Appeal appeal;

    @Override
    public void DatabaseAction() throws SQLException {
        appeal.insert();
    }

    @Override
    public MessageToSend message() throws SQLException, ParseException {
        MessageStructure structure=new MessageStructure();
        structure.addToLastRow("הגשת ערעור #");
        structure.addToLastRow(appeal.getId()+"");
        structure.addRow("מערער: ");
        structure.addToLastRow((new Member(appeal.getAppealer()).getName()));
        Law law=new Law(appeal.getLaw());
        structure.addRow("ערעור על חוק #");
        structure.addToLastRow(law.getId()+"");
        structure.addRow("תיאור: ");
        structure.addToLastRow(law.getDescription());
        structure.addRow("סיבת הערעור: ");
        structure.addToLastRow(appeal.getReason());
        String res = structure.getString();
        return new MessageToSend(res, Settings.getPresidentChatID());
    }

    public AddAppeal(Appeal appeal) {
        this.appeal = appeal;
    }

    public static void main(String[] args) throws SQLException, ParseException {
        Appeal appeal=new Appeal(370,5,"בגלל הדברים");
        AddAppeal addAppeal=new AddAppeal(appeal);
        addAppeal.DatabaseAction();
        System.out.println(addAppeal.message().getContent());
    }
}

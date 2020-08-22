package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetLawStatus.status;

public class GetStatusLaws implements VotingCommand {
    private String askerID;
    private int lawStatus;
    private String param;

    public GetStatusLaws(String askerID, int lawStatus, String param) {
        this.askerID = askerID;
        this.lawStatus = lawStatus;
        this.param = param;
    }

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }


    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        List<Law> list = Law.getLawsByStatus(lawStatus);
        if (list.size() == 0) return List.of(new MessageToSend( "אין חוקים " + param, askerID));
        MessageStructure structure = new MessageStructure();
        structure.addRow("*" + "רשימת חוקים " + param  + ":" + "*");
        listLaws(list, structure);
        return List.of(new MessageToSend(structure.getString(), askerID));
    }

    static void listLaws(List<Law> passed, MessageStructure structure) {
        for(int i = passed.size() - 1; i >= 0; i--) {
            Law law = passed.get(i);
            structure.addRow("*" + "חוק #" + law.getId() + "*");
            structure.addRow(law.getDescription());
            structure.addRow("");
        }
    }

    static void listLawsWithStatus(List<Law> passed, MessageStructure structure) {
        for(int i = passed.size() - 1; i >= 0; i--) {
            Law law = passed.get(i);
            structure.addRow("*" + "חוק #" + law.getId() + "*");
            structure.addRow(law.getDescription());
            structure.addRow("מצב החוק: ");
            structure.addToLastRow("*" + status(law) +"*");
            structure.addRow("");
        }
    }
}

package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class GetPassesLaws implements VotingCommand {
    private String askerID;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    public GetPassesLaws(String askerID) {
        this.askerID = askerID;
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        List<Law> passed = Law.getLawsByStatus(Law.passed);
        if (passed.size() == 0) return List.of(new MessageToSend("אין חוקים שעברו", askerID));
        MessageStructure structure = new MessageStructure();
        structure.addRow("*" + "רשימת חוקים שעברו:" + "*");
        for(int i = passed.size() - 1; i >= 0; i--) {
            Law law = passed.get(i);
            structure.addRow("*" + "חוק #" + law.getId() + "*");
            structure.addRow(law.getDescription());
            structure.addRow("");
        }
        return List.of(new MessageToSend(structure.getString(), askerID));
    }
}

package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class SearchLaw implements VotingCommand {
    private final String searchTerm;
    private final String askerNum;
    public SearchLaw(String askerNum, String searchTerm) {
        this.askerNum = askerNum;
        this.searchTerm = searchTerm;
    }

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        List<Law> passed = Law.getLawsByStatusLike(Law.passed, searchTerm);
        List<Law> failed = Law.getLawsByStatusLike(Law.failed, searchTerm);
        if (passed.size() + failed.size() == 0) return List.of(new MessageToSend("לא נמצאו חוקים שמתאימים לחיפוש", askerNum));
        MessageStructure structure = new MessageStructure();
        structure.addRow("*" + "חוקים שמתאימים לחיפוש שעברו" + "*");
        lawList(passed, structure);
        structure.addRow("*" + "חוקים שמתאימים לחיפוש שלא עברו" + "*");
        lawList(failed, structure);
        return List.of(new MessageToSend(structure.getString(), askerNum));
    }

    private void lawList(List<Law> passed, MessageStructure structure) {
        GetStatusLaws.listLaws(passed, structure);
    }
}

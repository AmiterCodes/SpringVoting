package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetStatusLaws.listLaws;
import static me.amitnave.voting.WAInterface.Command.VotingCommands.Law.GetStatusLaws.listLawsWithStatus;

public class GetMemberLaws implements VotingCommand {
    private String askerID;
    private Member member;

    public GetMemberLaws(String askerID, Member member) {
        this.askerID = askerID;
        this.member = member;
    }

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }


    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        List<Law> list = Law.getLawsByMember(member);
        if (list.size() == 0) return List.of(new MessageToSend(  "אין חוקים של " + member.getName(), askerID));
        MessageStructure structure = new MessageStructure();
        structure.addRow("*" + "רשימת חוקים של " + member.getName() + ":" + "*");
        listLawsWithStatus(list, structure);
        return List.of(new MessageToSend(structure.getString(), askerID));
    }
}

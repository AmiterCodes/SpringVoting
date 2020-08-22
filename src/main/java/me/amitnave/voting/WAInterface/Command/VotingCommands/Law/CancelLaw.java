package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class CancelLaw implements VotingCommand {
    private Law law;
    @Override
    public void DatabaseAction() throws SQLException, ParseException {
        law.cancel();
    }

    public CancelLaw(Law law) {
        this.law = law;
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        MessageStructure structure=new MessageStructure();
        Member member = new Member(law.getCreator());
        structure.addToLastRow("חוק #");
        structure.addToLastRow(law.getId()+"");
        structure.addToLastRow(" בוטל על ידי יוצרו ");
        if(law.isHasFakeName() || law.isAnonymousCreator()) {
            structure.addToLastRow(law.anonName());
        } else
            structure.addToLastRow(member.getName());
        structure.addRow(law.getDescription());
        return List.of(new MessageToSend(structure.getString(), Settings.getCouncilChatID()),
                new MessageToSend("החוק בוטל בהצלחה",member.getPhone()));
    }
}

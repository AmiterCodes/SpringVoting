package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.WAInterface.general.Settings;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.util.List;

public class PassLaw implements VotingCommand {
    private Law law;

    public PassLaw(Law law) {
        this.law = law;
    }

    @Override
    public void DatabaseAction() throws SQLException {
        law.insert();
    }

    @Override
    public List<MessageToSend> message() throws SQLException {
        String res = LowMessage(law);
        return List.of(new MessageToSend(res, Settings.getCouncilChatID()));
    }



    public static String LowMessage(Law law) throws SQLException {
        MessageStructure structure=new MessageStructure();
        structure.addToLastRow("הצעת חוק #");
        structure.addToLastRow(law.getId()+"");
        structure.addRow("*הוגה:* ");
        if(law.isHasFakeName() || law.isAnonymousCreator()) {
            structure.addToLastRow(law.anonName());
        } else {
            structure.addToLastRow((new Member(law.getCreator())).getName());
        }

        structure.addRow(law.getDescription());

        if (law.isAnonymousVoting()) {
            structure.addRow("*הצבעה אנונימית*");
        }


        String res = structure.getString();
        return res;
    }


}

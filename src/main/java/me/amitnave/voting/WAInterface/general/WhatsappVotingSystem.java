package me.amitnave.voting.WAInterface.general;

import me.amitnave.voting.WAInterface.Command.CommandParser;
import me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser.AddAnonymousVoteParser;
import me.amitnave.voting.WAInterface.Command.Parsers.AddVoteParser.AddVoteParser;
import me.amitnave.voting.WAInterface.Command.Parsers.AppealParser.AddAppealParser;
import me.amitnave.voting.WAInterface.Command.Parsers.AppealParser.AnswerAppealParser;
import me.amitnave.voting.WAInterface.Command.Parsers.HelpParser;
import me.amitnave.voting.WAInterface.Command.Parsers.LawParser.CancelLawParser;
import me.amitnave.voting.WAInterface.Command.Parsers.LawParser.GetLawStatusParser;
import me.amitnave.voting.WAInterface.Command.Parsers.LawParser.GetPassedLawsParser;
import me.amitnave.voting.WAInterface.Command.Parsers.LawParser.PassLawParser;
import me.amitnave.voting.WAInterface.Command.Parsers.MemberParser.MemberStatsParser;
import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.Message;
import me.amitnave.voting.WAInterface.Message.MessageStructure;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Law;
import me.amitnave.voting.databaseObjects.Vote;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class WhatsappVotingSystem {
    private List<CommandParser> parsers = List.of(
            new AddAnonymousVoteParser(),
            new AddVoteParser(),
            new AddAppealParser(),
            new AnswerAppealParser(),
            new GetLawStatusParser(),
            new PassLawParser(),
            new HelpParser(),
            new CancelLawParser(),
            new GetPassedLawsParser(),
            new MemberStatsParser()
    );
    public VotingCommand parseMessage(Message message) throws SQLException, ParseException {
        for (CommandParser parser: parsers
             ) {
            if (parser.isLegalCommand(message))
                return parser.getCommand();
        }
        return null;
    }



    public List<MessageToSend> updateLaws() throws SQLException, ParseException {
        List<Law> laws= Law.getLawsByStatus(Law.inProcess);
        List<MessageToSend> list = new ArrayList<>();
        for (Law law: laws) {
            if(law.updateStatus()) {
                MessageStructure ms = new MessageStructure();
                ms.addToLastRow("הצעת חוק #");
                ms.addToLastRow(""+law.getId());
                ms.addRow("תוצאה: ");
                ms.addToLastRow(law.getStatus() == Law.failed ? "לא עבר" : "עבר");
                var votes = law.getVotes();
                int forLaw = 0;
                int againstLaw = 0;
                int neutral = 0;
                for (Vote vote : votes) {
                    switch (vote.getVote()) {
                        case Vote.FOR:
                            forLaw++;
                            break;
                        case Vote.AGAINST:
                            againstLaw++;
                            break;
                        case Vote.NEUTRAL:
                            neutral++;
                            break;
                    }
                }
                ms.addRow("");
                ms.addRow("בעד - ");
                ms.addToLastRow(forLaw + "");
                ms.addRow("נגד - ");
                ms.addToLastRow(againstLaw + "");
                ms.addRow("נמנע - ");
                ms.addToLastRow(neutral + "");
                ms.addRow(law.getDescription());
                MessageToSend m = new MessageToSend(ms.getString(), Settings.getCouncilChatID());
                list.add(m);
            }
        }
        return list;
    }



}

package me.amitnave.voting.WAInterface.Command.VotingCommands;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;

import java.sql.SQLException;
import java.text.ParseException;

public class Help implements VotingCommand {
    private String askerID;
    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    public Help(String askerID) {
        this.askerID = askerID;
    }

    @Override
    public MessageToSend message() throws SQLException, ParseException {
        return new MessageToSend("\n" +
                "תמיכת פקודות\n" +
                "\n" +
                "#הצעת חוק\n" +
                "#הצבעה אנונימית (אופציונלי)\n" +
                "#מקור אנוימי (אופציונלי)\n" +
                "תוכן\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "מצב חוק #מספר\n" +
                "דוגמה: מצב חוק #23\n" +
                "\n" +
                "ערעור על חוק #מספר\n" +
                "סיבה\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "דוגמה:\n" +
                "ערעור על חוק #20\n" +
                "כי הוא בזוי\n" +
                "\n" +
                "מענה על על ערעור (לנשיא בלבד):\n" +
                "הגב על הודעת הערעור שקיבלת בפורמט הבא:\n" +
                "התקבל/נדחה\n" +
                "סיבה\n" +
                "\n" +
                "הצבעה:\n" +
                "הגב על הודעת החוק בהודעה שמתחילה עם בעד/נגד/נמנע, בקבוצת המטעצה\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "הצבעה אנונימית\n" +
                "הגב על הודעת החוק בהודעה שמתחילה עם בעד/נגד/נמנע, בפרטי לבוט\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "שים לב: אם תצביע בפרטי על חוק לא אנונימי ההצבעה לא תיחשב", askerID);
    }
}

package me.amitnave.voting.WAInterface.Command.VotingCommands;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class Help implements VotingCommand {
    private String askerID;

    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    public Help(String askerID) {
        this.askerID = askerID;
    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        return List.of(new MessageToSend("*תמיכת פקודות*\n" +
                "\n" +
                "*#הצעת חוק*\n" +
                "*#הצבעה אנונימית* (אופציונלי)\n" +
                "*#מקור אנונימי* (אופציונלי)\n" +
                "*#שם בדוי [שם]* (אופציונלי)\n" +
                "תוכן\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "*מצב חוק: *\n" +
                "*#מספר*\n" +
                "דוגמה: #23 \n" +
                "\n" +
                "*ערעור על חוק #מספר*\n" +
                "סיבה\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "דוגמה:\n" +
                "ערעור על חוק #20\n" +
                "כי הוא בזוי\n" +
                "\n" +
                "*מענה על על ערעור (לנשיא בלבד):*\n" +
                "הגב על הודעת הערעור שקיבלת בפורמט הבא:\n" +
                "התקבל/נדחה\n" +
                "סיבה\n" +
                "\n" +
                "*#חברים [במועצה]*\n" +
                "מחזיר את רשימת החברים שבמאגר הרייך, הוספה של במועצה תביא רק את החברים שבמועצה הנוכחית\n" +
                "\n" +
                "*הצבעה:*\n" +
                "הגב על הודעת החוק בהודעה שמתחילה עם בעד/נגד/נמנע, בקבוצת המועצה\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "*הצבעה אנונימית*\n" +
                "הגב על הודעת החוק בהודעה שמתחילה עם בעד/נגד/נמנע, בפרטי לבוט\n" +
                "(פקודה זמינה לחברי מועצה בלבד)\n" +
                "\n" +
                "קבלת רשימת חוקים על פי מצבם:\n" +
                "*#חוקים שעברו/שלא עברו/שנפסלו/שעדיין בהצבעה*\n" +
                "\n" +
                "קבלת רשימת חוקים על פי הוגה (לא כולל חוקים ממקור אנונימי):\n" +
                "*#חוקים של [שם חבר מועצה]*\n" +
                "דוגמה:\n" +
                "#חוקים של שלמה מזרחי\n" +
                "\n" +
                "*ביטול חוק:*\n" +
                "אם הצעת חוק והתחרטת עליו, תוכל לכתוב *ביטול חוק #מספר* אך רק אם הוא עדיין בתהליך הצבעה.\n" +
                "דוגמה: ביטול חוק #55\n" +
                "\n" +
                "סטטיסטיקה על חבר מועצה:\n" +
                "*#סטטיסטיקה שם*\n" +
                "דוגמה: #סטטיסטיקה נחמן\n\n" +
                "תזכורת של כל החוקים שעליך להצביע עליהם:\n" +
                "*#*\n\n"
                + "צפייה בעותק נוכחי של חוקת הרייך\n"
                + "\n*#חוקה*"
                +"\n"
                +"חיפוש חוק על פי תיאור\n"
                +"*#'ביטוי'*\n" +
                "דוגמהי:\n" +
                "#'שרבי'\n"
                , askerID));
    }
}

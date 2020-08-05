package me.amitnave.voting.WAInterface.Command.VotingCommands.Law;

import me.amitnave.voting.WAInterface.Command.VotingCommand;
import me.amitnave.voting.WAInterface.Message.MessageToSend;
import me.amitnave.voting.databaseObjects.Member;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class Constitution implements VotingCommand {
    private Member member;

    public Constitution(Member member) {
        this.member = member;
    }


    @Override
    public void DatabaseAction() throws SQLException, ParseException {

    }

    @Override
    public List<MessageToSend> message() throws SQLException, ParseException {
        LinkedList<MessageToSend> mts = new LinkedList<>();
        mts.add(new MessageToSend("*" + " חוקת הרייך " + "*"
                + "\n" + "-----------------"
                + "\n" + "1. המועצה כוללת 13 אנשים בדיוק"
                + "\n" + "(שנבחרו בבחירות האחרונות בלבד)"
                + "\n" + "2. לא ניתן להוסיף/לגרש חברי מועצה בזמן"
                + "\n" + "קדנציה, אלא באמצעות בחירות חדשות."
                + "\n" + "3. סמכויותיה של המועצה הן לארגן מפגשים,"
                + "\n" + "כנסים, אופרציות, פרויקטים, תחרויות, "
                + "\n" + "פעילויות והדבר החשוב ביותר חקיקת חוקים."
                + "\n" + "4. יש נשיא אחד אשר מטיל את הרכבת"
                + "\n" + "הממשלה על מפלגה אחת כל בחירות."
                + "\n" + "5. ניתן לערער על חוקים לנשיא בלבד"
                + "\n" + "6. כל 3 חודשים יתקימו בחירות למועצה"
                + "\n" + "7. רק חבר רייך יכול לרוץ למנהיגות הרייך,"
                + "\n" + "הוא יכול לרוץ לבד או ברוטציה עם חבר אחר."
                + "\n" + "8. רק חברי מועצה יכולים להציע או להצביע"
                + "\n" + "על חוקים בקבוצת וואטסאפ נפרדת"
                + "\n" + "שמיודעת להצבעות."
                + "\n" + "9. לחברי המועצה יש חסינות נגד חוקים"
                + "\n" + "המוציאים אותם מהרייך, אך אם הם ביצעו"
                + "\n" + "בגידה, או מתאימים לקריטריוני אויב הרשות"
                + "\n" + "השופטת יכולה להדיח אותם."
                + "\n" + "*" + " קול אחד בלבד! " + "*" + "10. לכל חבר במועצה יש ", member.getPhone()));
        return mts;
    }
}

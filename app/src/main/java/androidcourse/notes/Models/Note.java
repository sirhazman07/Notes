package androidcourse.notes.Models;

import java.text.SimpleDateFormat; // adds Simple Date Format "Package" (Package in Java, Namespace in C#)
import java.util.Date;

/**
 * Created by Harold on 24/03/2016.
 */
public class Note {
    private int mId;
    private String mTitle;
    private String mContent;
    private Date mLastModied;
    private String mPassword;
    private static int NEXt_ID = 1;


    public Note(String title, String content) {
        mId = NEXt_ID++;
        mTitle = title;
        mContent = content;
        mLastModied = new Date();
    }

    public Note(String title, String content, String password) {
        mId = NEXt_ID++;
        mTitle = title;
        mContent = content;
        mPassword = password;
        mLastModied = new Date();
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmContent() {
        return mContent;
    }

    public Date getmLastModied() {
        return mLastModied;
    }

    public String getmPassword() {
        return mPassword;
    }

    public String dateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd - HH:mm");
        return "Last edited on: " + sdf.format(mLastModied);
    }
}
package androidcourse.notes.Models;

import java.io.Serializable;
import java.text.SimpleDateFormat; // adds Simple Date Format "Package" (Package in Java, Namespace in C#)
import java.util.Date;

/**
 * Created by Harold on 24/03/2016.
 */
public class Note implements Serializable {
    private int mId;
    private String mTitle;
    private String mContent;
    private Date mLastModied;
    private String mPassword;
    private static int NEXt_ID = 1;

    //Contructor for notes without a Password
    public Note(String title, String content) {
        mId = NEXt_ID++;
        mTitle = title;
        mContent = content;
        mLastModied = new Date();
    }

    //Contructor for notes with a Password
    public Note(String title, String content, String password) {
        mId = NEXt_ID++;
        mTitle = title;
        mContent = content;
        mPassword = password;
        mLastModied = new Date();
    }

    //Getter for attributes
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

    //Setter for attributes

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public void setmLastModied(Date mLastModied) {
        this.mLastModied = mLastModied;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}
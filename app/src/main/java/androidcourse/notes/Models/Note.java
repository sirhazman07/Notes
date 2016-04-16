package androidcourse.notes.Models;

import java.text.SimpleDateFormat; // adds Simple Date Format "Package" (Package in Java, Namespace in C#)
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Harold on 24/03/2016.
 */
public class Note extends RealmObject {
    //Annotations in Realm serve to declare Pks and required class attributes
    @PrimaryKey
    private int mId;
    @Required
    private String mTitle;
    @Required
    private String mContent;
    @Required
    private Date mLastModified;
    private String mPassword;
    private String photoPath;

    //this is also required for Realm
    public Note() {}

    //Constructor for notes without a Password
    public Note(String title, String content) {
        mTitle = title;
        mContent = content;
        mLastModified = new Date();
    }
    //Constructor for notes with a Password
    public Note(String title, String content, String password) {
        //TODO: find out what this next line does
        this(title, content);
        mPassword = password;
        mLastModified = new Date();
    }

    //Getter and Setter for attributes
    public String getPhotoPath(){
        return photoPath;
    }
    public void setPhotoPath(String photoPath){
        this.photoPath = photoPath;
    }
    public int getmId() {
        return mId;
    }
    public  void setmId(int mId){
        this.mId = mId;
    }
    public String getmTitle() {
        return mTitle;
    }
    public String getmContent() {
        return mContent;
    }
    public Date getmLastModified() {
        return mLastModified;
    }
    public String getmPassword() {
        return mPassword;
    }
    public void setmTitle(String mTitle) {

        this.mTitle = mTitle;
    }
    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
    public void setmLastModified(Date mLastModified) {
        this.mLastModified = mLastModified;
    }
    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
    public String dateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd - HH:mm");
        return "Last edited on: " + sdf.format(mLastModified);
    }


}
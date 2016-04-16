package androidcourse.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

import androidcourse.notes.Models.Note;

public class AddNote extends AppCompatActivity {
    private String mPassword;
    //never forget to add realm here after adding the reference package io.realm.Realm
    private Realm realm;
    //action listener for the new button
    private String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        final CheckBox password = (CheckBox) findViewById(R.id.pwdCheckBox);
        assert password != null;
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View layout = getLayoutInflater().inflate(R.layout.pin_layout, null);
                final EditText password1 = (EditText) layout.findViewById(R.id.pwd1);
                final EditText password2 = (EditText) layout.findViewById(R.id.pwd2);
                final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);
                //Listener for the password checkbox
                password2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    public void afterTextChanged(Editable s) {
                        String strPass1 = password1.getText().toString();
                        String strPass2 = password2.getText().toString();
                        //validate if both passwords are the same
                        if (strPass1.equals(strPass2) && strPass2.length() == 4) {
                            error.setText("Passwords Match");
                            error.setTextColor(Color.GREEN);
                        } else if (strPass2.length() != 4) {
                            error.setText("Password must contain 4 digits");
                            error.setTextColor(Color.RED);
                        } else {
                            error.setText("Passwords do not Match");
                            error.setTextColor(Color.RED);
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(AddNote.this);
                builder.setView(layout);
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //if password prompt is cancelled disable checkbox
                        password.setChecked(false);
                        mPassword = null;
                    }
                });
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String strPassword1 = password1.getText().toString();
                        String strPassword2 = password2.getText().toString();
                        if (strPassword1.equals(strPassword2)) {
                            mPassword = strPassword1;
                        }
                    }
                });
                AlertDialog passwordDialog = builder.create();
                passwordDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            //disable camera icon
            item.setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.SaveNote) {
            String title = ((EditText) findViewById(R.id.title_add)).getText().toString();
            String contents = ((EditText) findViewById(R.id.title_add)).getText().toString();
            Note note;
            //check if the note has password set.
            if (mPassword == null) {
                note = new Note(title, contents);
            } else {
                note = new Note(title, contents, mPassword);
            }
            if (currentPhotoPath != null){
                note.setPhotoPath(currentPhotoPath);
            }
            //save the note
            saveNote(note);
            finish();
        }
        else if (item.getItemId() == R.id.image){
            Intent takePictureIntent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
            //Ensure that there's a camera activity to handle the intent
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                //Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex){
                    //Error occurred while creating the File
                    Toast.makeText(AddNote.this, "Error occurred while creating the File",
                            Toast.LENGTH_SHORT).show();
                }
                //Continue only if the File was successfully created
                if (photoFile != null){
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(photoFile));
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }
    }
        return true;
    }

    private void saveNote(Note note) {
        //set the id for the note
        note.setmId(getNextNoteId());
        //persist your data
        realm.beginTransaction();
        realm.copyToRealm(note);
        realm.commitTransaction();
    }

    //used to generate the next note id
    private int getNextNoteId(){
        int id = 1;
        if (realm.allObjects(Note.class).size() > 0)
            id= realm.where(Note.class).max("mId").intValue() + 1;
        return id;
    }

    //Close the connection
    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    private File createImageFile () throws IOException{
        //create an image FIle name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //getExternalFilesDir makes photos to remain private to your app only
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName, /* [prefix */
                "jpg",          /* sufix  */
                storageDir      /* directory */
        );

        // Save the file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
package androidcourse.notes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.util.Date;

import androidcourse.notes.Models.Note;
import io.realm.Realm;

public class EditNote extends AppCompatActivity {
    private Note noteToEdit;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        realm = Realm.getDefaultInstance();
        //obtaining the note from noteList activity
        Intent intent = getIntent();
        if (intent.hasExtra("note to edit")) {
            int noteId = intent.getIntExtra("note to edit", 0);
            noteToEdit = findNote(noteId);
        }
        if (noteToEdit.getPhotoPath() != null) {
            Button load = (Button) findViewById(R.id.load);
            load.setVisibility(View.VISIBLE);
        }
        //updating the UI with the note info
        EditText title = (EditText) findViewById(R.id.title_edit);
        title.setText(noteToEdit.getmTitle());
        EditText content = (EditText) findViewById(R.id.note_info_edit);
        content.setText(noteToEdit.getmContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_note_menu, menu);
        return true;
    }

    public void load(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(noteToEdit.getPhotoPath())), "image/*");
        startActivity(intent);
    }
    //Method that overwrites the title and content for an existing note -- The SMART way
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.SaveNote) {
            String title = ((EditText) findViewById(R.id.title_edit)).getText().toString();
            String content = ((EditText) findViewById(R.id.note_info_edit)).getText().toString();
            //updating the note
            updateNote(title, content);
        } else if (item.getItemId() == R.id.DeleteNote) {
            deleteNote();
        }else if (item.getItemId() == R.id.email){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("mailto:?subject=" + noteToEdit.getmTitle() + "&body=" +
            noteToEdit.getmContent());
            intent.setData(data);
            startActivity(intent);
        }
        //Return to noteList activity
        finish();
        return true;
    }

    //find the note by id
    private Note findNote(int id) {
        return realm.where(Note.class).equalTo("mId", id).findFirst();
    }

    // update note using realm
    private void updateNote(String title, String content) {
        realm.beginTransaction();
        noteToEdit.setmTitle(title);
        noteToEdit.setmContent(content);
        noteToEdit.setmLastModified(new Date());
        realm.commitTransaction();
    }

    // delete note using realm
    private void deleteNote() {
        realm.beginTransaction();
        noteToEdit.removeFromRealm();
        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}

package androidcourse.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidcourse.notes.Models.Note;

public class EditNote extends AppCompatActivity {
    private Note noteToEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        //obtaining the note from main activity
        Intent intent = getIntent();
        if (intent.hasExtra("note to edit")) {
            noteToEdit = (Note) intent.getSerializableExtra("note to edit");
        }
        //updating the UI with the note info
        EditText title =  (EditText) findViewById(R.id.tittle_edit);
        title.setText(noteToEdit.getmTitle());
        EditText content = (EditText) findViewById(R.id.note_info_edit);
        content.setText(noteToEdit.getmContent());
    }

    //Method that overwrites the title and content for an existing note -- The SMART way
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.SaveNote)
        {
            String title =  ((EditText) findViewById(R.id.tittle_edit)).getText().toString();
            String content= ((EditText) findViewById(R.id.note_info_edit)).getText().toString();
            //updatin the note
            noteToEdit.setmTitle(title);
            noteToEdit.setmContent(content);
            //Responding to the request from main activity
            Intent editNote = new Intent();
            editNote.putExtra("note", noteToEdit);
            setResult(RESULT_OK);
            finish();
        }
        return true;
    }

    public boolean OnCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_note_menu, menu);
        return true;
    }


}

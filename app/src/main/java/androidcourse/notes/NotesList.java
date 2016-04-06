package androidcourse.notes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import androidcourse.notes.Adapters.NotesAdapter;
import androidcourse.notes.Models.Note;

public class NotesList extends AppCompatActivity {
    //After we set-up the on clickLIstener for the ImageView we give it an Intent an alse need to
    // Serialize each intent (label it) through the ADD_NOTE_REQUEST which is an int initialized with 1
    //Think of it as an Itent signature, because you may may multiple intents for the same Click event OK.
    private ArrayList<Note> notes = new ArrayList<Note>();
    private static final int ADD_NOTE_REQUEST = 1;
    private NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);
        final ListView notesListView = (ListView) findViewById(R.id.listView);
        adapter = new NotesAdapter(this, R.layout.note_row, notes);
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        //Listener for add NoteImage
        ImageView addNoteImg = (ImageView) findViewById(R.id.addNoteImg);
        addNoteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent
                Intent addNoteIntent = new Intent(getBaseContext(), AddNote.class);
                startActivityForResult(addNoteIntent, ADD_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_NOTE_REQUEST:
                if (resultCode == RESULT_OK) {
            //get the new note from the intent
                    Note newNote = (Note) data.getSerializableExtra("note");
                    notes.add(newNote);
            //notify the adapter and
            // update the listView adapter with the new Note
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}


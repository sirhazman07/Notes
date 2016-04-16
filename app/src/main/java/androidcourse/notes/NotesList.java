package androidcourse.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import androidcourse.notes.Adapters.NotesAdapter;
import androidcourse.notes.Models.Note;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

public class NotesList extends AppCompatActivity {
    //After we set-up the on clickListener for the ImageView we give it an Intent an alse need to
    // Serialize each intent (label it) through the ADD_NOTE_REQUEST which is an int initialized with 1
    //Think of it as an Intent signature, because you may may multiple intents for the same Click event OK.
    //When we use realm we also need to remove all request because we are not required to start and activity for result
    //ALSO NOTE we use a list not an array list for item using realm
    private NotesAdapter adapter;
    private Realm realm;

    private List<Note> getNotesList() {
        return realm.allObjectsSorted(Note.class, "mTitle", Sort.ASCENDING);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();
        setContentView(R.layout.activity_notes_list);
        final ListView notesListView = (ListView) findViewById(R.id.listView);
        adapter = new NotesAdapter(this, R.layout.note_row, getNotesList());
        notesListView.setAdapter(adapter);

        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the selected note from the list
                Note note = adapter.getItem(position);
                //if note is password protected
                if (note.getmPassword() != null) {
                    displayPinPrompt(note);
                } else {
                    editNoteIntent(note);
                }
            }
        });
        //Listener for add NoteImage
        ImageView addNoteImg = (ImageView) findViewById(R.id.addNoteImg);
        addNoteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an intent
                Intent addNoteIntent = new Intent(getBaseContext(), AddNote.class);
                startActivity(addNoteIntent);
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    private void displayPinPrompt(final Note note) {
        View layout = getLayoutInflater().inflate(R.layout.pin_prompt_layout, null);
        final EditText password1 = (EditText) layout.findViewById(R.id.pwd1);
        final TextView error = (TextView) layout.findViewById(R.id.TextView_PwdProblem);

        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strPass1 = password1.getText().toString();
                //validate if password is correct
                if (!strPass1.equals(note.getmPassword())) {
                    error.setText("Invalid Password");
                    error.setTextColor(Color.RED);
                } else {
                    error.setText("Valid Password");
                    error.setTextColor(Color.GREEN);
                }
            }
        });

        //set a listener for the prompt that lets you cancel from the prompt if you dont to enter a password and return to
        //activity (cancels prompt)
        AlertDialog.Builder builder = new AlertDialog.Builder(NotesList.this);
        builder.setView(layout);
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        //set a listener that checks if the password on the prompts match, if they do start the create
        //note activity
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strPassword1 = password1.getText().toString();
                if (strPassword1.equals(note.getmPassword())) {
                    editNoteIntent(note);
                }
            }
        });
        AlertDialog passwordDialog = builder.create();
        passwordDialog.show();
    }

    //create intent for the edit note listener
    private void editNoteIntent(Note note) {
        Intent editNoteIntent = new Intent(NotesList.this, EditNote.class);
        editNoteIntent.putExtra("note to edit", note.getmId());
        startActivity(editNoteIntent);
    }

    //Close realm database connection
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

}


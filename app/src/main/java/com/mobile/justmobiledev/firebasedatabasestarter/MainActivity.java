package com.mobile.justmobiledev.firebasedatabasestarter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobile.justmobiledev.firebasedatabasestarter.models.Note;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private String newNoteId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //writeNoteToCloud();
        newNoteId = "-L0t-Cz9ZJxvryPijIWJ";
        //queryNoteFromCloud();
        //updateNote();
        removeNote();
    }

    private void writeNoteToCloud()
    {
        try {
            // Create Reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(); // 1

            // Add a child node for all notes
            DatabaseReference notesRef = dbRef.child("notes"); // 2

            // Get a new key for the Note
            newNoteId = notesRef.push().getKey(); // 3
            Log.d(TAG, "NoteId: "+newNoteId);

            // Create a reference to the new note
            DatabaseReference noteRef = notesRef.child(newNoteId); // 2

            // Create a note
            Note note = new Note(newNoteId, "My first note", "This is my Note body"); // 4

            // Create the note async with completion handler
            noteRef.setValue(note, new DatabaseReference.CompletionListener() { // 5
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) { // 6
                    if (databaseError != null) {
                        Log.e(TAG, "Note could not be saved " + databaseError.getMessage());
                    } else {
                        Log.d(TAG, "Note saved successfully.");
                    }
                }
            });
        }
        catch(Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void queryNoteFromCloud()
    {
        try {
            // Create Reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(); // 1

            // Add a child node for all notes
            DatabaseReference notesRef = dbRef.child("notes"); // 2

            Query query = notesRef.orderByChild("id").equalTo(newNoteId); // 3
            query.addListenerForSingleValueEvent(new ValueEventListener() { // 4
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                        // Get the first match
                        //Note note =  dataSnapshot.getValue(Note.class);
                        DataSnapshot noteSnapshot =  dataSnapshot.getChildren().iterator().next();
                        Note note = noteSnapshot.getValue(Note.class);
                        Log.d(TAG, "Note: " + note.title + ", body: " + note.body);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch(Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    private void updateNote()
    {
        try {
            // Create Reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(); // 1

            // Add a child node for all notes
            DatabaseReference notesRef = dbRef.child("notes"); // 2

            // Update title attribute
            notesRef.child(newNoteId).child("title").setValue("this is the new title"); // 3

            Note note = new Note(newNoteId, "new title", "new body"); // 4
            notesRef.child(newNoteId).setValue(note);
        }
        catch(Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }



    private void removeNote()
    {
        try {
            // Create Reference
            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(); // 1

            // Add a child node for all notes
            DatabaseReference notesRef = dbRef.child("notes"); // 2

            // Update title attribute
            notesRef.child(newNoteId).removeValue(new DatabaseReference.CompletionListener() { // 3
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) { // 4
                    if (databaseError != null) {
                        Log.e(TAG, "Note could not be deleted " + databaseError.getMessage());
                    } else {
                        Log.d(TAG, "Note deleted successfully.");
                    }
                }
            });
        }
        catch(Exception ex)
        {
            Log.e(TAG, ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

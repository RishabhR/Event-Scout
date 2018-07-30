package com.example.rajagopalan.gpslocation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Activity which lets user read from and write comments to the Firebase Database
 */
public class CommentsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference commentsReference;
    private RecyclerView recyclerView;
    private ActionBar supportActionBar;

    /**
     * On create method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_list);
        final String ACTION_BAR_TITLE = "Top Comments";
        supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(ACTION_BAR_TITLE);
        initialize();
        writeComment();

        final FirebaseRecyclerAdapter<String, CommentViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<String, CommentViewHolder>(String.class,
                        R.layout.comment, CommentViewHolder.class, commentsReference) {

                    @Override
                    protected void populateViewHolder(CommentViewHolder viewHolder, String model,
                                                      int position) {
                        viewHolder.mCommentTextView.setText(model);
                    }
                };

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    /**
     * Displays dialog box and lets user write a comment
     */
    public void writeComment() {
        FloatingActionButton commentButton = (FloatingActionButton)findViewById(R.id.commentsButton);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_box);
                //Opens up a dialog box for user to enter comment
                dialog.setTitle("Enter comment");
                final EditText editText = (EditText) dialog.findViewById(R.id.editText);

                final Button button = (Button) dialog.findViewById(R.id.button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String comment = editText.getText().toString();
                        commentsReference.push().setValue(comment);
                        // Pushes user's comment to firebase database
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    /**
     * Retrieves parceled data and initializes database reference variables
     */
    public void initialize() {
        database = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        final Event eventInfo = data.getParcelable("event");
        commentsReference = database.getReference("events/" + eventInfo.getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    /**
     * Caches references to subviews
     */
    public static class CommentViewHolder extends RecyclerView.ViewHolder {

        public TextView mCommentTextView;

        public CommentViewHolder(View itemView) {
            super(itemView);
            mCommentTextView = (TextView) itemView.findViewById(R.id.commentTextView);
        }
    }
}

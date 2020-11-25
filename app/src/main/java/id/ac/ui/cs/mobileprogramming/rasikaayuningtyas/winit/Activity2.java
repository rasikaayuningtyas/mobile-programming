package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;


public class Activity2 extends AppCompatActivity implements View.OnClickListener{

    private Button button;
    private ImageView arrow_back, deleteBtn;
    private boolean status = false;
    private ProgressBar progressBar1, progressBar2, progressBar3;
    private TextView groupName, streams, votes, mentions;
    private ImageView plusStream, plusVote, plusMention;
    RoomDB database;
    List<MainData> dataList = new ArrayList<>();
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        button = (Button) findViewById(R.id.playBtn);
        button.setOnClickListener(this);

        arrow_back = findViewById(R.id.back);
        final Intent go_back = new Intent(this, MainActivity.class);
        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(go_back);
            }
        });


        streams = findViewById(R.id.streamNumber);
        votes = findViewById(R.id.voteNumber);
        mentions = findViewById(R.id.mentionNumber);
        plusStream = findViewById(R.id.plus1);
        plusVote = findViewById(R.id.plus2);
        plusMention = findViewById(R.id.plus3);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();
        adapter = new MainAdapter(this, dataList);

        groupName = findViewById(R.id.textView3);
        final int id = getIntent().getIntExtra("sID", 1000);
        final String name = getIntent().getStringExtra("name");
        final int StreamingNumber = getIntent().getIntExtra("streaming", 0);
        final int VotingNumber = getIntent().getIntExtra("voting", 0);
        final int MentionNumber = getIntent().getIntExtra("mention", 0);
        final int CurrentStream = getIntent().getIntExtra("currentStream", 0);
        final int CurrentVote = getIntent().getIntExtra("currentVote", 0);
        final int CurrentMention = getIntent().getIntExtra("currentMention", 0);


        String title = "Win a trophy for " + name + "!";
        groupName.setText(title);
        String streamtxt = CurrentStream+" / "+StreamingNumber;
        String votetxt = CurrentVote+" / "+VotingNumber;
        String mentiontxt = CurrentMention+" / "+MentionNumber;
        streams.setText(streamtxt);
        votes.setText(votetxt);
        mentions.setText(mentiontxt);

        progressBar1 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        progressBar1.setMax(StreamingNumber);
        progressBar1.setProgress(CurrentStream);

        progressBar2.setMax(VotingNumber);
        progressBar2.setProgress(CurrentVote);

        progressBar3.setMax(MentionNumber);
        progressBar3.setProgress(CurrentMention);

        //if all progress completed
        if (progressBar1.getProgress() == progressBar1.getMax()
        && progressBar2.getProgress() == progressBar2.getMax()
        && progressBar3.getProgress() == progressBar3.getMax()){
            new GoalCompletedAsyncTask(groupName, name).execute();
        }

        final Intent refresh = new Intent(this, Activity2.class);

        plusStream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bikin dialog baru
                final Dialog dialog = new Dialog(Activity2.this);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);


                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        int stream_added = Integer.parseInt(editText.getText().toString());
                        database.mainDao().updateStream(id, stream_added);
                        refresh.putExtra("sID", id);
                        refresh.putExtra("name", name);
                        refresh.putExtra("streaming", StreamingNumber);
                        refresh.putExtra("voting", VotingNumber);
                        refresh.putExtra("mention", MentionNumber);
                        refresh.putExtra("currentStream", CurrentStream+stream_added);
                        refresh.putExtra("currentVote", CurrentVote);
                        refresh.putExtra("currentMention", CurrentMention);
                        startActivity(refresh);

                    }
                });
            }
        });

        plusVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bikin dialog baru
                final Dialog dialog = new Dialog(Activity2.this);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        int vote_added = Integer.parseInt(editText.getText().toString());
                        database.mainDao().updateVote(id, vote_added);
                        refresh.putExtra("sID", id);
                        refresh.putExtra("name", name);
                        refresh.putExtra("streaming", StreamingNumber);
                        refresh.putExtra("voting", VotingNumber);
                        refresh.putExtra("mention", MentionNumber);
                        refresh.putExtra("currentStream", CurrentStream);
                        refresh.putExtra("currentVote", CurrentVote+vote_added);
                        refresh.putExtra("currentMention", CurrentMention);
                        startActivity(refresh);
                    }
                });
            }
        });

        plusMention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bikin dialog baru
                final Dialog dialog = new Dialog(Activity2.this);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width,height);
                dialog.show();

                final EditText editText = dialog.findViewById(R.id.edit_text);
                Button btUpdate = dialog.findViewById(R.id.bt_update);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        int mention_added = Integer.parseInt(editText.getText().toString());
                        database.mainDao().updateMention(id, mention_added);
                        refresh.putExtra("sID", id);
                        refresh.putExtra("name", name);
                        refresh.putExtra("streaming", StreamingNumber);
                        refresh.putExtra("voting", VotingNumber);
                        refresh.putExtra("mention", MentionNumber);
                        refresh.putExtra("currentStream", CurrentStream);
                        refresh.putExtra("currentVote", CurrentVote);
                        refresh.putExtra("currentMention", CurrentMention+mention_added);
                        startActivity(refresh);
                    }
                });
            }
        });

        deleteBtn = findViewById(R.id.trash);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<dataList.size();i++){
                    if (dataList.get(i).getID() == id){
                        database.mainDao().delete(dataList.get(i));
                        startActivity(go_back);
                    }
                }
            }
        });
    }


    public void onClick(View view) {
        //if status true then the music is playing, so stop the music
        if (status){
            status = false;
            button.setBackgroundResource(R.drawable.play_button);
            stopService(new Intent(this, MusicService.class));

        }
        //if status false then the music is stopped, so start the music
        else{
            status = true;
            button.setBackgroundResource(R.drawable.stop_button);
            startService(new Intent(this, MusicService.class));
        }
    }
}
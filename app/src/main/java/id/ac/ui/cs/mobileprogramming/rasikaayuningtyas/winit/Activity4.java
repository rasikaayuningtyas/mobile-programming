package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.widget.Toast;
import android.util.Log;

import com.google.android.material.textfield.TextInputEditText;

public class Activity4 extends AppCompatActivity {

    //private TextView eventText, eventDate;
    TextInputEditText groupname, streamingtimes, votingtimes, mentiontimes;
    Button saveBtn;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_4);

        groupname = findViewById(R.id.groupnameinput);
        streamingtimes = findViewById(R.id.streamingtimeinput);
        votingtimes = findViewById(R.id.votingtimesinput);
        mentiontimes = findViewById(R.id.mentiontimesinput);
        saveBtn = findViewById(R.id.saveBtn);

        //Initialize database
        database = RoomDB.getInstance(this);
        //store database
        dataList = database.mainDao().getAll();


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = groupname.getText().toString().trim();
                int streamingNum = Integer.parseInt(streamingtimes.getText().toString());
                int votingNum = Integer.parseInt(votingtimes.getText().toString());
                int mentionNum = Integer.parseInt(mentiontimes.getText().toString());
                Log.i("msg",""+mentionNum);
                if (!groupName.equals("")){
                    MainData data = new MainData();
                    data.setGroup(groupName);
                    data.setStreamingNumber(streamingNum);
                    data.setVotingNumber(votingNum);
                    data.setMentionNumber(mentionNum);
                    data.setCurrentStream(0);
                    data.setCurrentVoting(0);
                    data.setCurrentMention(0);
                    database.mainDao().insert(data);
                    groupname.setText("");
                    streamingtimes.setText("");
                    votingtimes.setText("");
                    mentiontimes.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }
}
package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.database.Cursor;

import java.util.GregorianCalendar;
import java.util.List;
import android.util.Log;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import android.os.Build;
import android.app.NotificationChannel;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    //button
    TextView eventText, eventDate;
    FloatingActionButton floatingBtn;
    RecyclerView recyclerView;
    //private TextView eventText, eventDate;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    NotificationManager notificationManager;
    int notifID = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        createNotificationChannel();
        eventText = (TextView) findViewById(R.id.event);
        eventDate = (TextView) findViewById(R.id.eventdate);

        floatingBtn = (FloatingActionButton) findViewById(R.id.floatingBtn);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(MainActivity.this, Activity4.class);
                startActivity(intent3);
            }
        });


        //Initialize database
        database = RoomDB.getInstance(this);

        //store database
        dataList = database.mainDao().getAll();

        //LinearLayout Manager
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        //Adapter
        adapter = new MainAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        for (int i=0; i<dataList.size(); i++){
            int curr_stream = dataList.get(i).getCurrentStream();
            int goal_stream = dataList.get(i).getStreamingNumber();
            float stream_percentage = (float)curr_stream/goal_stream;

            int curr_vote = dataList.get(i).getCurrentVoting();
            int goal_vote = dataList.get(i).getVotingNumber();
            float vote_percentage = (float)curr_vote/goal_vote;

            int curr_mention = dataList.get(i).getCurrentMention();
            int goal_mention = dataList.get(i).getMentionNumber();
            float mention_percentage = (float)curr_mention/goal_mention;

            if ((0.8 <= stream_percentage && stream_percentage < 1) |
                    (0.8 <= vote_percentage && vote_percentage < 1) |
                    (0.8 <= mention_percentage && mention_percentage < 1)){
                Log.i("msg", "masuk sini");
                notifyGoal(dataList.get(i));
            }
        }

        //Alarm
        Long alertTime = new GregorianCalendar().getTimeInMillis() + 10 * 1000;
        Intent alertIntent = new Intent(MainActivity.this, HourlyAlertReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime, PendingIntent.getBroadcast(
                MainActivity.this, 1, alertIntent, PendingIntent.FLAG_UPDATE_CURRENT));


//        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI, null,
//                null, null, null);
//        while (cursor.moveToNext()) {
//            if (cursor!=null){
//                String event = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
//                String date = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
//
//                eventText.setText(event);
//                eventDate.setText(date);
//            }
//            else{
//                return;
//            }
//        }
//        cursor.close();

    }

    private void notifyGoal(MainData object) {
        Log.i("msg", "masuk kok");

        Intent moreInfoIntent = new Intent(this, Activity2.class);
        moreInfoIntent.putExtra("sID", object.getID());
        moreInfoIntent.putExtra("name", object.getGroup());
        moreInfoIntent.putExtra("streaming", object.getStreamingNumber());
        moreInfoIntent.putExtra("voting", object.getVotingNumber());
        moreInfoIntent.putExtra("mention", object.getMentionNumber());
        moreInfoIntent.putExtra("currentStream", object.getCurrentStream());
        moreInfoIntent.putExtra("currentVote", object.getCurrentVoting());
        moreInfoIntent.putExtra("currentMention", object.getCurrentMention());

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addParentStack(Activity2.class);
        taskStackBuilder.addNextIntent(moreInfoIntent);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(this,"my_id")
                .setContentTitle(object.getGroup()+ " Goal almost complete!")
                .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                .setContentText("Finish your goal to win a trophy for "+ object.getGroup())
                .setTicker("Alert New Message")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notifID, notificationBuilder.build());

    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_id", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
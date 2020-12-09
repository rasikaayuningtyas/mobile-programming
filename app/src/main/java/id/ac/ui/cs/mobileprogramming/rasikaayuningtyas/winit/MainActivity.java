package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import android.util.Log;
import android.Manifest;
import android.net.Uri;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import android.os.Build;
import android.app.NotificationChannel;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 5;
    //button
    TextView eventText, eventDate;
    FloatingActionButton floatingBtn;
    RecyclerView recyclerView;
    //private TextView eventText, eventDate;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    int notifID = 16;

    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();
    public static ArrayList<String> events = new ArrayList<String>();

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

        //Calendar
        readCalendarEvent(MainActivity.this);
        Log.i("msgku", "mashooook");
        for (int i=0; i<events.size(); i++){
            Log.i("msgku lagi", "mashoook lagi");
            Log.i("events", events.get(i));
        }
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



    public void readCalendarEvent(Context context) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR}, MY_PERMISSIONS_REQUEST_READ_CALENDAR);
        }

        ContentResolver contentResolver = getContentResolver();
        String selection = CalendarContract.Calendars.VISIBLE + " = 1 AND " + CalendarContract.Calendars.IS_PRIMARY + "=1";
        Cursor cursor = contentResolver.query(CalendarContract.Calendars.CONTENT_URI, new String[] { "calendar_id", "title", "description",
                "dtstart", "dtend", "eventLocation" }, selection, null, null);

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        cursor.moveToFirst();

        if (cursor != null && cursor.getCount() > 0){
            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            descriptions.add(cursor.getString(2));
            cursor.moveToNext();

        }
        if (cursor != null){
            cursor.close();
    }
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = null;
        formatter = new SimpleDateFormat(
                    "dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }



}
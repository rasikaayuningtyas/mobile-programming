package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.winit;

import android.os.AsyncTask;
import android.widget.TextView;

public class GoalCompletedAsyncTask extends AsyncTask<Void, Void, String> {

    private TextView title;
    private String idol;

    public GoalCompletedAsyncTask(TextView text, String name){
        title = text;
        idol = name;
    }
    @Override
    protected String doInBackground(Void... voids) {
        try{
            Thread.sleep(5000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        return "Congrats you complete the mission for " + idol + "!";
    }

    protected void onPostExecute(String result){
        title.setText(result);
        title.setTextSize(32);
    }
}

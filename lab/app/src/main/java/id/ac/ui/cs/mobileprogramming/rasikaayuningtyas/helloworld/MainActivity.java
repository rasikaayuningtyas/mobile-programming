package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    TextView txtView;
    boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        txtView = (TextView)findViewById(R.id.textView);
        status = true;
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (this.status) {
            this.status = false;
            txtView.setText("You will have a nice day");
        }else{
            this.status = true;
            txtView.setText("Hello, you :)");
        }
    }
}



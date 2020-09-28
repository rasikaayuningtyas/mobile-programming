package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button button;
    Button regButton;
    TextView txtView;
    TextInputLayout txtInputLayout;
    EditText editText;
    String name;
    boolean status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        regButton = (Button) findViewById(R.id.button2);
        txtView = (TextView)findViewById(R.id.textView);
        txtInputLayout = (TextInputLayout) findViewById(R.id.textInputLayout);
        editText = (EditText) findViewById(R.id.editTextTextPersonName);
        status = true;
        name = editText.getText().toString().trim();
        button.setOnClickListener(this);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(name);
            }
        });
    }

    public boolean register(String myname){
        if(myname == ""){
            return false;
        }
        name = editText.getText().toString().trim();
        txtView.setText(("Hello, " + name + " :)"));
        return true;
    }

    @Override
    public void onClick(View view) {
        if (this.status) {
            this.status = false;
            txtView.setText("You will have a nice day");
        }else{
            this.status = true;
            txtView.setText("Hello, " + name + " :)");
        }
    }
}



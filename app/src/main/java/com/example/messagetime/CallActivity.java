package com.example.messagetime;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class CallActivity extends AppCompatActivity {
    TextView personNameView;
    TextView personNumberView;
    ImageButton phonecallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        personNameView = findViewById(R.id.personNameView);
        personNumberView = findViewById(R.id.personNumberView);
        Intent personData = getIntent();
        if (personData != null){
            Bundle inArgs = personData.getExtras();
            String name = "";
            String number = "";
            try {
                name = inArgs.getString("NAME", "");
                number = inArgs.getString("NUMBER", "");
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            if (name.length() < 1 || number.length() < 1){
                Toast.makeText(this, "Error loading data", Toast.LENGTH_LONG).show();
                finish();
            } else {
                personNameView.setText(name);
                personNumberView.setText(number);
            }
        }
    }

    public void dialNumber(){
        String number = String.format("tel: %s",personNumberView.getText().toString());
        Toast.makeText(this, "Phone Status: DIALING: " + number, Toast.LENGTH_LONG).show();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(number));
        if (callIntent.resolveActivity(getPackageManager()) != null){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                startActivity(callIntent);
            else
                Toast.makeText(this, "Require call permission to be set", Toast.LENGTH_SHORT).show();
        } else {
            Log.e("ERROR", "Cant resolve action for call intent");
        }
    }

    public void callNumber(View v){
        dialNumber();
    }
}

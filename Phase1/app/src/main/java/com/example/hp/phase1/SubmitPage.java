package com.example.hp.phase1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;


public class SubmitPage extends ActionBarActivity {

    private Button Submit;
    private Button Previous;
    private EditText ConfirmationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_submit_page);

        Submit = (Button) findViewById(R.id.submit);
        Previous = (Button) findViewById(R.id.previous);
        ConfirmationCode= (EditText) findViewById(R.id.code);

        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FacebookTwitterPage.class);
                startActivity(intent);
            }

        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ConfirmationCode.getText().toString() != null && !ConfirmationCode.getText().toString().isEmpty()) {
                    int code = Integer.parseInt(ConfirmationCode.getText().toString());
                }

                Intent intent = new Intent(context, FriendSignUp.class);
                startActivity(intent);
            }

        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        setTitle("Registration(3)");


        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }





    }


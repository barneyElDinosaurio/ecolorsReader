package com.wxyz.root.ecolorsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by root on 4/12/16.
 */

public class AddPoints extends AppCompatActivity {


    FirebaseDatabase db;
    DatabaseReference ref;
    TextView idText;
    EditText puntos;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("id");

        db = FirebaseDatabase.getInstance();
        ref = db.getReference(message);

        idText = (TextView) findViewById(R.id.id);
        idText.setText(message);

        puntos = (EditText) findViewById(R.id.puntos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPoints();
            }
        });

    }

    void addPoints(){
        ref.setValue(puntos.getText());
        puntos.setText("");
    }

}

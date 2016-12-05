package com.wxyz.root.ecolorsreader;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by root on 4/12/16.
 */

public class AddPoints extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;
    TextView idText;
    EditText puntos;
    Button btn;
    boolean paso,paso2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paso = true;

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("id");

        db = FirebaseDatabase.getInstance();
        ref = db.getReference(message);

        idText = (TextView) findViewById(R.id.id);
        idText.setText(message);

        puntos = (EditText) findViewById(R.id.puntos);

        btn = (Button) findViewById(R.id.enviar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//BTN CLICK

                new AlertDialog.Builder(AddPoints.this)//ALERT DIALOG
                        .setTitle("Agregar Puntos")
                        .setMessage("Esta seguro q desea agregar estos puntos?")

                        //Positive Alert Dialog Btn
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with adding points
                                ref.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(paso) {
                                            //Sumando puntos
                                            ref.setValue(Integer.valueOf(dataSnapshot.getValue().toString()) + Integer.valueOf(puntos.getText().toString()));
                                            paso = false;
                                            puntos.setText("");//Borrando texto del EdiTxt
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        })

                        //Negative Alert Dialog Btn
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)//icon alert dialog
                        .show();//show alert dialog
            }

        });


    }


}

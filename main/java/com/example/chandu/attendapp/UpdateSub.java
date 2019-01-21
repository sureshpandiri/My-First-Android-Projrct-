package com.example.chandu.attendapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.persistence.room.Update;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateSub extends AppCompatActivity {
    private TextView sname;
    private ImageView cheat;
    private Dialog epic;
    public static final int Up_ok = 4;
    public static final String Extra_upid = "com.example.chandu.attendapp_upID";
    public static final String Extra_uptitle = "com.example.chandu.attendapp_upET";
    public static final String Extra_uptotal = "com.example.chandu.attendapp_upT";
    public static final String Extra_upattend = "com.example.chandu.attendapp_upA";
    private NumberPicker uptot;
    private NumberPicker upatt;
    int ex_total;
    int ex_attend;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sub);

        epic = new Dialog(this);
        sname = findViewById(R.id.up_title);
        uptot = findViewById(R.id.up_totnp);
        upatt = findViewById(R.id.up_attnp);
        uptot.setMaxValue(0);
        uptot.setMaxValue(5);
        upatt.setMinValue(0);
        upatt.setMaxValue(5);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        String text = intent.getStringExtra(Extra_uptitle);
        sname.setText(text);

        //int ex_total= intent.getIntExtra(Extra_total,0);
        //int ex_attend=intent.getIntExtra(Extra_attend,0);
        setTitle("Update attendance");
    }

    private void updatesub() {
        int t = uptot.getValue();
        int a = upatt.getValue();

        if (t < a) {
            epic.setContentView(R.layout.cheat);
            cheat = (ImageView) epic.findViewById(R.id.cheat_close);
            cheat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epic.dismiss();
                }
            });
            epic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epic.show();
            epic.setCanceledOnTouchOutside(false);
            epic.setCancelable(false);
            return;
        }
        Intent data = new Intent();

        data.putExtra(Extra_uptotal, t);
        data.putExtra(Extra_upattend, a);
        int id = getIntent().getIntExtra(Extra_upid, -1);

        if (id != -1) {
            data.putExtra(Extra_upid, id);
        }
        setResult(Up_ok, data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                updatesub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

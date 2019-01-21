package com.example.chandu.attendapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

public class Add_Sub extends AppCompatActivity {
    public static final int Set_ok = 3;
    public static final String Extra_title = "com.example.chandu.attendapp_ET";
    public static final String Extra_total = "com.example.chandu.attendapp_T";
    public static final String Extra_attend = "com.example.chandu.attendapp_A";
    private EditText title;
    private NumberPicker tot;
    private NumberPicker att;
    private Dialog epic;
    private ImageView cheat,sub_img;
    private Button sub_but;
    public static final int PICK_IMG=12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__sub);

        epic = new Dialog(this);
        title = findViewById(R.id.ettitle);
        tot = findViewById(R.id.totnp);
        att = findViewById(R.id.attnp);
        tot.setMaxValue(0);
        tot.setMaxValue(100);
        att.setMinValue(0);
        att.setMaxValue(100);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Subject");





    }

    private void saveSub() {
        String sub = title.getText().toString();
        int total = tot.getValue();
        int attend = att.getValue();
        if (total < attend) {
            epic.setContentView(R.layout.cheat);
            cheat = (ImageView) epic.findViewById(R.id.cheat_close);
            cheat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epic.dismiss();
                }
            });
            epic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epic.setCanceledOnTouchOutside(false);
            epic.setCancelable(false);
            epic.show();
            return;
        }
        if (sub.trim().isEmpty()) {
            Toast.makeText(this, "please insert text", Toast.LENGTH_LONG).show();
            return;
        }



        Intent data = new Intent();
        data.putExtra(Extra_title, sub);
        data.putExtra(Extra_total, total);
        data.putExtra(Extra_attend, attend);

        setResult(Set_ok, data);
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
                saveSub();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

package com.example.chandu.attendapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE=1;

    PermissionManager permissionManager;
    private SubjectViewModel subjectViewModel;
    public static final int Set_ok = 3;
    public static final int Up_ok = 4;
    public static final int Add_req = 1;
    public static final int Up_req = 2;
    List<Subject> allsub = new ArrayList<>();
    int ut = 0, ua = 0, uid;
    String uti;
    ImageView pos, neg, avg, close;
    TextView postive;
    TextView posT;
    TextView posm;
    TextView negm;
    TextView negati;
    TextView option;


    TextView negT;

    Dialog epic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonadd = findViewById(R.id.fab);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You have already granted this permission!",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Add_Sub.class);
                    startActivityForResult(intent, Add_req);
                }
                else {
                    requestStoragePermission();
                }

            }
        });


/*
 permissionManager = new PermissionManager() {
        };
        permissionManager.checkAndRequestPermissions(this);


<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>


    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>

    <uses-permission android:name="android.permission.INTERNET"/>

    <uses-permission android:name="android.permission.READ_CONTACTS"/>
    <uses-permission android:name="android.permission.WRITE_CONTACTS"/>
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>

    <uses-permission android:name="android.permission.BLUETOOTH"/>

    <uses-permission android:name="android.permission.CAMERA"/>

    <uses-permission android:name="android.permission.READ_CALL_LOG"/>
    <uses-permission android:name="android.permission.WRITE_CALL_LOG"/>
    <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS"/>

    <uses-permission android:name="android.permission.RECORD_AUDIO"/>

    <uses-permission android:name="android.permission.ANSWER_PHONE_CALLS"/>
    <uses-permission android:name="android.permission.READ_PHONE_NUMBERS"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    <uses-permission android:name="android.permission.USE_SIP"/>
    <uses-permission android:name="android.permission.ADD_VOICEMAIL"/>
    <uses-permission android:name="android.permission.CALL_PHONE"/>

    <uses-permission android:name="android.permission.RECEIVE_SMS"/>
    <uses-permission android:name="android.permission.SEND_SMS"/>
    <uses-permission android:name="android.permission.WRITE_SMS"/>
    <uses-permission android:name="android.permission.RECEIVE_WAP_PUSH"/>
    <uses-permission android:name="android.permission.RECEIVE_MMS"/>
    <uses-permission android:name="android.permission.READ_SMS"/>*/

        epic = new Dialog(this);

        final RecyclerView recyclerView = findViewById(R.id.re_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final SubjectAdapter adapter = new SubjectAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        subjectViewModel = ViewModelProviders.of(this).get(SubjectViewModel.class);
        subjectViewModel.getallsubs().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                adapter.submitList(subjects);


            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
                //subjectViewModel.delete(adapter.subjectAt(viewHolder.getAdapterPosition()));
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Are You Sure Want To Delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subjectViewModel.delete(adapter.subjectAt(viewHolder.getAdapterPosition()));
                        Toast.makeText(MainActivity.this, "Subject Deleted", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });
                setFinishOnTouchOutside(false);
                // alert.setCanceledOnTouchOutside(false);
                alert.setCancelable(false);
                alert.create().show();


            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnClickItemListener(new SubjectAdapter.onCilckItemListener() {
            @Override
            public void itemClick(Subject subject) {

                uid = subject.getId();
                ut = subject.getTotal();
                ua = subject.getAttended();
                uti = subject.getTitle();

                Intent intent = new Intent(MainActivity.this, UpdateSub.class);
                intent.putExtra(UpdateSub.Extra_upid, uid);
                intent.putExtra(UpdateSub.Extra_uptitle, uti);
                intent.putExtra(UpdateSub.Extra_uptotal, ut);
                intent.putExtra(UpdateSub.Extra_upattend, ua);
                startActivityForResult(intent, Up_req);

            }
        });


    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == Add_req && resultCode == Set_ok) {
            super.onActivityResult(requestCode, resultCode, data);
            String title = data.getStringExtra(Add_Sub.Extra_title);
            int total = data.getIntExtra(Add_Sub.Extra_total, 1);
            int attend = data.getIntExtra(Add_Sub.Extra_attend, 1);

            Subject subject = new Subject(title, total, attend);
            subjectViewModel.insert(subject);

            Toast.makeText(this, "Subject Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == Up_req && resultCode == Up_ok) {

            super.onActivityResult(requestCode, resultCode, data);
            int id = data.getIntExtra(UpdateSub.Extra_upid, -1);

            if (id == -1) {
                Toast.makeText(this, "Attendance can not be updated", Toast.LENGTH_SHORT).show();
                return;
            }


            int t = data.getIntExtra(UpdateSub.Extra_uptotal, 1);
            int a = data.getIntExtra(UpdateSub.Extra_upattend, 1);
            ut = ut + t;
            ua = ua + a;
            Subject subject = new Subject(uti, ut, ua);
            subject.setId(id);
            subjectViewModel.update(subject);
            Toast.makeText(this, "Attendance updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Subject not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public float getReport() {
        Subject subject;
        float repT = 0, repA = 0;
        float rep;
        allsub = subjectViewModel.getallsubs().getValue();
        int i = allsub.size();
        for (int j = 0; j < i; j++) {
            subject = allsub.get(j);
            repT = repT + subject.getTotal();
            repA = repA + subject.getAttended();
        }
        rep = repA / repT;
        return rep * 100;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.rpt_id:
                // subjectViewModel.report();
                //Toast.makeText(this, "report generated", Toast.LENGTH_SHORT).show();
                float rp = 0;
                rp = getReport();
                if (rp >= 75) {
                    epic.setContentView(R.layout.positive);
                    pos = (ImageView) epic.findViewById(R.id.smile);
                    postive = (TextView) epic.findViewById(R.id.TotPer);
                    postive.setText("" + rp + "%");
                    pos.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            epic.dismiss();

                        }
                    });
                    epic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    epic.show();
                } else if (rp <= 65) {
                    epic.setContentView(R.layout.negative);
                    neg = (ImageView) epic.findViewById(R.id.fasak);
                    negati = (TextView) epic.findViewById(R.id.TotPerN);
                    negati.setText("" + rp + "%");
                    neg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            epic.dismiss();
                        }
                    });
                    epic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    epic.show();
                } else {
                    epic.setContentView(R.layout.average);
                    avg = (ImageView) epic.findViewById(R.id.avg_close);
                    posm = (TextView) epic.findViewById(R.id.TotPerAvg);
                    posm.setText("" + rp + "%");
                    avg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            epic.dismiss();
                        }
                    });
                    epic.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    epic.show();

                }
                return true;

            case R.id.about_us:
                Intent i = new Intent(MainActivity.this, AboutUs.class);
                startActivity(i);
                return true;
            case R.id.time_table:
                Toast.makeText(this, "comming in next update", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(this, "not generated", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);

        }
    }


}
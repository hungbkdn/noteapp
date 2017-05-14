package com.example.admin.bknote;



import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.DialogInterface;

import android.content.Intent;

import android.media.RingtoneManager;

import android.net.Uri;

import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.NotificationCompat;

import android.support.v7.widget.Toolbar;

import android.view.KeyEvent;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;



public class TrangChu extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    FloatingActionButton fab;

    FirebaseUser user;

    ListView noteContainer;

    private NoteAdapter adapter;



    private static final int MY_NOTIFICATION_ID = 12345;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {

            finish();

            startActivity(new Intent(this, MainActivity.class));

        }

        user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_main);

        //Khởi tạo toolbar

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //Không hiện tiêu đề

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Hiện nút back

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //quay lai activity truoc

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                finish();

            }

        });

        //listview

        noteContainer = (ListView)findViewById(R.id.noteContainer);

        adapter = new NoteAdapter(TrangChu.this, new ArrayList<Note>());

        noteContainer.setAdapter(adapter);

        //xu ly su kien cho fab

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                startActivity(new Intent(TrangChu.this, Add.class));

            }

        });

        noteContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Note note = (Note)(noteContainer.getItemAtPosition(position));

                Intent intent = new Intent(TrangChu.this, Edit.class);

                intent.putExtra("uId",note.getuId());

                intent.putExtra("note", note.getNote());

                intent.putExtra("time", note.getDate());

                intent.putExtra("hour", note.getTime());

                startActivity(intent);

            }

        });



        noteContainer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long l) {

                Note note = (Note)(noteContainer.getItemAtPosition(pos));

                final String ref = note.getuId();

                AlertDialog.Builder builder = new AlertDialog.Builder(TrangChu.this);

                builder.setTitle("BKNote");

                builder.setMessage("Are you sure you want to delete this note?");

                builder.setCancelable(false);

                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(TrangChu.this, "Cancelled", Toast.LENGTH_SHORT).show();

                    }

                });

                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {

                        databaseReference.child(user.getUid()).child(ref).removeValue();

                        Toast.makeText(TrangChu.this, "Deleting", Toast.LENGTH_SHORT).show();

                        dialogInterface.dismiss();

                    }

                });

                builder.setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override

                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                            dialog.dismiss();

                            return true; // Consumed

                        }

                        else {

                            return false; // Not consumed

                        }

                    }

                });

                AlertDialog alertDialog = builder.create();

                alertDialog.show();



                return true;

            }





        });





        databaseReference.child(user.getUid().toString()).addChildEventListener(new ChildEventListener() {

            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Note note = dataSnapshot.getValue(Note.class);

                startNotify(note);

                adapter.add(note);

                adapter.notifyDataSetChanged();

            }



            @Override

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Note note = dataSnapshot.getValue(Note.class);

                adapter.add(note);

                adapter.notifyDataSetChanged();

                for(int i=0;i<adapter.getCount();i++)

                {

                    if (adapter.getItem(i).getuId() == note.getuId())

                    {

                        adapter.update(note, i);

                        adapter.notifyDataSetChanged();

                        Toast.makeText(TrangChu.this, "Edit success", Toast.LENGTH_SHORT).show();

                        finish();

                        startActivity(getIntent());





                    }

                }

            }



            @Override

            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Note note = dataSnapshot.getValue(Note.class);

                for(int i=0;i<adapter.getCount();i++)

                {

                    if(adapter.getItem(i).getuId() == note.getuId())

                    {

                        adapter.remove(i);

                        adapter.notifyDataSetChanged();

                        Toast.makeText(TrangChu.this, "Edit success", Toast.LENGTH_SHORT).show();

                        finish();

                        startActivity(getIntent());

                    }

                }

            }



            @Override

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {



            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });



        //lay du lieu tu firebase vao arraylist

    }







    //inflate menu

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }



    // Bắt sự kiện cho các item Menu

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {

            case R.id.mnAdd:

                startActivity(new Intent(this, Add.class));

                break;

            case R.id.mnAccount:

                firebaseAuth.signOut();

                finish();

                startActivity(new Intent(this, MainActivity.class));

                break;

            case android.R.id.home:

                finish();

                break;

        }

        return super.onOptionsItemSelected(item);

    }

    //Alarm - xu ly chua duoc

    /*

    public void startAlert(Note note) {

        String date = note.getDate();

        String[]dmy = date.split("-");

        String time = note.getTime();

        String[] hm = time.split(":");

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.YEAR, Integer.parseInt(dmy[2]));

        calendar.set(Calendar.MONTH, Integer.parseInt(dmy[1]));

        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dmy[0]));

        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hm[0]));

        calendar.set(Calendar.MINUTE, Integer.parseInt(hm[1]));

        Intent intent = new Intent(this, MyBroadcastReceiver.class);

        long diff = calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC_WAKEUP, diff , pendingIntent);

        Toast.makeText(TrangChu.this, "Alarm in: "+diff+" seconds", Toast.LENGTH_SHORT).show();

    }*/

    public void startNotify(Note note)

    {

        NotificationCompat.Builder notBuilder = new NotificationCompat.Builder(this);

        notBuilder.setAutoCancel(true);

        notBuilder.setSmallIcon(R.mipmap.bknotelogo);

        notBuilder.setTicker("This is a ticker");

        notBuilder.setContentTitle("BKNote");

        notBuilder.setContentText("New note has been add:"+note.getNote());

        Uri path = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notBuilder.setSound(path);

        //edit

        Intent editIntent = new Intent();

        editIntent.setAction(AppConstant.EDIT_ACTION);

        //truyen tham so qua edit

        editIntent.putExtra("uId",note.getuId());

        editIntent.putExtra("note", note.getNote());

        editIntent.putExtra("time", note.getDate());

        editIntent.putExtra("hour", note.getTime());

        PendingIntent pendingIntentEdit = PendingIntent.getBroadcast(this, 12345, editIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notBuilder.addAction(R.drawable.ic_edit, "Edit this note", pendingIntentEdit);

        //delete - chua xu ly

        Intent deleteIntent = new Intent();

        deleteIntent.setAction(AppConstant.DELETE_ACTION);

        PendingIntent pendingIntentDelete = PendingIntent.getBroadcast(this, 12345, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notBuilder.addAction(R.drawable.ic_delete, "Delete this note", pendingIntentDelete);

        NotificationManager notificationService  = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);

        notificationService.notify(MY_NOTIFICATION_ID, notBuilder.getNotification());

    }

}
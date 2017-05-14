package com.example.admin.bknote;



import android.app.DatePickerDialog;

import android.app.TimePickerDialog;

import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;

import android.view.Menu;

import android.view.MenuItem;

import android.view.View;

import android.widget.Button;

import android.widget.DatePicker;

import android.widget.EditText;

import android.widget.TextView;

import android.widget.TimePicker;

import android.widget.Toast;



import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;







public class Add extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    EditText idText;

    TextView loinhac, timefinish;

    Button idBt, idBt2;

    DatabaseReference databaseReference;

    String date;

    String hour;

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null)

        {

            finish();

            startActivity(new Intent(this, MainActivity.class));

        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        idText = (EditText) findViewById(R.id.idText);

        idBt = (Button) findViewById(R.id.idButton1);

        idBt2 = (Button) findViewById(R.id.idButton2);

        loinhac = (TextView) findViewById(R.id.loinhac);

        timefinish = (TextView) findViewById(R.id.timefin);

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

        idBt.setOnClickListener(this);

        idBt2.setOnClickListener(this);


    }

    @Override

    public void onClick(View v) {

        if (v == idBt)

        {

            showDatePickerDialog();

        }

        if (v == idBt2)

        {

            showTimePickerDialog();

        }

    }

    public void showDatePickerDialog()

    {

        // Get Current Date

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);

        mMonth = c.get(Calendar.MONTH);

        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,

                new DatePickerDialog.OnDateSetListener() {


                    @Override

                    public void onDateSet(DatePicker view, int year,

                                          int monthOfYear, int dayOfMonth) {


                        loinhac.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                    }

                }, mYear, mMonth, mDay);

        datePickerDialog.setTitle("Choose date");

        datePickerDialog.show();

        ;

    }

    public void showTimePickerDialog()

    {

        // Get Current Time

        final Calendar c = Calendar.getInstance();

        mHour = c.get(Calendar.HOUR_OF_DAY);

        mMinute = c.get(Calendar.MINUTE);


        // Launch Time Picker Dialog

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,

                new TimePickerDialog.OnTimeSetListener() {


                    @Override

                    public void onTimeSet(TimePicker view, int hourOfDay,

                                          int minute) {


                        timefinish.setText(hourOfDay + ":" + minute);

                        hour = hourOfDay + ":" + minute;

                    }

                }, mHour, mMinute, false);

        timePickerDialog.show();

    }


    //inflate menu

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuadd, menu);

        return super.onCreateOptionsMenu(menu);

    }


    // Bắt sự kiện cho các item Menu

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.mnAllow:

                String description = idText.getText() + "";


                FirebaseUser user = firebaseAuth.getCurrentUser();

                String ref = databaseReference.child(user.getUid()).push().getKey();

                Note note = new Note(description, date, hour, ref);

                databaseReference.child(user.getUid()).child(ref).setValue(note);

                Toast.makeText(this, "Saving data", Toast.LENGTH_SHORT).show();

                finish();

                break;

            case R.id.mnCancel:

                //xu ly

                break;

            case android.R.id.home:

                //xu ly

                break;

        }

        return super.onOptionsItemSelected(item);

    }
}
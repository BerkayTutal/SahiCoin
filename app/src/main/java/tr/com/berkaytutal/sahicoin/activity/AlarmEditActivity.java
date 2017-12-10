package tr.com.berkaytutal.sahicoin.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import tr.com.berkaytutal.sahicoin.R;
import tr.com.berkaytutal.sahicoin.model.AlarmPOJO;
import tr.com.berkaytutal.sahicoin.utils.DatabaseHandler;

public class AlarmEditActivity extends AppCompatActivity {

    private Button saveButton;
    private Button deleteButton;
    private EditText priceEditText;
    private Switch overUnderSwitch;
    private Activity activity;
    private DatabaseHandler db;

    private AlarmPOJO alarmPOJO;

    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);
        activity = this;
        db = new DatabaseHandler(this);
        saveButton = (Button) findViewById(R.id.saveButton);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        overUnderSwitch = (Switch) findViewById(R.id.overUnderSwitch);


        Intent intent = getIntent();
        isEdit = intent.hasExtra("uuid");
        final String uuid = intent.getStringExtra("uuid");
        if(isEdit){
            alarmPOJO = db.getSingleAlarm(uuid);
            overUnderSwitch.setChecked(alarmPOJO.isMoreExpensive());
            priceEditText.setText(alarmPOJO.getPrice() + "");
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String price = priceEditText.getText().toString().trim();
                if (price.equals("")) {
                    Toast.makeText(activity, "Please enter a valid price", Toast.LENGTH_SHORT).show();
                } else {
                    AlarmPOJO alarm = new AlarmPOJO(overUnderSwitch.isChecked(), Integer.parseInt(price));
                    if (isEdit) {
                        alarm.setUUID(uuid);
                        db.deleteAlarm(alarm);
                    }
                    db.addAlarm(alarm);
                    finish();
                }
            }
        });

        deleteButton = (Button) findViewById(R.id.deleteButton);
        if(!isEdit){
            deleteButton.setVisibility(View.GONE);
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAlarm(alarmPOJO);
            }
        });
    }
}

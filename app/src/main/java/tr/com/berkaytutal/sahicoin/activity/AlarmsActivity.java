package tr.com.berkaytutal.sahicoin.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import tr.com.berkaytutal.sahicoin.R;
import tr.com.berkaytutal.sahicoin.adapter.AlarmAdapter;
import tr.com.berkaytutal.sahicoin.model.AlarmPOJO;
import tr.com.berkaytutal.sahicoin.utils.DatabaseHandler;

public class AlarmsActivity extends AppCompatActivity {

    private ListView mAlarmsListView;
    private Button mNewAlarmButton;
    private Activity activity;
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);

        activity = this;



    }

    @Override
    protected void onResume() {


        super.onResume();


        mNewAlarmButton = (Button) findViewById(R.id.addAlarmButton);
        mNewAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, AlarmEditActivity.class));
            }
        });

        mAlarmsListView = (ListView) findViewById(R.id.alarmsListView);


        db = new DatabaseHandler(this);
        List<AlarmPOJO> alarms = db.getAllAlarms();
        AlarmAdapter alarmAdapter = new AlarmAdapter(alarms, this);

        mAlarmsListView.setAdapter(alarmAdapter);
    }
}

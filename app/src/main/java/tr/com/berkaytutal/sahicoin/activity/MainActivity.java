package tr.com.berkaytutal.sahicoin.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import tr.com.berkaytutal.sahicoin.AlarmReceiver;
import tr.com.berkaytutal.sahicoin.R;
import tr.com.berkaytutal.sahicoin.tasks.CurrentLoaderAsyncTask;
import tr.com.berkaytutal.sahicoin.utils.GlobalHolder;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    private Activity mainActivity;
    private GlobalHolder globalHolder;
    private TextView mPriceTextView;
    private TextView mCurrencyTextView;
    private TextView mSyncDateTextView;
    private Switch mCurrencySwitch;

    private SwipeRefreshLayout swipeRefreshLayout;

    private Button mHistoryButton;
    private Button mALarmsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainActivity = this;

        Intent intent = new Intent(this, AlarmReceiver.class);

        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5 * 1000, 60 * 1000, alarmIntent);


        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mCurrencyTextView = (TextView) findViewById(R.id.currencyTextView);
        mSyncDateTextView = (TextView) findViewById(R.id.dateTextView);
        mCurrencySwitch = (Switch) findViewById(R.id.currencySwitch);

        mHistoryButton = (Button) findViewById(R.id.viewHistoryButton);


        globalHolder = (GlobalHolder) getApplicationContext();


//TODO buraya bir listview yolla
//        HistoryLoaderAsyncTask historyLoaderAsyncTask = new HistoryLoaderAsyncTask(mainActivity);
//        historyLoaderAsyncTask.execute("");


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mainSwipeRefreshLayout);

        CurrentLoaderAsyncTask currentLoaderAsyncTask = new CurrentLoaderAsyncTask(mainActivity, mPriceTextView, mSyncDateTextView, swipeRefreshLayout);
        currentLoaderAsyncTask.execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CurrentLoaderAsyncTask currentLoaderAsyncTask = new CurrentLoaderAsyncTask(mainActivity, mPriceTextView, mSyncDateTextView, swipeRefreshLayout);
                currentLoaderAsyncTask.execute();
            }
        });


        mCurrencySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Double result = globalHolder.currentInUSD * globalHolder.USD_TL;
                    if (result != 0) {
                        mPriceTextView.setText(String.format("%1.2f", result));
                        mCurrencyTextView.setText("TL");
                    } else {
                        compoundButton.setChecked(false);
                    }
                } else {
                    mPriceTextView.setText(globalHolder.currentInUSD + "");
                    mCurrencyTextView.setText("USD");
                }
            }
        });


        mHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainActivity, HistoryActivity.class));
            }
        });

        mALarmsButton = (Button) findViewById(R.id.alarmsButton);

        mALarmsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.startActivity(new Intent(mainActivity, AlarmsActivity.class));
            }
        });


    }


}

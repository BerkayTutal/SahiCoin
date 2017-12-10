package tr.com.berkaytutal.sahicoin.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tr.com.berkaytutal.sahicoin.R;
import tr.com.berkaytutal.sahicoin.activity.AlarmEditActivity;
import tr.com.berkaytutal.sahicoin.model.AlarmPOJO;

/**
 * Created by berka on 10.12.2017.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater li;
    private List<AlarmPOJO> alarms;
    private Activity activity;

    public AlarmAdapter( List<AlarmPOJO> alarms, Activity activity) {
        li = LayoutInflater.from(activity);
        this.alarms = alarms;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int i) {
        return alarms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final AlarmPOJO alarm = alarms.get(i);

        View viewInflated = li.inflate(R.layout.adaper_alarm, null);
        TextView alarmText = (TextView) viewInflated.findViewById(R.id.alarmText);
        String overUnder = alarm.isMoreExpensive() ? "Over" : "Under";
        alarmText.setText(overUnder + " " + alarm.getPrice() + " USD");

        viewInflated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(activity, AlarmEditActivity.class);
                myIntent.putExtra("uuid", alarm.getUUID()); //Optional parameters
                activity.startActivity(myIntent);

            }
        });

        return viewInflated;
    }
}

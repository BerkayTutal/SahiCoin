package tr.com.berkaytutal.sahicoin;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import java.util.List;

import tr.com.berkaytutal.sahicoin.model.AlarmPOJO;
import tr.com.berkaytutal.sahicoin.model.PricePOJO;
import tr.com.berkaytutal.sahicoin.utils.DatabaseHandler;
import tr.com.berkaytutal.sahicoin.utils.Utils;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHandler db = new DatabaseHandler(context);
        List<AlarmPOJO> alarms = db.getAllAlarms();
        if (alarms.size() > 0) {
            MyAsyncTask myAsyncTask = new MyAsyncTask(alarms, context);
            myAsyncTask.execute();
        }
        System.out.println("Alarm is working");
    }

    class MyAsyncTask extends AsyncTask<String, String, PricePOJO> {

        private List<AlarmPOJO> alarms;
        private Context context;

        public MyAsyncTask(List<AlarmPOJO> alarms, Context context) {
            this.alarms = alarms;
            this.context = context;
        }

        @Override
        protected PricePOJO doInBackground(String... strings) {

            if (!Utils.isInternetAvailable()) {
                return null;
            }

            try {
                return Utils.readPriceFromUrl("https://devakademi.sahibinden.com/ticker");
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }


        }

        @Override
        protected void onPostExecute(PricePOJO pricePOJO) {


            if (pricePOJO != null) {
                for (AlarmPOJO alarm :
                        alarms) {

                    String notificationText = "";
                    if (alarm.isMoreExpensive() && pricePOJO.getValue() > alarm.getPrice()) {
                        //todo string
                    } else if (!alarm.isMoreExpensive() && pricePOJO.getValue() < alarm.getPrice()) {
                        //todo string
                    }

                    //TODO notification


                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteAlarm(alarm);
                }

                super.onPostExecute(pricePOJO);
            }
        }
    }
}

package tr.com.berkaytutal.sahicoin.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import java.util.List;

import tr.com.berkaytutal.sahicoin.model.PricePOJO;
import tr.com.berkaytutal.sahicoin.utils.GlobalHolder;
import tr.com.berkaytutal.sahicoin.utils.Utils;

public class HistoryLoaderAsyncTask extends AsyncTask<String, String, List<PricePOJO>> {

    private GlobalHolder globalHolder;
    private Activity activity;
    private ProgressDialog progressDialog;

    public HistoryLoaderAsyncTask(Activity activity) {
        this.globalHolder = (GlobalHolder) activity.getApplicationContext();
        this.activity = activity;
        progressDialog = ProgressDialog.show(activity, "",
                "Loading data", true);
    }

    @Override
    protected List<PricePOJO> doInBackground(String... strings) {

        if (!Utils.isInternetAvailable()) {
            return null;
        }

        try {
            globalHolder.USD_TL = Utils.readDoviz(globalHolder.USD_TRY_URL);
            return Utils.readPricesFromUrl(globalHolder.HISTORY_URL);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }


    }

    @Override
    protected void onPostExecute(List<PricePOJO> pricePOJOs) {
        progressDialog.cancel();

        if (pricePOJOs == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    activity);

            alertDialogBuilder
                    .setMessage("Check your internet connection and try again")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
//                                mainActivity.finish();
//                                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
                        }
                    });


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();


        } else {
            for (PricePOJO price : pricePOJOs) {
                System.out.println(price);
            }
        }
        super.onPostExecute(pricePOJOs);
    }
}


package tr.com.berkaytutal.sahicoin.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import tr.com.berkaytutal.sahicoin.model.PricePOJO;
import tr.com.berkaytutal.sahicoin.utils.GlobalHolder;
import tr.com.berkaytutal.sahicoin.utils.Utils;

public class CurrentLoaderAsyncTask extends AsyncTask<String, String, PricePOJO> {

    private GlobalHolder globalHolder;
    private Activity activity;
    private ProgressDialog progressDialog;
    private TextView mPriceTextView;
    private TextView syncDateTextView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CurrentLoaderAsyncTask(Activity activity, TextView priceTextView, TextView syncDateTextView, SwipeRefreshLayout swipeRefreshLayout) {

        this.swipeRefreshLayout = swipeRefreshLayout;
        this.globalHolder = (GlobalHolder) activity.getApplicationContext();
        this.activity = activity;
        this.syncDateTextView = syncDateTextView;
        mPriceTextView = priceTextView;
        progressDialog = ProgressDialog.show(activity, "",
                "Loading data", true);

    }

    @Override
    protected PricePOJO doInBackground(String... strings) {

        if (!Utils.isInternetAvailable()) {
            return null;
        }

        try {
            globalHolder.USD_TL = Utils.readDoviz(globalHolder.USD_TRY_URL);
            return Utils.readPriceFromUrl(globalHolder.PRICE_URL);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }


    }

    @Override
    protected void onPostExecute(PricePOJO pricePOJO) {
        progressDialog.cancel();

        if (pricePOJO == null) {
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
            System.out.println(pricePOJO);
            mPriceTextView.setText(pricePOJO.getValue() + "");
            syncDateTextView.setText(pricePOJO.getAsDate() + "");
            globalHolder.currentInUSD = pricePOJO.getValue();
            swipeRefreshLayout.setRefreshing(false);
        }
        super.onPostExecute(pricePOJO);
    }
}


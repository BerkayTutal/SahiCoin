package tr.com.berkaytutal.sahicoin.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import tr.com.berkaytutal.sahicoin.model.PricePOJO;
import tr.com.berkaytutal.sahicoin.utils.GlobalHolder;
import tr.com.berkaytutal.sahicoin.utils.Utils;

public class DovizLoaderAsyncTask extends AsyncTask<String, String, Double> {

    private GlobalHolder globalHolder;
    private Activity activity;
    private ProgressDialog progressDialog;

    public DovizLoaderAsyncTask(Activity activity) {
        this.globalHolder = (GlobalHolder) activity.getApplicationContext();
        this.activity = activity;
        progressDialog = ProgressDialog.show(activity, "",
                "Loading data", true);
    }

    @Override
    protected Double doInBackground(String... strings) {

        if (!Utils.isInternetAvailable()) {
            return null;
        }

        try {
            String json = Utils.readUrl(globalHolder.USD_TRY_URL);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            return gson.fromJson(json, JsonElement.class).getAsJsonObject().get("buying").getAsDouble();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }


    }

    @Override
    protected void onPostExecute(Double result) {
        progressDialog.cancel();

        if (result == null) {
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
            globalHolder.USD_TL = result;
            System.out.println(result);
        }
        super.onPostExecute(result);
    }
}


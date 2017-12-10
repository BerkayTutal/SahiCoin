package tr.com.berkaytutal.sahicoin.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import tr.com.berkaytutal.sahicoin.model.PricePOJO;

/**
 * Created by berka on 10.12.2017.
 */

public class Utils {

    public static boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {
            // Log error
        }
        return false;
    }


    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    public static List<PricePOJO> readPricesFromUrl(String historyUrl) throws Exception {
        String json = readUrl(historyUrl);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        PricePOJO[] prices = gson.fromJson(json, PricePOJO[].class);
        return Arrays.asList(prices);
    }

    public static PricePOJO readPriceFromUrl(String currentUrl) throws Exception {
        String json = readUrl(currentUrl);

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        return gson.fromJson(json, PricePOJO.class);

    }

    public static Double readDoviz(String dovizUrl){
        try {
            String json = Utils.readUrl(dovizUrl);

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();

            return gson.fromJson(json, JsonElement.class).getAsJsonObject().get("selling").getAsDouble();
        } catch (Exception e) {
            return null;
        }
    }

}

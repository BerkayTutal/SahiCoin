package tr.com.berkaytutal.sahicoin.utils;

import android.app.Application;

public class GlobalHolder extends Application {
    public String HISTORY_URL = "https://devakademi.sahibinden.com/history";
    public String PRICE_URL = "https://devakademi.sahibinden.com/ticker";
    public String USD_TRY_URL = "https://www.doviz.com/api/v1/currencies/USD/latest";

    public Double USD_TL = 0D;

    public Double currentInUSD = 0D;

}

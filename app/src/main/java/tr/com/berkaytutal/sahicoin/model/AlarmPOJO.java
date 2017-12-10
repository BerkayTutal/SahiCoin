package tr.com.berkaytutal.sahicoin.model;

import java.util.UUID;

/**
 * Created by berka on 10.12.2017.
 */

public class AlarmPOJO {
    private String uuid = UUID.randomUUID().toString();
    private boolean moreExpensive;
    private int price;


    public AlarmPOJO(String uuid, boolean moreExpensive, int price) {
        this.uuid = uuid;
        this.moreExpensive = moreExpensive;
        this.price = price;
    }

    public AlarmPOJO(boolean moreExpensive, int price) {
        this.moreExpensive = moreExpensive;
        this.price = price;
    }

    public boolean isMoreExpensive() {
        return moreExpensive;
    }

    public void setMoreExpensive(boolean moreExpensive) {
        this.moreExpensive = moreExpensive;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String UUID) {
        this.uuid = UUID;
    }
}

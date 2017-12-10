package tr.com.berkaytutal.sahicoin.model;

import java.sql.Timestamp;

/**
 * Created by berka on 10.12.2017.
 */

public class PricePOJO {

    Double value;
    long date;

    public PricePOJO(Double value, long timestamp) {
        this.value = value;
        this.date = timestamp;
    }


    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getAsDate() {
        return new Timestamp(date);
    }

    public void setAsDate(long timestamp) {
        this.date = timestamp;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PricePOJO{" +
                "value=" + value +
                ", date=" + new Timestamp(date) +
                ", date=" + date +
                '}';
    }
}

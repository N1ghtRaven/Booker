package xyz.zaddrot.booker.utils.donation.alerts.entity;

/**
 * Created by night on 20.08.2016.
 */
public class Currency {

    final private String currency;
    public Currency(String currency){
        switch (currency){
            case "RUB":
                this.currency = getRUB();
                break;
            case "USD":
                this.currency = getUSD();
                break;
            case "EUR":
                this.currency = getEUR();
                break;
            default:
                this.currency = null;
                break;
        }
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getRUB() {
        return "RUB";
    }

    public String getUSD() {
        return "USD";
    }

    public String getEUR() {
        return "EUR";
    }
}

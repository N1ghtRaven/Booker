package xyz.zaddrot.booker.utils.donation.alerts.entity;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import xyz.zaddrot.booker.utils.donation.util.Donation;

/**
 * Created by night on 20.08.2016.
 */
public class DonationImpl implements Donation {
    private final Logger logger = LogManager.getLogger(getClass());

    final private String donor;
    final private int cash;
    final private Currency currency;

    public DonationImpl(JSONObject jsonDonation){
        donor = jsonDonation.get("username").toString();
        cash = Integer.valueOf(jsonDonation.get("amount_formatted").toString().replaceAll(" ", ""));
        currency = new Currency(jsonDonation.get("currency").toString());
    }

    public String getUsername() {
        return donor;
    }

    public int getAmount() {
        return cash;
    }

    public Currency getCurrency() {
        return currency;
    }

    private JSONObject toJSON(String... args){
        JSONParser jsonParser = new JSONParser();
        try {
            return (JSONObject) jsonParser.parse(args[0]);
        } catch (ParseException e) { logger.warn(e); return null; }
    }
}

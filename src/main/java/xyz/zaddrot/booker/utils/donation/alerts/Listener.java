package xyz.zaddrot.booker.utils.donation.alerts;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import xyz.zaddrot.booker.utils.donation.alerts.entity.DonationImpl;
import xyz.zaddrot.booker.utils.donation.logger.DonationController;
import xyz.zaddrot.booker.utils.donation.logger.fragment.JSON;
import xyz.zaddrot.booker.utils.donation.util.Donation;

/**
 * Created by night on 20.08.2016.
 */
class Listener {
    private final Logger logger = LogManager.getLogger(getClass());

    void onDonation(Object... args){
        Donation donation = new DonationImpl(toJSON(args[0].toString()));

        DonationController controller = new DonationController();
        controller.createDonation(donation.getUsername(), donation.getAmount());
        //TODO: Блок обработки (Бизнес логика)
    }

    void onTimeout(Object... args){
        JSONObject json = toJSON(args[0].toString());
        logger.warn("Timeout - " + json);
    }

    private JSONObject toJSON(String text){
        try {
            return (JSONObject) new JSONParser().parse(text);
        } catch (ParseException e) { logger.error(e); }
        return null;
    }
}

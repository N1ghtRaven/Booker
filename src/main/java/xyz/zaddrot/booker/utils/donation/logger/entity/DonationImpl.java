package xyz.zaddrot.booker.utils.donation.logger.entity;

import xyz.zaddrot.booker.utils.donation.util.Donation;

/**
 * Created by NightBook on 05.10.2016.
 */
public class DonationImpl implements Donation {
    private String username;
    private int amount;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

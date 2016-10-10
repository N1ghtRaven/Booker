package xyz.zaddrot.booker.utils.sort;

import xyz.zaddrot.booker.utils.donation.logger.entity.DonationImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NightBook on 07.10.2016.
 */
public class Sort {

    public List<DonationImpl> sort(List<DonationImpl> base){
        List<DonationImpl> sorted = new ArrayList<>();
        int cycleValue = base.size();
        for(int i = 0; i < cycleValue;++i){
            DonationImpl max = getMaxAmount(base);
            sorted.add(max);
            base.remove(max);
        }
        return sorted;
    }
    private DonationImpl getMaxAmount(List<DonationImpl> base){
        int maxValue = 0;
        int value = 0;
        for (int i = 0; i < base.size(); i++) {
            if (base.get(i).getAmount() > maxValue) {
                maxValue = base.get(i).getAmount();
                value = i;
            }
        }
        return base.get(value);
    }
}

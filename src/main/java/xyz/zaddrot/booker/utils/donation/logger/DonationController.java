package xyz.zaddrot.booker.utils.donation.logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import xyz.zaddrot.booker.utils.conf.ConfigController;
import xyz.zaddrot.booker.utils.conf.fragment.dto.Properties;
import xyz.zaddrot.booker.utils.donation.logger.entity.DonationImpl;
import xyz.zaddrot.booker.utils.donation.logger.fragment.JSON;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;
import xyz.zaddrot.booker.utils.donation.util.Donation;
import xyz.zaddrot.booker.utils.sort.Sort;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by NightBook on 05.10.2016.
 */
public class DonationController {
    private final Logger logger = LogManager.getLogger(getClass());
    private final JSON json = new JSON();

    public void createDonation(String username, int amount){
        DonationImpl donation = new DonationImpl();
        donation.setUsername(username);
        donation.setAmount(amount);

        json.dumpDonation(donation);

        outRebuild();
    }

    public void outRebuild(){
        json.rebuild();
        Properties cfg = new ConfigController().getAuth().getProperties();
        if(cfg.isTopMounth()) outTopMonth(json.getMonthBase());
        if(cfg.isTopDay()) outTopDay(json.getDayBase());
    }

    private void outTopMonth(List<DonationImpl> base){
        String data = "";
        for(int i = 0; i < 3;++i)if(i <= base.size()) data += base.get(i).getUsername() + " - " + base.get(i).getAmount() + "р." + "\r\n";

        //for (Donation donation: base) data += donation.getUsername() + " - " + donation.getAmount() + "р." + "\r\n";
        writeFile(Path.PATH_TO_MONTH.toFile(), data);
    }
    private void outTopDay(List<DonationImpl> base){
        String data = "";
        for (Donation donation: base) data += donation.getUsername() + " - " + donation.getAmount() + "р. ,";
        writeFile(Path.PATH_TO_DAY.toFile(), data);
    }

    private void writeFile(File file, String data){
        Path.PATH_TO_OUT.toFile().mkdirs();

        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

            out.write(data);
            out.close();
        }catch (IOException e) { logger.error(e); }
    }
}

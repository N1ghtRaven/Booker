package xyz.zaddrot.booker.utils.donation.logger.fragment;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import xyz.zaddrot.booker.utils.donation.logger.entity.DonationImpl;
import xyz.zaddrot.booker.utils.donation.logger.fragment.version.Version;
import xyz.zaddrot.booker.utils.donation.logger.util.Path;
import xyz.zaddrot.booker.utils.donation.util.Donation;
import xyz.zaddrot.booker.utils.sort.Sort;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NightBook on 05.10.2016.
 */
public class JSON {
    private final Logger logger = LogManager.getLogger(getClass());
    private final Sort sort = new Sort();
    private String baseString;
    private String dayBaseString;

    public JSON(){
        Path.PATH_TO_BASE.toFile().mkdirs();
        new Version();
    }

    public void dumpDonation(Donation donation){
        baseString = baseToString(Path.BASE.toFile());
        JSONObject base = toJSON(baseString);
        if(isExist(donation.getUsername(), Path.BASE.toFile(), baseString)) base.replace(String.valueOf(getID(donation.getUsername(), Path.BASE.toFile(), baseString)), donationToJSON(donation, Path.BASE.toFile(), baseString));
        else base.put(base.size(), donationToJSON(donation, Path.BASE.toFile(), baseString));
        dumpBase(Path.BASE.toFile(), base);

        baseString = baseToString(Path.BASE.toFile());

        dayBaseString = baseToString(Path.DAY_BASE.toFile());
        base = toJSON(dayBaseString);
        if(isExist(donation.getUsername(), Path.DAY_BASE.toFile(), dayBaseString)) base.replace(String.valueOf(getID(donation.getUsername(), Path.DAY_BASE.toFile(), dayBaseString)), donationToJSON(donation, Path.DAY_BASE.toFile(), dayBaseString));
        else base.put(base.size(), donationToJSON(donation, Path.DAY_BASE.toFile(), dayBaseString));
        dumpBase(Path.DAY_BASE.toFile(), base);

        dayBaseString = baseToString(Path.DAY_BASE.toFile());
    }

    public void rebuild(){
        baseString = baseToString(Path.BASE.toFile());
        dayBaseString = baseToString(Path.DAY_BASE.toFile());
    }

    public List<DonationImpl> getMonthBase(){
        List<DonationImpl> donationList = new ArrayList<>();
        JSONObject base = toJSON(baseString);
        for(int i = 0; i < base.size();++i){
            JSONObject user = (JSONObject) base.get(String.valueOf(i));

            DonationImpl donation = new DonationImpl();
            donation.setUsername(user.get("username").toString());
            donation.setAmount(Integer.parseInt(user.get("amount").toString()));

            donationList.add(donation);
        }
        return sort.sort(donationList);
    }
    public List<DonationImpl> getDayBase(){
        List<DonationImpl> donationList = new ArrayList<>();
        JSONObject base = toJSON(dayBaseString);
        for(int i = 0; i < base.size();++i){
            JSONObject user = (JSONObject) base.get(String.valueOf(i));

            DonationImpl donation = new DonationImpl();
            donation.setUsername(user.get("username").toString());
            donation.setAmount(Integer.parseInt(user.get("amount").toString()));

            donationList.add(donation);
        }
        return sort.sort(donationList);
    }

    //TODO: Метод сортировки по убыванию

    private JSONObject donationToJSON(Donation donation, File baseFile, String baseData){
        JSONObject json = new JSONObject();
        json.put("username", donation.getUsername());
        json.put("amount", getAmount(donation.getUsername(), baseFile, baseData) + donation.getAmount());

        return json;
    }
    private int getAmount(String username, File baseFile, String baseData){
        List<DonationImpl> list = parseBase(baseFile, baseData);
        for (DonationImpl donation : list) if(username.equalsIgnoreCase(donation.getUsername())) return donation.getAmount();
        return 0;
    }
    private int getID(String username, File baseFile, String baseDate){
        List<DonationImpl> list = parseBase(baseFile, baseDate);
        for (int i = 0; i < list.size();++i) if(username.equalsIgnoreCase(list.get(i).getUsername())) return i;
        return list.size();
    }
    private boolean isExist(String username, File baseFile, String baseData){
        List<DonationImpl> list = parseBase(baseFile, baseData);
        for (DonationImpl donation : list) if(username.equalsIgnoreCase(donation.getUsername())) return true;
        return false;
    }
    private List<DonationImpl> parseBase(File baseFile, String baseData){
        List<DonationImpl> list = new ArrayList<>();
        if(baseFile.exists()){
            JSONObject base = toJSON(baseData);
            for(int i = 0; i < base.size();++i){
                JSONObject user = (JSONObject) base.get(String.valueOf(i));

                DonationImpl donation = new DonationImpl();
                donation.setUsername(user.get("username").toString());
                donation.setAmount(Integer.parseInt(user.get("amount").toString()));

                list.add(donation);
            }
        } else Path.PATH_TO_BASE.toFile().mkdirs();
        return list;
    }
    private JSONObject toJSON(String text) {
        try { return (JSONObject) new JSONParser().parse(text); } catch (ParseException e) { logger.error(e); }
        return null;
    }
    private String baseToString(File base){
        if (base.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(base))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                br.close();
                return sb.toString();
            } catch (IOException e) { logger.error(e); }
        } else return "{}";
        return "{}";
    }
    private void dumpBase(File baseFile, JSONObject base){
        try {
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(baseFile)));

            out.write(base.toJSONString());
            out.close();
        }catch (IOException e) { logger.error(e); }
    }
}

package util;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import Model.payment;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Calendar;
import java.time.Instant;
import java.text.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime ;
import java.time.ZoneId;
import java.util.List;


public class TransactionsCollector {



/**
 public static void main(String args[]){
 //Date date = new Date();
 //Timestamp ts=new Timestamp(date.getTime());
 //System.out.println(ts.date);

 Calendar c = Calendar.getInstance();
 int curr_year = c.get(Calendar.YEAR);
 int curr_month = c.get(Calendar.MONTH)+1; // month starts from 0 , so +1


 // Today's transactions
 getTransactions(currentTS());

 // Yesterday's transactions
 getTransactions(currentTS() - 24*60*60);

 // particular day's transactions
 getTransactions(getTS(curr_year, curr_month, 1));


 */




    // transactions in given period of Time
    public static void getTransactions(long from, long to, int count , int skip){
         String KEY_ID = "rzp_test_sNL7UTCVCOAZtW";
         String KEY_SECRET = "LYznqeSQXwy140aNYEdelK4D";
        try{

            RazorpayClient razorpay = new RazorpayClient(KEY_ID,KEY_SECRET);
            try {
                JSONObject paymentRequest = new JSONObject();

                //supported option filters (from, to, count, skip)
                paymentRequest.put("from", from);
                paymentRequest.put("to", to);
                paymentRequest.put("count", count);
                paymentRequest.put("skip", skip);

                List<Payment> payments = razorpay.Payments.fetchAll(paymentRequest);
            } catch (RazorpayException e) {
                // Handle Exception
                System.out.println(e.getMessage());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    // Default parameters for getTransactions()
    public static void getTransactions(long from, long to){
        getTransactions(from, to, 100, 0); }
    // Default parameters for getTransactions()
    public static void getTransactions(long from){
        getTransactions(from, from + 24*60*60 , 100, 0);
    }


    // To get timestamp( in seconds) for a given date
    public static long getTS(int year, int month, int day){
        LocalDateTime ldt = LocalDateTime.of(year, month, day, 0, 0, 0);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("Asia/Kolkata"));
        long epoch_sec = zdt.toInstant().toEpochMilli()/1000;
        return epoch_sec;

    }

    // To get timestamp( in seconds) for a current Time
    public static long currentTS(){
        Instant instant = Instant.now();
        long timeStamp = instant.toEpochMilli()/1000;
        return timeStamp;
    }




}


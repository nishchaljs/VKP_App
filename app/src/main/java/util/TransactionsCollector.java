package util;

import android.util.Log;

import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;
import Model.payment;
import Model.paymentList;
import android.os.AsyncTask;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.time.Instant;
import java.text.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime ;
import java.time.ZoneId;
import java.util.List;


public class TransactionsCollector {
    private List<payment> paymentLists = new ArrayList<>();

    private String KEY_ID = "rzp_test_sNL7UTCVCOAZtW";
    private String KEY_SECRET = "LYznqeSQXwy140aNYEdelK4D";
    long from;
    long to;
    int skip = 0;
    int count = 100;
    int days = 1;



    // Constructors

    public TransactionsCollector() {
        this.from = currentTS();
        this.to = this.from + 24*60*60;
        this.skip = 0;
        this.count = 100;

        getTransactions(this.from,this.to,this.count,this.skip);
    }

    public TransactionsCollector(int days) {
        this.to = currentTS();
        this.days = days;
        this.from = this.to - 24*60*60*days;
        this.skip = 0;
        this.count = 100;



        getTransactions(this.from,this.to,this.count,this.skip);
      //  getTransactions();
    }

    public TransactionsCollector(long from, long to, int skip, int count) {
        this.from = from;
        this.to = to;
        this.skip = skip;
        this.count = count;

    }

    public TransactionsCollector(long from, long to) {
        this.from = from;
        this.to = to;
        this.skip = 0;
        this.count = 100;
        getTransactions(this.from,this.to,this.count,this.skip);
    }

    public TransactionsCollector(long from) {
        this.from = from;
        this.to = from + 24*60*60 ;
        this.skip = 0;
        this.count = 100;
    }

    public List<payment> getPaymentLists() {
        return paymentLists;
    }
















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
    public void getTransactions(long from, long to, int count , int skip){

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

                Log.d(this.toString(),"----------------------------------");
                Log.d(this.toString(),"Length of data recieved : "+ payments.size());


                for (int i = 0; i < payments.size(); i++) {
                    //System.out.println(paymentList.get(i));
                    Payment Payment_index = payments.get(i);
                    Log.d(this.toString(),"----------------------------------");
                    Log.d(this.toString(),"JSON DUMP -"+i+ Payment_index.toJson().toString());

                    payment payment_index = convertJson(Payment_index.toJson());
                    Log.d(this.toString(),"----------------------------------");
                    Log.d(this.toString(),"Transaction ID :" + payment_index.getId());
                    paymentLists.add(payment_index);


                }




            } catch (RazorpayException e) {
                // Handle Exception
                System.out.println(e.getMessage());
                Log.e(this.toString(),"MESSAGE ERROR :"+e.getMessage());
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            Log.e(this.toString(),"MESSAGE ERROR :"+e.getMessage());
        }

    }

    public void getTransactions(){
        String KEY_ID = "rzp_test_sNL7UTCVCOAZtW";
        String KEY_SECRET = "LYznqeSQXwy140aNYEdelK4D";

        RazorpayClient razorpay = null;
        try {
            razorpay = new RazorpayClient(KEY_ID,KEY_SECRET);
        } catch (RazorpayException e) {
            e.printStackTrace();
            Log.e(this.toString(),"MESSAGE ERROR :"+e.getMessage());
        }
        try {
            JSONObject paymentRequest = new JSONObject();
            try{
                //supported option filters (from, to, count, skip)
                paymentRequest.put("count", 2);
                paymentRequest.put("skip", 1);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                Log.e(this.toString(),"MESSAGE ERROR :"+e.getMessage());
            }

            List<Payment> payments = razorpay.Payments.fetchAll(paymentRequest);

            Log.d(this.toString(),"Length of data received : "+ payments.size());


        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
            Log.e(this.toString(),"MESSAGE ERROR :"+e.getMessage());
        }


    }




    // To get timestamp( in seconds) for a given date
    public static long getTS(int year, int month, int day){
        String date = "2 8 2001";
        long milli = 0;
        SimpleDateFormat  sdf = new SimpleDateFormat("dd MM yyyy");
        try {
            Date mdate = sdf.parse(date);
            milli = mdate.getTime();
            System.out.println(milli);

        } catch(Exception e) {
            Log.d("MESSAGE","Failed to convert timestamp : "+ e.getMessage());
        }
        return milli;
    }

    // To get timestamp( in seconds) for a current Time
    public static long currentTS(){

        //Instant instant = Instant.now();
        //long timeStamp = instant.toEpochMilli()/1000;
        long timeStamp = System.currentTimeMillis()/1000;
        return timeStamp;
    }

    public static payment convertJson(JSONObject obj) {

        payment P = new payment();

        try {

            P.setId((String) obj.get("id"));
            //P.setEntity((String) obj.get("entity"));
            P.setAmount((int) obj.get("amount"));
//            P.setCurrency((String) obj.get("currency"));
//            P.setStatus((String) obj.get("status"));
//            P.setOrder_id((String) obj.get("order_id"));
//            P.setInvoice_id((String) obj.get("invoice_id"));
//            P.setMethod((String) obj.get("method"));
//            P.setAmount_refunded( (Float)  obj.get("amount_refunded"));
//            P.setRefund_status((String) obj.get("refund_status"));
            P.setCaptured(obj.getBoolean("captured"));
//            P.setDescription((String) obj.get("description"));
//            P.setCard_id((String) obj.get("card_id"));
//            P.setBank((String) obj.get("bank"));
//            P.setWallet((String) obj.get("wallet"));
//            P.setVpa((String) obj.get("vpa"));
//            P.setEmail((String) obj.get("email"));
//            P.setContact((String) obj.get("contact"));
//            P.setNotes(obj.getJSONObject("notes"));
//            P.setFee((String) obj.get("fee"));
//            P.setTax((String) obj.get("tax"));
//            P.setError_code((String) obj.get("error_code"));
//            P.setError_description((String) obj.get("error_description"));
            P.setCreated_at( (int) obj.get("created_at"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return P;

    }





}



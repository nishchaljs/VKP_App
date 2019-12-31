package tecmanic.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

public class payment_gateway extends Activity implements  PaymentResultListener {

    String deviceUID ;
    long deviceID  ;
    float devicePrice ;
    long deviceStatus ;
    long devicePending  ;
    String deviceGame  , GameType;
    int orderQuantity;
    Order order;
    RazorpayClient razorpayClient;
//    private TextView textView;

    private DatabaseReference myRef;

    private static final String TAG = payment_gateway.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_paytm);

        try {
            razorpayClient = new RazorpayClient("rzp_test_sNL7UTCVCOAZtW", "LYznqeSQXwy140aNYEdelK4D");
        }
        catch (Exception e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
          deviceUID = getIntent().getStringExtra("deviceUID");
          deviceID = getIntent().getLongExtra("deviceID",0);
          devicePrice = getIntent().getFloatExtra("devicePrice",0);
          deviceStatus = getIntent().getLongExtra("deviceStatus",0);
          devicePending = getIntent().getLongExtra("devicePending",0);
          deviceGame = getIntent().getStringExtra("deviceGame");
          orderQuantity = getIntent().getIntExtra("orderQuantity",1);
          GameType = getIntent().getStringExtra("deviceGameType");

        ImageView image = (ImageView) findViewById(R.id.gameImage);

        if (deviceStatus == 0){
            RelativeLayout r = findViewById(R.id.btnRegister);
            r.setVisibility(View.GONE);

        }







        if(GameType.equals("supermario")){
            image.setImageResource(R.drawable.super_mario);
        }
        else if(GameType.equals("contra")){
            image.setImageResource(R.drawable.contra);
        }
        else if(GameType.equals("tekken")){
            image.setImageResource(R.drawable.tekken);
        }
        else if(GameType.equals("duckhunt")){
            image.setImageResource(R.drawable.duckhunt);
        }
        else if(GameType.equals("chipndale")){
            image.setImageResource(R.drawable.chipndale);
        }
        else{
            image.setImageResource(R.drawable.default_game_icon);
        }



        TextView GameName= (TextView) (TextView)findViewById(R.id.GameName);
        GameName.setText(String.valueOf(deviceGame));

        TextView textViewgamePrice = (TextView) (TextView)findViewById(R.id.unitGamePrice);
        textViewgamePrice.setText(String.valueOf(devicePrice));

        TextView textViewMachineID = (TextView) (TextView)findViewById(R.id.machine_unique_id);
        textViewMachineID.setText(String.valueOf(deviceUID));

        TextView textViewamount = (TextView) (TextView)findViewById(R.id.edt_amount);
        textViewamount.setText(String.valueOf(orderQuantity*devicePrice));


        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    JSONObject orderRequest = new JSONObject();
                    orderRequest.put("amount", orderQuantity*devicePrice*100); // amount in the smallest currency unit
                    orderRequest.put("currency", "INR");
                    orderRequest.put("receipt", "order_rcptid_11");
                    orderRequest.put("payment_capture", 1);

                    JSONObject notes = new JSONObject();
                    notes.put("deviceUID",deviceUID );
                    notes.put("deviceID",deviceID);
                    notes.put("game",deviceGame);
                    notes.put("quantity",orderQuantity);
                    orderRequest.put("notes", notes );

                    order = razorpayClient.Orders.create(orderRequest);

                    TextView name = (TextView) (TextView)findViewById(R.id.machine_id);
                    name.setText((String)order.get("id"));

//
                }
                catch (Exception e) {
                    // Handle Exception
                    System.out.println(e.getMessage());
                }
            }
        });

        thread.start();


        Checkout.preload(getApplicationContext());

        Button button = (Button) findViewById(R.id.start_transaction);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();



            }
        });



    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout checkout = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "VKP");
            options.put("description", "Testing payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("order_id", order.get("id"));

            /**
             * Amount is always passed in currency subunits (Paisa)
             * Eg: "100" = INR 1.00
             */
            options.put("amount", String.valueOf(orderQuantity*devicePrice*100) );

//            JSONObject preFill = new JSONObject();
//            preFill.put("email", "test@razorpay.com");
//            preFill.put("contact", "9876543210");

//            options.put("prefill", preFill);

            JSONObject notes = new JSONObject();
            notes.put("deviceUID",deviceUID );
            notes.put("deviceID",deviceID);
            notes.put("game",deviceGame);
            notes.put("quantity",orderQuantity);

            options.put("notes", notes );


            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            String deviceID = deviceUID;
            myRef = FirebaseDatabase.getInstance().getReference("devices");
            myRef.child(deviceID).child("pending").setValue(5);
//            myRef.child(deviceID).child("pending").setValue(5);




            Toast.makeText(this, "yess!!, Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Intent enjoyGame = new Intent(payment_gateway.this, ThanksOrder.class);
            startActivity(enjoyGame);
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Ohh !! Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }

}




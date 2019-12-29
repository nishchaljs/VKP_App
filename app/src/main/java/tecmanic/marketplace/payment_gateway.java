package tecmanic.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class payment_gateway extends Activity implements  PaymentResultListener {

    String deviceUID ;
    long deviceID  ;
    float devicePrice ;
    long deviceStatus ;
    long devicePending  ;
    String deviceGame  ;



    private DatabaseReference myRef;

    private static final String TAG = payment_gateway.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);

          deviceUID = getIntent().getStringExtra("deviceUID");
          deviceID = getIntent().getLongExtra("deviceID",0);
          devicePrice = getIntent().getFloatExtra("devicePrice",0);
          deviceStatus = getIntent().getLongExtra("deviceStatus",0);
          devicePending = getIntent().getLongExtra("devicePending",0);
          deviceGame = getIntent().getStringExtra("deviceGame");

        TextView name = (TextView) (TextView)findViewById(R.id.machine_id);
        name.setText(deviceGame);

        TextView textViewgamePrice = (TextView) (TextView)findViewById(R.id.unitGamePrice);
        textViewgamePrice.setText(String.valueOf(devicePrice));

        TextView textViewMachineID = (TextView) (TextView)findViewById(R.id.machine_unique_id);
        textViewMachineID.setText(String.valueOf(deviceID));




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
            options.put("name", "Razorpay Corp");
            options.put("description", "Testing payment");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            /**
             * Amount is always passed in currency subunits (Paisa)
             * Eg: "100" = INR 1.00
             */
            options.put("amount", String.valueOf(devicePrice*100) );

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

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
            Intent enjoyGame = new Intent(payment_gateway.this, EnjoyGame.class);
            //startActivity(enjoyGame);
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




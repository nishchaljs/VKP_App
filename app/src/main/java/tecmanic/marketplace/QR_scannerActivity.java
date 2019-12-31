package tecmanic.marketplace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class QR_scannerActivity extends AppCompatActivity implements PaymentResultListener {
    private static final String TAG = payment_gateway.class.getSimpleName();
    TextView result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        result = (TextView) findViewById(R.id.edt_amount);
        TextView mid=(TextView)findViewById(R.id.machine_id);
        final Barcode barcode = getIntent().getParcelableExtra("barcode");
        try {
            JSONObject obj = new JSONObject(String.valueOf(barcode.displayValue));
            if(obj.has("machine_id")){mid.setText(obj.getString("machine_id")); }
            if(obj.has("amount")){result.setText(obj.getString("amount")); }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button button = (Button) findViewById(R.id.start_transaction);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startPayment();



            }
        });
//        YourResult = (TextView) findViewById(R.id.TextView_YourResult);
//        final Barcode barcode = getIntent().getParcelableExtra("barcode");
//        result.setText(barcode.displayValue);

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
            options.put("amount", "100");

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
            Toast.makeText(this, "yess!!, Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Intent enjoyGame = new Intent(QR_scannerActivity.this, EnjoyGame.class);
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


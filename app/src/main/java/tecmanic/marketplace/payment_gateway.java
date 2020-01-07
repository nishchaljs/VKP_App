package tecmanic.marketplace;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentResultListener;
import com.razorpay.RazorpayClient;

import org.json.JSONObject;

public class payment_gateway extends Activity implements  PaymentResultListener {

    String deviceUID ;
    String deviceID  ;
    float devicePrice ;
    long deviceStatus ;
    long devicePending  ;
    String deviceGame  , GameType, gameDuration, deviceMessage;
    int orderQuantity;

    Order order;
    RazorpayClient razorpayClient;
    ProgressDialog progressDialog;
//    private TextView textView;

    private DatabaseReference myRef;

    private static final String TAG = payment_gateway.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myRef = FirebaseDatabase.getInstance().getReference("devices");
        setContentView(R.layout.activity_paytm);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult result) {
                boolean isAdmin ;

                try{
                    isAdmin =(boolean) result.getClaims().get("admin");
                }
                catch (Exception e){
                    isAdmin = false;
                }

                if (isAdmin) {
                    // Show admin UI.

                    Button delete_machine = (Button) findViewById(R.id.delete_machine);
                    Button edit_machine = (Button) findViewById(R.id.edit_machine);
                    Button add_machine = (Button) findViewById(R.id.add_machine);
                    delete_machine.setVisibility(View.VISIBLE);
                    add_machine.setVisibility(View.VISIBLE);
                    edit_machine.setVisibility(View.VISIBLE);




                } else {
                    // Show regular user UI.
                    Button delete_machine = (Button) findViewById(R.id.delete_machine);
                    Button edit_machine = (Button) findViewById(R.id.edit_machine);
                    Button add_machine = (Button) findViewById(R.id.add_machine);
                    delete_machine.setVisibility(View.GONE);
                    add_machine.setVisibility(View.GONE);
                    edit_machine.setVisibility(View.GONE);

                }
            }
        });

        Button delete_machine = (Button) findViewById(R.id.delete_machine);
        Button edit_machine = (Button) findViewById(R.id.edit_machine);
        Button add_machine = (Button) findViewById(R.id.add_machine);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });



        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        delete_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Do you want to Delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(payment_gateway.this, MainActivity.class);
//                i.putExtra("action",'delete');
                                finish();
                                myRef.child(deviceUID).removeValue();
                                startActivity(i);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert =builder.create();
                alert.show();

            }
        });

        edit_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(payment_gateway.this, edit_device.class);
                i.putExtra("action","update_device");

                i.putExtra("uid", deviceUID);

                i.putExtra("device_id_string", String.valueOf(deviceID));
                i.putExtra("game_name_string", deviceGame);
                i.putExtra("game_type_string",GameType);
                i.putExtra("game_price_string",String.valueOf(devicePrice));
                i.putExtra("game_duration_string", gameDuration);
                i.putExtra("game_status_string",String.valueOf(deviceStatus));
                i.putExtra("game_message_string", deviceMessage);




                startActivity(i);

            }
        });

        add_machine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(payment_gateway.this, edit_device.class);
                i.putExtra("action","new_device");

                startActivity(i);

            }
        });





        try {
            razorpayClient = new RazorpayClient("rzp_test_sNL7UTCVCOAZtW", "LYznqeSQXwy140aNYEdelK4D");
        }
        catch (Exception e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
        deviceUID = getIntent().getStringExtra("deviceUID");
        deviceID = getIntent().getStringExtra("deviceID");
        devicePrice = getIntent().getFloatExtra("devicePrice",0);
        deviceMessage = getIntent().getStringExtra("deviceMessage");
        gameDuration = getIntent().getStringExtra("gameDuration");

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

        TextView textViewMachineID = (TextView) (TextView)findViewById(R.id.machine_id);
        textViewMachineID.setText(deviceID);

        TextView textViewamount = (TextView) (TextView)findViewById(R.id.edt_amount);
        textViewamount.setText(String.valueOf(orderQuantity*devicePrice));

        TextView display_item_count = (TextView) (TextView)findViewById(R.id.display_item_count);




        Button increaseQuantity = (Button) findViewById(R.id.increase_item_count);
        Button decreaseQuantity = (Button) findViewById(R.id.decrease_item_count);

        increaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderQuantity < 10){
                    orderQuantity += 1;
                    TextView textViewamount = (TextView) (TextView)findViewById(R.id.edt_amount);
                    textViewamount.setText(String.valueOf(orderQuantity*devicePrice));

                    TextView display_item_count = (TextView) (TextView)findViewById(R.id.display_item_count);
                    display_item_count.setText(String.valueOf(orderQuantity));
                }
            }
        });

        decreaseQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderQuantity > 1){
                    orderQuantity -= 1;
                    TextView textViewamount = (TextView) (TextView)findViewById(R.id.edt_amount);
                    textViewamount.setText(String.valueOf(orderQuantity*devicePrice));

                    TextView display_item_count = (TextView) (TextView)findViewById(R.id.display_item_count);
                    display_item_count.setText(String.valueOf(orderQuantity));
                }
            }
        });



        Checkout.preload(getApplicationContext());

        Button button = (Button) findViewById(R.id.start_transaction);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrderTask myAsyncTasks = new CreateOrderTask();
                myAsyncTasks.execute();





            }
        });



    }
    public class CreateOrderTask extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(payment_gateway.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", orderQuantity*devicePrice*100); // amount in the smallest currency unit
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "order_rcpt_id_11");
                orderRequest.put("payment_capture", 1);

                JSONObject notes = new JSONObject();
                notes.put("deviceUID",deviceUID );
                notes.put("deviceID",deviceID);
                notes.put("game",deviceGame);
                notes.put("quantity",orderQuantity);
                orderRequest.put("notes", notes );

                order = razorpayClient.Orders.create(orderRequest);

            }
            catch (Exception e) {
                // Handle Exception
                Log.d(TAG, "PAYMENT GATEWAY: Error Creating Order : "+e.getMessage());
            }

            return "";
        }

        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"Order ID : "+order.get("id") ,Toast.LENGTH_SHORT ).show();
            startPayment();


        }

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

            TextView name = (TextView) (TextView)findViewById(R.id.order_id);
            name.setText((String)order.get("id"));
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
            myRef.child(deviceID).child("pending").setValue(orderQuantity);
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

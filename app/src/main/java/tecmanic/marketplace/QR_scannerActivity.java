package tecmanic.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class QR_scannerActivity extends AppCompatActivity{
    private static final String TAG = payment_gateway.class.getSimpleName();
    TextView result;

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("devices");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final Barcode barcode = getIntent().getParcelableExtra("barcode");
        try {
            JSONObject obj = new JSONObject(String.valueOf(barcode.displayValue));
            if(obj.has("uid")){
                final String uid = obj.getString("uid");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            try{

                                DataSnapshot data = dataSnapshot.child(uid);
//                                String UID =  data.getKey();
                                String ID = (String) data.child("ID").getValue();
                                String game = (String) data.child("Game").getValue();
                                String message = (String) data.child("message").getValue();
                                long pending = (long) data.child("pending").getValue();
                                long priceLong = (long) data.child("price").getValue();

                                Float price = Float.valueOf(priceLong);


                                String duration = (String) data.child("duration").getValue();

                                long status = (long) data.child("status").getValue();

                                String gameType = (String) data.child("type").getValue();


                                Intent intent = new Intent(QR_scannerActivity.this, payment_gateway.class);

                                intent.putExtra("deviceUID",uid);
                                intent.putExtra("deviceID",ID);
                                intent.putExtra("devicePrice",price);
                                intent.putExtra("deviceStatus",status);
                                intent.putExtra("devicePending",pending);
                                intent.putExtra("deviceGame",game);
                                intent.putExtra("deviceGameType",gameType);
                                intent.putExtra("deviceMessage",message);
                                intent.putExtra("gameDuration",duration);
                                intent.putExtra("orderQuantity",1);

                                finish();


                                startActivity(intent);

                            }
                            catch (Exception e){
                                Intent intent = new Intent(QR_scannerActivity.this, MainActivity.class);
                                Toast.makeText(getApplicationContext(),"Can't load machine : " +uid ,Toast.LENGTH_SHORT ).show();
                                finish();
                                startActivity(intent);
                            }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    }
                });




            }
            else{

                Intent intent = new Intent(QR_scannerActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(),"Invalid parametered QR Code",Toast.LENGTH_SHORT ).show();
                finish();
                startActivity(intent);
            }
//            if(obj.has("amount")){result.setText(obj.getString("amount")); }
        } catch (JSONException e) {
            e.printStackTrace();

            Intent intent = new Intent(QR_scannerActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(),"Invalid QR Code",Toast.LENGTH_SHORT ).show();
            finish();
            startActivity(intent);

            }
        }


//        YourResult = (TextView) findViewById(R.id.TextView_YourResult);
//        final Barcode barcode = getIntent().getParcelableExtra("barcode");
//        result.setText(barcode.displayValue);

//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        new AlertDialog.Builder(this)
//                .setTitle("Really Exit?")
//                .setMessage("Are you sure you want to exit?")
//                .setNegativeButton(android.R.string.no,null)
//                .setNegativeButton(android.R.string.yes,new DialogInterface.OnClickListener(){
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        finishAndRemoveTask();
//                    }
//                }).create().show();
//
//    }

}

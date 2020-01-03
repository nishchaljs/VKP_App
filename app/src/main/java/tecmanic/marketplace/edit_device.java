package tecmanic.marketplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_device extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_device);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        final DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference("devices");


        final String action =  getIntent().getStringExtra("action");

        if (action.equals("new_device")){


        }
        else {
            String uid = getIntent().getStringExtra("uid");

            // Update Device
            String device_id_string =  getIntent().getStringExtra("device_id_string");
            String game_name_string =  getIntent().getStringExtra("game_name_string");
            String game_type_string =  getIntent().getStringExtra("game_type_string");
            String game_price_string =  getIntent().getStringExtra("game_price_string");
            String game_duration_string =  getIntent().getStringExtra("game_duration_string");
            String game_status_string =  getIntent().getStringExtra("game_status_string");
            String game_message_string =  getIntent().getStringExtra("game_message_string");

            EditText E1, E2, E3, E4, E5, E7;
            Switch E6;

            E1 = (EditText) findViewById(R.id.device_id_view);
            E2 = (EditText) findViewById(R.id.device_name_view);
            E3 = (EditText) findViewById(R.id.device_type_view);
            E4 = (EditText) findViewById(R.id.device_price_view);
            E5 = (EditText) findViewById(R.id.device_duration_view);
            E6 = (Switch) findViewById(R.id.device_status_view);
            E7 = (EditText) findViewById(R.id.device_message_view);

            E1.setText(device_id_string);
            E2.setText(game_name_string);
            E3.setText(game_type_string);
            E4.setText(game_price_string);
            E5.setText(game_duration_string);

            if(game_status_string.equals("1")){
                E6.setChecked(true);
            }
            else{
                E6.setChecked(false);
            }


            E7.setText(game_message_string);



        }

        Button Save = (Button) findViewById(R.id.save_device_data);

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String device_id_string, game_name_string, game_type_string, game_price_string, game_duration_string, game_status_string, game_message_string;
                EditText E1, E2, E3, E4, E5,  E7;
                Boolean game_status;
                Switch E6;

                Integer game_statusInt = 0 ;
                Float gamePrice = Float.intBitsToFloat(0);


                E1 = (EditText) findViewById(R.id.device_id_view);
                E2 = (EditText) findViewById(R.id.device_name_view);
                E3 = (EditText) findViewById(R.id.device_type_view);
                E4 = (EditText) findViewById(R.id.device_price_view);
                E5 = (EditText) findViewById(R.id.device_duration_view);
                E6 = (Switch) findViewById(R.id.device_status_view);
                E7 = (EditText) findViewById(R.id.device_message_view);

                device_id_string = E1.getText().toString();
                game_name_string = E2.getText().toString();
                game_type_string = E3.getText().toString();
                game_price_string = E4.getText().toString();
                game_duration_string = E5.getText().toString();
                game_status = E6.isChecked();

                game_message_string = E7.getText().toString();


                try{
                    gamePrice = Float.parseFloat(game_price_string);

                    if (game_status == true){
                        game_statusInt = 1;
                    }
                    else{
                        game_statusInt = 0;
                    }
                }
                catch (Exception e){
                    TextView error_msg = (TextView) findViewById(R.id.Error_message);
                    error_msg.setText("Error: " + e.getMessage());
                    return;

                }

                String UID;

                if (action.equals("new_device")){
                    UID = myRef.push().getKey();
                }
                else{
                    UID = getIntent().getStringExtra("uid");
                }


                myRef.child(UID).child("ID").setValue(device_id_string);
                myRef.child(UID).child("Game").setValue(game_name_string);
                myRef.child(UID).child("type").setValue(game_type_string);
                myRef.child(UID).child("price").setValue(gamePrice);
                myRef.child(UID).child("duration").setValue(game_duration_string);
                myRef.child(UID).child("status").setValue(game_statusInt);
                myRef.child(UID).child("message").setValue(game_message_string);
                myRef.child(UID).child("pending").setValue(0);


                Intent i = new Intent(edit_device.this, MainActivity.class);
                startActivity(i);


            }
        });




    }
}

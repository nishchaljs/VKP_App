package tecmanic.marketplace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

public class QR_scannerActivity extends AppCompatActivity {

    EditText result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        result = (EditText) findViewById(R.id.edt_amount);
        TextView mid=(TextView)findViewById(R.id.machine_id);
        final Barcode barcode = getIntent().getParcelableExtra("barcode");
        try {
            JSONObject obj = new JSONObject(String.valueOf(barcode.displayValue));
            if(obj.has("name")){mid.setText(obj.getString("name")); }
            if(obj.has("amount")){result.setText(obj.getString("amount")); }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // result.setText(barcode.displayValue);

//        YourResult = (TextView) findViewById(R.id.TextView_YourResult);
//        final Barcode barcode = getIntent().getParcelableExtra("barcode");
//        result.setText(barcode.displayValue);

    }
}


package tecmanic.marketplace;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.vision.barcode.Barcode;

public class QR_scannerActivity extends AppCompatActivity {

    EditText result;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paytm);
        result = (EditText) findViewById(R.id.edt_amount);
        final Barcode barcode = getIntent().getParcelableExtra("barcode");
        result.setText(barcode.displayValue);

//        YourResult = (TextView) findViewById(R.id.TextView_YourResult);
//        final Barcode barcode = getIntent().getParcelableExtra("barcode");
//        result.setText(barcode.displayValue);

    }
}


package tecmanic.marketplace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//public class EnjoyGame extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enjoy_game);
//    }
//}


public class EnjoyGame extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enjoy_game);

        Toast.makeText(this, "ENJOY GAME ", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG ", "IN ENJOY GAME");

    }





    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
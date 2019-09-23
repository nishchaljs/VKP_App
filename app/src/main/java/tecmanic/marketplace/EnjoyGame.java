package tecmanic.marketplace;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import Adapter.payment_adapter;
import util.IotCoreCommunicator;
import util.TransactionsCollector;

//public class EnjoyGame extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enjoy_game);
//    }
//}


public class EnjoyGame extends AppCompatActivity {


    private IotCoreCommunicator communicator;
    private Handler handler;

    private class SendData extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute(){
            // To be executed before doInBackground
        }

        @Override
        protected Integer doInBackground(Void... params) {

            String subtopic = "iot-topic";
            // Your message you want to send
            String message = "Send data ";
            communicator.publishMessage(subtopic, message);

            return 0;

        }

        protected void onProgressUpdate(Void... update) {


        }

        protected void onPostExecute(Integer result) {


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enjoy_game);





        Toast.makeText(this, "ENJOY GAME ", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG ", "IN ENJOY GAME");

        // Setup the communication with your Google IoT Core details
        communicator = new IotCoreCommunicator.Builder()
                .withContext(this)
                .withCloudRegion("asia-east1") // ex: europe-west1
                .withProjectId("red-league-253518")   // ex: supercoolproject23236
                .withRegistryId("iot-registry") // ex: my-devices
                .withDeviceId("esp8266_8394AC") // ex: my-test-raspberry-pi
                .withPrivateKeyRawFileId(R.raw.private_key)
                .build();
        new SendData().execute();
        HandlerThread thread = new HandlerThread("MyBackgroundThread");
        thread.start();
        handler = new Handler(thread.getLooper());
        handler.post(connectOffTheMainThread); // Use whatever threading mechanism you want

    }

    private final Runnable connectOffTheMainThread = new Runnable() {
        @Override
        public void run() {
            communicator.connect();

            handler.post(sendMqttMessage);
        }
    };

    private final Runnable sendMqttMessage = new Runnable() {
        private int i;

        /**
         * We post 100 messages as an example, 1 a second
         */
        @Override
        public void run() {
            if (i == 10) {
                return;
            }

            // events is the default topic for MQTT communication
            String subtopic = "iot-topic";
            // Your message you want to send
            String message = "Hello World " + i++;
            communicator.publishMessage(subtopic, message);
            Log.d("DEBUG ", "Sending Message "+i);

            handler.postDelayed(this, TimeUnit.SECONDS.toMillis(1));
        }
    };

    @Override
    protected void onDestroy() {
        communicator.disconnect();
        super.onDestroy();
    }
}
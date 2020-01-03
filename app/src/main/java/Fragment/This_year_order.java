package Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import Adapter.payment_adapter;
import Config.BaseURL;
import Model.payment;
import tecmanic.marketplace.MainActivity;
import tecmanic.marketplace.R;
import util.ConnectivityReceiver;
import util.RecyclerTouchListener;
import util.Session_management;
import util.TransactionsCollector;


public class This_year_order extends Fragment {

    //  private static String TAG = My_Past_Order.class.getSimpleName();
    private int no_of_days;

    private RecyclerView rv_myorder;

    //remove final for parsing
    List<payment> item=new ArrayList<>();
    payment_adapter itemadapter=new payment_adapter(item);

    TabHost tHost;

    public This_year_order() {
        // Required empty public constructor
    }


    public void set_No_of_days(int no_of_days) {
        this.no_of_days = no_of_days;
    }

    private class FetchData extends AsyncTask<Void, Void, Integer> {

        protected void onPreExecute(){
            // To be executed before doInBackground
        }

        @Override
        protected Integer doInBackground(Void... params) {

            TransactionsCollector collector = new TransactionsCollector(no_of_days);
            item = collector.getPaymentLists();
            Log.d(this.toString(),"length of data collected" + item.size() );


            return 0;

        }

        protected void onProgressUpdate(Void... update) {


        }

        protected void onPostExecute(Integer result) {
            Log.d(this.toString(),"total transaction received " + item.size());
            Log.d(this.toString(),"Notifying data change to adapter");
            payment_adapter itemadapter=new payment_adapter(item);
            rv_myorder.setAdapter(itemadapter);
            itemadapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_past_order, container, false);

        // ((My_Order_activity) getActivity()).setTitle(getResources().getString(R.string.my_order));
// temp adjustment
        rv_myorder=(RecyclerView) view.findViewById(R.id.rv_myorder);


        //Payment Payment = new Payment("#orderid-0001",100,"upi",1400826750 );
        //String order_id, float amount, String method, float created_at




        rv_myorder.setAdapter(itemadapter);

        new FetchData().execute();

        // TODO : Dummy data here
        payment dummy_pay = new payment("#orderid-0001",100,"upi",1400826750 );
        dummy_pay.setStatus("created");
        item.add(dummy_pay);
        Log.d(this.toString(),"transaction with dummy data" + item.size());

        itemadapter.notifyDataSetChanged();

        // handle the touch event if true
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // check user can press back button or not
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

//                    Fragment fm = new Home_fragment();
//                    android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.contentPanel, fm)
//                            .addToBackStack(null).commit();
                    return true;
                }
                return false;
            }
        });

        rv_myorder = (RecyclerView) view.findViewById(R.id.rv_myorder);
        rv_myorder.setLayoutManager(new LinearLayoutManager(getActivity()));

        Session_management sessionManagement = new Session_management(getActivity());
        String user_id = sessionManagement.getUserDetails().get(BaseURL.KEY_ID);

        // check internet connection
        if (ConnectivityReceiver.isConnected())

        {
            //makeGetOrderRequest(user_id);
        } else

        {
            ((MainActivity) getActivity()).onNetworkConnectionChanged(false);
        }

        // recyclerview item click listener
        rv_myorder.addOnItemTouchListener(new

                RecyclerTouchListener(getActivity(), rv_myorder, new RecyclerTouchListener.OnItemClickListener()

        {
            @Override
            public void onItemClick(View view, int position) {
//                String sale_id = my_paymentList.get(position).getSale_id();
//                String date = my_paymentList.get(position).getOn_date();
//                String time = my_paymentList.get(position).getDelivery_time_from() + "-" + my_paymentList.get(position).getDelivery_time_to();
//                String total = my_paymentList.get(position).getTotal_amount();
//                String status = my_paymentList.get(position).getStatus();
//                String deli_charge = my_paymentList.get(position).getDelivery_charge();
//                 Intent intent=new Intent(getContext(), MyOrderDetail.class);
//                intent.putExtra("sale_id", sale_id);
//                intent.putExtra("date", date);
//                intent.putExtra("time", time);
//                intent.putExtra("total", total);
//                intent.putExtra("status", status);
//                intent.putExtra("deli_charge", deli_charge);
                //                startActivity(intent);

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }


    public void retrieveData(){

    }
}
package Fragment;

import util.TransactionsCollector;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Payment;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import Adapter.My_Past_Order_adapter;
import Adapter.paymentAdapter;
import Config.BaseURL;
import Model.My_Past_order_model;
import Model.payment;
import tecmanic.marketplace.AppController;
import tecmanic.marketplace.MainActivity;
import tecmanic.marketplace.MyOrderDetail;
import tecmanic.marketplace.R;
import util.ConnectivityReceiver;
import util.CustomVolleyJsonArrayRequest;
import util.RecyclerTouchListener;
import util.Session_management;


public class This_year_order extends Fragment {

    //  private static String TAG = My_Past_Order.class.getSimpleName();

    private RecyclerView rv_myorder;

    //remove final for parsing
     ArrayList<Payment> my_paymentList = new ArrayList<Payment>();

    TabHost tHost;

    public This_year_order() {
        // Required empty public constructor
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
        List<payment> item=new ArrayList<>();

        //Payment Payment = new Payment("#orderid-0001",100,"upi",1400826750 );
        //String order_id, float amount, String method, float created_at



        paymentAdapter itemadapter=new paymentAdapter(item);
        rv_myorder.setAdapter(itemadapter);

        TransactionsCollector collector = new TransactionsCollector(30);

         item = collector.getPaymentLists();
        Log.d(this.toString(),"transaction recieved " + item.size());


        item.add(new payment("#orderid-0001",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0002",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0003",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0004",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0005",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0006",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0007",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0008",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0009",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0010",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0011",100,"upi",1400826750 ));
        item.add(new payment("#orderid-0012",100,"upi",1400826750 ));



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


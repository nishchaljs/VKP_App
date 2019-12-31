// reference : https://www.androidhive.info/2016/01/android-working-with-recycler-view/

//package info.androidhive.recyclerview;

package Adapter;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.payment;
import tecmanic.marketplace.R;

public class payment_adapter extends RecyclerView.Adapter<payment_adapter.MyViewHolder> {

    private List<payment> paymentList;


    //TODO : get data from razorpay about transaction and store in paymentList
    // paymentList = TransactionCollector.getTransactions();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView orderId, time, method;
        public TextView tv_payment_method, tv_payment_id, tv_payment_status, tv_payment_date, tv_payment_time, tv_payment_amount, tv_payment_email, tv_payment_contact ;
        LinearLayout payment_status_theme;
        public MyViewHolder(View view) {
            super(view);
            tv_payment_method=(TextView)view.findViewById(R.id.payment_method);
            tv_payment_id = (TextView) view.findViewById(R.id.tv_payment_id);
            tv_payment_status = (TextView) view.findViewById(R.id.payment_status);
            tv_payment_date = (TextView) view.findViewById(R.id.payment_date);
            tv_payment_time= (TextView) view.findViewById(R.id.payment_time);
            tv_payment_amount = (TextView) view.findViewById(R.id.tv_order_price);
            tv_payment_email = (TextView) view.findViewById(R.id.payment_email);
            tv_payment_contact = (TextView) view.findViewById(R.id.payment_contact);
            payment_status_theme = view.findViewById(R.id.payment_status_theme);
//
        }
    }


    public payment_adapter(List<payment> paymentList) {
        this.paymentList = paymentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_list_row, parent, false); //TODO: insert row for payment

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        payment Payment = paymentList.get(position);

        //TODO : change following code according to getter/setter method od payment class

        long timestamp = (long) Payment.getCreated_at();

        Date d = new Date( (long) timestamp*1000  );
        DateFormat f = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat f2 = new SimpleDateFormat("HH:mm:ss");
        String Payment_Date = f.format(d);
        String Payment_Time = f2.format(d);





        holder.tv_payment_id.setText(Payment.getId());
        holder.tv_payment_method.setText(Payment.getMethod());
        holder.tv_payment_date.setText(Payment_Date);
        holder.tv_payment_time.setText(Payment_Time);
        holder.tv_payment_amount.setText(String.valueOf( Payment.getAmount()/100.0 ));

        holder.tv_payment_status.setText(Payment.getStatus());
        holder.tv_payment_email.setText(Payment.getEmail());
        holder.tv_payment_contact.setText(Payment.getContact());



//        TODO
        if (holder.tv_payment_status.equals("created")){
            holder.payment_status_theme.setBackgroundColor(Color.parseColor("#fff570"));
        }
        else if (holder.tv_payment_status.equals("captured")){
            holder.payment_status_theme.setBackgroundColor(Color.parseColor("#75fa69"));
        }
        else if (holder.tv_payment_status.equals("authorized")){

            holder.payment_status_theme.setBackgroundColor(Color.parseColor("#6ffcf3"));
        }
        else if (holder.tv_payment_status.equals("failed")){
            holder.payment_status_theme.setBackgroundColor(Color.parseColor("#fa4848"));

        }
        else if (holder.tv_payment_status.equals("refunded")){
            holder.payment_status_theme.setBackgroundColor(Color.parseColor("#faac64"));
        }




    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
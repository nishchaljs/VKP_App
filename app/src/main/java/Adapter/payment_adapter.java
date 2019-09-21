// reference : https://www.androidhive.info/2016/01/android-working-with-recycler-view/

//package info.androidhive.recyclerview;

package Adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import Model.payment;
import tecmanic.marketplace.R;

public class payment_adapter extends RecyclerView.Adapter<payment_adapter.MyViewHolder> {

    private List<payment> paymentList;

    //TODO : get data from razorpay about transaction and store in paymentList
    // paymentList = TransactionCollector.getTransactions();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId, time, method;

        public MyViewHolder(View view) {
            super(view);
            orderId= (TextView) view.findViewById(R.id.tv_order_no);
            time = (TextView) view.findViewById(R.id.tv_order_time);
            method = (TextView) view.findViewById(R.id.payment_method);
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
        holder.orderId.setText(Payment.getId());
        holder.method.setText(Payment.getMethod());
        holder.time.setText(String.valueOf(Payment.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }
}
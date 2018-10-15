package com.example.fifty.sellerappv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fifty on 7/3/2018.
 */

public class PaymentInfoAdapter extends RecyclerView.Adapter<PaymentInfoAdapter.MassageHolder>{
    ArrayList<PaymentInfo> paymentInfoArrayList;
    public class MassageHolder extends RecyclerView.ViewHolder{
        TextView purchaserName , paymentDate , billAmount;
        PaymentInfo paymentInfo;
        Button stateBtn;
        public MassageHolder(View itemView) {
            super(itemView);
            purchaserName = (TextView) itemView.findViewById(R.id.purchaser_name);
            paymentDate = (TextView) itemView.findViewById(R.id.date);
            billAmount = (TextView) itemView.findViewById(R.id.bill_amount);
            stateBtn = (Button) itemView.findViewById(R.id.state);
        }
    }
    public PaymentInfoAdapter(ArrayList<PaymentInfo> paymentInfos){
        paymentInfoArrayList = paymentInfos;
    }
    public void swap(ArrayList<PaymentInfo> newList) {
            if(newList == null || newList.size()==0)
                return;
            if (paymentInfoArrayList != null && paymentInfoArrayList.size()>0)
                paymentInfoArrayList.clear();
            paymentInfoArrayList.addAll(newList);
            notifyDataSetChanged();
    }
    @Override
    public MassageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_info_card,parent,false);
        return new MassageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MassageHolder holder, int position) {
        holder.paymentInfo = paymentInfoArrayList.get(position);
        holder.purchaserName.setText(holder.paymentInfo.getPurchaserName());
        holder.billAmount.setText(holder.paymentInfo.getBillAmount()+" SDG");
        holder.paymentDate.setText(holder.paymentInfo.getStringDate());
        if (holder.paymentInfo.getFlag()==1){
            holder.stateBtn.setBackgroundResource(R.drawable.state_true);
            holder.stateBtn.setText("Completed");
        }
        else{
            holder.stateBtn.setBackgroundResource(R.drawable.state_false);
            holder.stateBtn.setText("Not Completed");
        }
    }

    @Override
    public int getItemCount() {
        return paymentInfoArrayList.size();
    }
   /* private View.OnClickListener availableBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            final EditText et = (EditText) v.findViewById(R.id.editTexttt);
            paymentInfoArrayList.get(itemPosition).setAnswer(true);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,Configuration.RETURN_MASSAGE_ANSWER,null
                    , new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getString(Configuration.KEY_RESULT).compareToIgnoreCase("success")==0){
                            Toast.makeText(v.getContext(), "Answer send successfully", Toast.LENGTH_SHORT).show();
                             alertDialog.dismiss();
                        }
                        else {
                            Toast.makeText(v.getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(v.getContext(), "Something went Wrong on Application try again", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(v.getContext(), "Something went Wrong try again", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> params = new HashMap<>();
                    SharedPreferences sharedPreferences = v.getContext().getSharedPreferences(Configuration.MY_PREFERENCE, Context.MODE_PRIVATE);
                    params.put(Configuration.MASSAGE_ANSWER,"true");
                    params.put(Configuration.MASSAGE_RESPONSE,et.getText().toString());
                    params.put(Configuration.MASSAGE_ID, paymentInfoArrayList.get(itemPosition).getSenderID());
                    params.put(Configuration.PHARMACEY_ID,sharedPreferences.getString(Configuration.PHARMACEY_ID,null));
                    return params;
                }
            };
            MySingleton.getInstance(v.getContext().getApplicationContext()).getRequestQueue().add(jsonObjectRequest);
        }
    };*/

}

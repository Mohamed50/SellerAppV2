package com.example.fifty.sellerappv2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static int IMG_RESULT = 1;
    String ImageDecode;
    String[] FILE;
    Intent intent;
    View alertView;
    static String username;
    static String password;
    static String userPhoneNO;
    AlertDialog alertDialog;
    EditText companyName, companyPhoneNo, companyBankAccount;
    Spinner spinner;
    Bitmap license;
    ArrayList<CompanyItem> companyItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initCompanyItems();
        setStatusBarGradiant(this);
        companyName = (EditText) findViewById(R.id.companyName);
        companyPhoneNo = (EditText) findViewById(R.id.companyPhoneNo);
        companyBankAccount = (EditText) findViewById(R.id.companyBankAccount);
        ImageView imageView = (ImageView) findViewById(R.id.license);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new CompanyAdapter(getBaseContext(),companyItems));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, IMG_RESULT);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == IMG_RESULT && resultCode == RESULT_OK
                    && null != data) {


                Uri URI = data.getData();
                String[] FILE = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(URI, FILE, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(FILE[0]);
                ImageDecode = cursor.getString(columnIndex);
                cursor.close();
                ImageView imageView = (ImageView) findViewById(R.id.license);
                imageView.setImageBitmap(BitmapFactory.decodeFile(ImageDecode));
                license = BitmapFactory.decodeFile(ImageDecode);
                Drawable drawable = new BitmapDrawable(getResources(), license);
                imageView.setImageDrawable(drawable);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }
    public void compeleteRegister(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RegisterActivity.this);
        alertView = getLayoutInflater().inflate(R.layout.register_completed_dialog,null);
        Button dismissBtn = (Button) alertView.findViewById(R.id.dissmissBtn);
        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        mBuilder.setView(alertView);
        alertDialog = mBuilder.create();
        alertDialog.show();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.side_nav_bar);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorTransparent));
            window.setBackgroundDrawable(background);
        }
    }
    public void sendDataTheServer(View view){
        StringRequest registerRequest = new StringRequest(Request.Method.POST,Configuration.ADD_COMPANY_URL,registerResponse,registerErrorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                SharedPreferences sharedPreferences = getSharedPreferences(Configuration.MY_PREFERENCE,MODE_PRIVATE);
                Map<String,String> params = new HashMap<>();
                params.put(Configuration.COMPANY_NAME,companyName.getText().toString());
                params.put(Configuration.COMPANY_PHONE_NO,companyPhoneNo.getText().toString());
                params.put(Configuration.COMPANY_BANK_ACCOUNT,companyBankAccount.getText().toString());
                params.put(Configuration.COMPANY_LICENSE,imageToString(license));
                params.put(Configuration.KEY_SELLER_ID,sharedPreferences.getString(Configuration.KEY_SELLER_ID,null));
                return params;
            }

        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(registerRequest);
        registerRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*2,5,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
    Response.Listener<String> registerResponse = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString(Configuration.CODE)=="1"){
                    compeleteRegister();
                }
                Toast.makeText(RegisterActivity.this, jsonObject.getString(Configuration.MESSAGE), Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    Response.ErrorListener registerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(RegisterActivity.this, "Check Internet Connection", Toast.LENGTH_LONG).show();
        }
    };

    public String imageToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes,Base64.DEFAULT);
    }
    public void initCompanyItems(){
        companyItems = new ArrayList<CompanyItem>();
        companyItems.add(new CompanyItem("Cafe",R.mipmap.cafe_icon));
        companyItems.add(new CompanyItem("Restaurant",R.mipmap.restuarent_icon));
        companyItems.add(new CompanyItem("Game Store",R.mipmap.game_icon));
        companyItems.add(new CompanyItem("SuperMarket",R.mipmap.supermarket_icon));
    }

}

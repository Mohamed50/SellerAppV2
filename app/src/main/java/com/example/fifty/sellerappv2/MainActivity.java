package com.example.fifty.sellerappv2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity{
    LinearLayout loginLayout,registerLayout;
    Button openLogin , openRegister,loginBtn;
    ImageView logo;
    EditText l_username , l_password ,r_username , r_password , r_confirm_password , r_user_phone_no;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            loginLayout.setVisibility(View.VISIBLE);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarGradiant(this);
        isNetworkStatusAvailable(getApplicationContext());

        loginLayout = (LinearLayout) findViewById(R.id.login_Layout);
        registerLayout = (LinearLayout) findViewById(R.id.register_layout);
        openLogin = (Button) findViewById(R.id.open_login);
        openRegister = (Button) findViewById(R.id.open_register);
        loginBtn = (Button) findViewById(R.id.login);
        logo = (ImageView) findViewById(R.id.logo);


        openLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.VISIBLE);
                registerLayout.setVisibility(View.GONE);
            }
        });
        openRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
            }
        });
        handler.postDelayed(runnable,3000);



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
    Response.Listener loginResponse = new Response.Listener<String>() {
        @Override
        public void onResponse(String res) {
            SharedPreferences sharedPreferences = getSharedPreferences(Configuration.MY_PREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            try {
                JSONObject response = new JSONObject(res);
                editor.putString(Configuration.PREFERENCE_USER_ID,response.getString(Configuration.USERS_ID));
                editor.putString(Configuration.PREFERENCE_USER_NAME,response.getString(Configuration.USERS_NAME));
                editor.putString(Configuration.PREFERENCE_USER_PHONE,response.getString(Configuration.USERS_PHONE));
                editor.commit();
                Toast.makeText(getBaseContext(), "Login successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),MenuActivity.class);
                startActivity(intent);
            } catch (JSONException e) {
                Toast.makeText(getBaseContext(), "Un handel exceptions", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };
    Response.ErrorListener loginErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getBaseContext(), "Server Not responding", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(),MenuActivity.class);
            startActivity(intent);
        }
    };
    public void login(View view){
        l_username = (EditText) findViewById(R.id.l_username_et);
        l_password = (EditText) findViewById(R.id.l_password_et);
        if (l_username.getText().toString().isEmpty() || l_password.getText().toString().isEmpty()){
            Toast.makeText(this, "fill all the field", Toast.LENGTH_SHORT).show();
            l_username.requestFocus();
        }
        else{
            /*StringRequest loginRequest = new StringRequest(Request.Method.POST,Configuration.LOGIN_URL,
                    loginResponse,loginErrorListener){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put(Configuration.USERS_NAME,l_username.getText().toString());
                    params.put(Configuration.USERS_PASSWORD,l_password.getText().toString());
                    return params;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);*/

            SharedPreferences sharedPreferences = getSharedPreferences(Configuration.MY_PREFERENCE,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Configuration.PREFERENCE_USER_SERIAL_NO,Build.SERIAL);
            editor.commit();
            System.out.println("SSSSSSSSSSSSSSSSSSSSSSs:"+sharedPreferences.getString(Configuration.PREFERENCE_USER_SERIAL_NO,"GGGG"));
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }
    }
    public void register(View view){
        r_username = (EditText) findViewById(R.id.r_username);
        r_password = (EditText) findViewById(R.id.r_password);
        r_confirm_password = (EditText) findViewById(R.id.r_confirm_password);
        r_user_phone_no = (EditText) findViewById(R.id.r_user_phone_no);
        if (r_username.getText().toString().isEmpty() || r_password.getText().toString().isEmpty()
                || r_confirm_password.getText().toString().isEmpty() || r_user_phone_no.getText().toString().isEmpty()){
            Toast.makeText(this, "please fill all field", Toast.LENGTH_SHORT).show();
        }
        else if (r_password.getText().toString().compareTo(r_confirm_password.getText().toString())!=0){
            Toast.makeText(this, "password and confirm password is not the same", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
            startActivity(intent);
            String username = r_username.getText().toString();
            String password = r_password.getText().toString();
            String phone = r_user_phone_no.getText().toString();
            sendRegisterDataToTheServer(username,password,phone);
        }
    }
    public boolean isNetworkStatusAvailable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null){
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null){
                Toast.makeText(this, "Internet is Connected", Toast.LENGTH_SHORT).show();
                return  networkInfo.isConnected();
            }
        }
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        return false;
    }
    public void sendRegisterDataToTheServer(final String username, final String password, final String phone){
        StringRequest registerRequest = new StringRequest(Request.Method.POST,Configuration.REGISTER_URL,registerResponse,registerErrorListener){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put(Configuration.USERS_NAME,username);
                params.put(Configuration.USERS_PASSWORD,password);
                params.put(Configuration.USERS_PHONE,phone);

                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(registerRequest);

    }
    Response.Listener<String> registerResponse = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
        }
    };
    Response.ErrorListener registerErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

}

package com.example.fifty.sellerappv2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.nio.charset.Charset;
import java.util.Date;

public class ReceiveActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback ,
        NfcAdapter.OnNdefPushCompleteCallback{
    NfcAdapter nfcAdapter;
    EditText bill_amount ;
    Button sendBillBtn;
    String extrnalType = "nfclab.com:SmartPay";
    boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        bill_amount = (EditText) findViewById(R.id.bill_amount_et);
        sendBillBtn = (Button) findViewById(R.id.send_bill_btn);
        checkNFCSupporting();
        checkForNFCAdapter();
        nfcAdapter.setOnNdefPushCompleteCallback(this,this);
        nfcAdapter.setNdefPushMessageCallback(this,this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkNFCSupporting();
        checkForNFCAdapter();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            receiveIntent(getIntent());
            Toast.makeText(this, "reciveing", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkNFCSupporting(){
        PackageManager pm = this.getPackageManager();
        // Check whether NFC is available on device
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // NFC is not available on the device.
            Toast.makeText(this, "The device does not has NFC hardware.",
                    Toast.LENGTH_SHORT).show();
        }
        // Check whether device is running Android 4.1 or higher
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // Android Beam feature is not supported.
            Toast.makeText(this, "Android Beam is not supported.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            // NFC and Android Beam file transfer is supported.
            Toast.makeText(this, "Android Beam is supported on your device.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void checkForNFCAdapter(){
        nfcAdapter = NfcAdapter.getDefaultAdapter(getBaseContext());
        if (!nfcAdapter.isEnabled()){
            Toast.makeText(this, "Enable NFC Please", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        else if (!nfcAdapter.isNdefPushEnabled()){
            Toast.makeText(this, "Enable Android Beam Please", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else{
            Toast.makeText(this, "ready to pay", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendBill(NdefMessage ndefMessage,String msg) {
        bill_amount.setText(msg);
        nfcAdapter.setNdefPushMessage(ndefMessage,this);
    }

    private PaymentInfo getPaymentInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(Configuration.MY_PREFERENCE,MODE_PRIVATE);
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setBillAmount(bill_amount.getText().toString());
        paymentInfo.setCompanyName(sharedPreferences.getString(Configuration.PREFERENCE_COMPANY_NAME,""));
        paymentInfo.setCompanyType(sharedPreferences.getInt(Configuration.PREFERENCE_COMPANY_TYPE,0));
        paymentInfo.setCompanyBankAccount(sharedPreferences.getString(Configuration.COMPANY_BANK_ACCOUNT,null));
        paymentInfo.setCompanyId(sharedPreferences.getString(Configuration.KEY_SELLER_ID,null));
        return paymentInfo;
    }

    public NdefRecord createNDEFRecord(String msg){
        byte [] payload = msg.getBytes(Charset.forName("UTF-8"));
        NdefRecord record = new NdefRecord(
                NdefRecord.TNF_EXTERNAL_TYPE,
                extrnalType.getBytes(),
                new byte[0],
                payload
        );
        return record;

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        PaymentInfo paymentInfo = getPaymentInfo();
        Gson g = new Gson();
        String msg = g.toJson(paymentInfo);
        NdefRecord ndefRecord = createNDEFRecord(msg);
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {

        Toast.makeText(this, "Payment process has done successfully", Toast.LENGTH_SHORT).show();
        check = true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    private void receiveIntent(Intent intent) {
        NdefMessage messages = getNdefMessages(getIntent());
        NdefRecord record = messages.getRecords()[0];
        //String payload = new String (record.getPayload(),1,record.getPayload().length-1, Charset.forName("UTF-8"));
        String payload = new String (record.getPayload());
        Log.d("is arrive",payload);
        Toast.makeText(this, "Receiving:"+payload, Toast.LENGTH_SHORT).show();

        bill_amount.setText(payload);
        insertPaymentInfoToDBA(payload);
    }
    private NdefMessage getNdefMessages(Intent intent) {
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage msg = (NdefMessage) rawMsgs[0];

        return msg;
    }

    private void insertPaymentInfoToDBA(String json){
        Gson g = new Gson();
        PaymentInfo paymentInfo = g.fromJson(json,PaymentInfo.class);
        LocalDBA.getInstance(getApplicationContext()).insertPaymentTransaction(paymentInfo);
        paymentDialog();
    }
    private void paymentDialog(){
        View alertView = getLayoutInflater().inflate(R.layout.payment_transaction_success_dialog,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(alertView);
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

}

package com.example.fifty.sellerappv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Fifty on 6/20/2018.
 */

public class LocalDBA extends SQLiteOpenHelper  {
    private static LocalDBA mInstance;

    public LocalDBA(Context context) {
        super(context, Configuration.DATABASE_NAME, null, 1);
    }

    public static synchronized LocalDBA getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocalDBA(context);
        }
        return mInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
               db.execSQL("create Table PaymentsTransaction"
                +"(p_id integer primary key AUTOINCREMENT ,p_unique_id , p_bill_Amount real , p_payment_date text , p_time text ,p_company_bank_account integer,p_company_id integer," +
                        " p_company_type integer, p_company_name text,p_purchaser_id text,p_purchaser_name text,p_purchaser_card_id text,p_flag integer,p_send integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table If Exists PaymentsTransaction");
        onCreate(db);

    }


    public boolean insertPaymentTransaction(PaymentInfo paymentInfo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_BILL_AMOUNT,paymentInfo.getBillAmount());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_UNIQUE_ID,paymentInfo.getUniqueId());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_ID,paymentInfo.getCompanyId());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_BANK_ACCUONT,paymentInfo.getCompanyBankAccount());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_NAME,paymentInfo.getCompanyName());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_TYPE,paymentInfo.getCompanyType());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_DATE,paymentInfo.getStringDate());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_TIME,paymentInfo.getStringTime());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_ID,paymentInfo.getPurchaserId());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_NAME,paymentInfo.getPurchaserName());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_CARD_ID,paymentInfo.getCardId());
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_FLAG,0);
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_SEND_FLAG,0);
        db.insert(Configuration.TABLE_PAYMENT_TRANSACTION_TABLE_NAME,null,contentValues);
        return true;
    }
    public boolean updatePaymentTransactionFlag(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_FLAG,1);
        db.update(Configuration.TABLE_PAYMENT_TRANSACTION_TABLE_NAME,contentValues
                ,Configuration.TABLE_PAYMENT_TRANSACTION_ID+"= ?"
                ,new String[]{Integer.toString(id)});
        return true;
    }
    public boolean updatePaymentTransactionSendFlag(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Configuration.TABLE_PAYMENT_TRANSACTION_SEND_FLAG,1);
        db.update(Configuration.TABLE_PAYMENT_TRANSACTION_TABLE_NAME,contentValues
                ,Configuration.TABLE_PAYMENT_TRANSACTION_ID+"= ?"
                ,new String[]{Integer.toString(id)});
        return true;
    }
    public ArrayList<PaymentInfo> getAllPaymentTransaction(){
        ArrayList<PaymentInfo> paymentInfoArrayList = new ArrayList<PaymentInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("Select * from PaymentsTransaction",null);
        result.moveToFirst();
        while (result.isAfterLast() == false){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setTransactionId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_ID)));
            paymentInfo.setUniqueId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_UNIQUE_ID)));
            paymentInfo.setBillAmount(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_BILL_AMOUNT)));
            paymentInfo.setCompanyId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_ID)));
            paymentInfo.setCompanyName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_NAME)));
            paymentInfo.setCompanyType(result.getInt(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_TYPE)));
            paymentInfo.setStringDate(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_DATE)));
            paymentInfo.setStringTime(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_TIME)));
            paymentInfo.setPurchaserName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_NAME)));
            paymentInfo.setCardId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_CARD_ID)));
            paymentInfo.setFlag(Integer.valueOf(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_FLAG))));
            paymentInfoArrayList.add(paymentInfo);
            result.moveToNext();
        }
        return paymentInfoArrayList;
    }


    public ArrayList<PaymentInfo> getUncommittedPaymentTransaction(){
        ArrayList<PaymentInfo> paymentInfoArrayList = new ArrayList<PaymentInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("Select * from PaymentsTransaction where p_flag=0 and p_send=0",null);
        result.moveToFirst();
        while (result.isAfterLast() == false){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setTransactionId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_ID)));
            paymentInfo.setUniqueId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_UNIQUE_ID)));
            paymentInfo.setBillAmount(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_BILL_AMOUNT)));
            paymentInfo.setCompanyId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_ID)));
            paymentInfo.setCompanyName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_NAME)));
            paymentInfo.setCompanyType(result.getInt(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_TYPE)));
            paymentInfo.setStringDate(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_DATE)));
            paymentInfo.setStringTime(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_TIME)));
            paymentInfo.setPurchaserName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_NAME)));
            paymentInfo.setCardId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_CARD_ID)));
            paymentInfo.setPurchaserId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_ID)));
            paymentInfo.setCompanyBankAccount(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_BANK_ACCUONT)));
            paymentInfoArrayList.add(paymentInfo);
            result.moveToNext();
        }
        return paymentInfoArrayList;
    }

    public ArrayList<PaymentInfo> getNonResponsePaymentTransaction(){
        ArrayList<PaymentInfo> paymentInfoArrayList = new ArrayList<PaymentInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("Select * from PaymentsTransaction where p_flag=0 and p_send=1",null);
        result.moveToFirst();
        while (result.isAfterLast() == false){
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setTransactionId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_ID)));
            paymentInfo.setUniqueId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_UNIQUE_ID)));
            paymentInfo.setBillAmount(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_BILL_AMOUNT)));
            paymentInfo.setCompanyId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_ID)));
            paymentInfo.setCompanyName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_NAME)));
            paymentInfo.setCompanyType(result.getInt(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_TYPE)));
            paymentInfo.setStringDate(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_DATE)));
            paymentInfo.setStringTime(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_TIME)));
            paymentInfo.setPurchaserName(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_NAME)));
            paymentInfo.setCardId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_CARD_ID)));
            paymentInfo.setPurchaserId(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_PURCHASER_ID)));
            paymentInfo.setCompanyBankAccount(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_COMPANY_BANK_ACCUONT)));
            paymentInfo.setSendFlag(Integer.valueOf(result.getString(result.getColumnIndex(Configuration.TABLE_PAYMENT_TRANSACTION_SEND_FLAG))));

            paymentInfoArrayList.add(paymentInfo);
            result.moveToNext();
        }
        return paymentInfoArrayList;
    }
}

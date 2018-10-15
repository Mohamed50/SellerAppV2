package com.example.fifty.sellerappv2;

/**
 * Created by Fifty on 7/11/2018.
 */

public class Configuration {
    //Server Urls
    public static final String LOGIN_URL = "http://c419caf9.ngrok.io/login";
    public static final String SEND_PAYMENT_INFO_URL = "http://c419caf9.ngrok.io/add/seller/payment";
    public static final String CHECK_PAYMENT_INFO_URL = "http://c419caf9.ngrok.io/add/seller/payment/check";
    public static final String REGISTER_URL="http://c419caf9.ngrok.io/add/seller";
    public static final String ADD_COMPANY_URL="http://c419caf9.ngrok.io/add/company";



    public static final String MY_PREFERENCE = "";
    public static final String PREFERENCE_USER_ID = "userID";
    public static final String PREFERENCE_USER_NAME = "userName";
    public static final String PREFERENCE_USER_PHONE = "phoneNumber";
    public static final String PREFERENCE_USER_EMAIL = "email";
    public static final String PREFERENCE_USER_SERIAL_NO = "serialNo";
    public static final String PREFERENCE_COMPANY_NAME = "companyName";
    public static final String PREFERENCE_COMPANY_TYPE = "companyType";

    //USER Table KEYS
    public static final String USERS_ID = "userID";
    public static final String USERS_NAME = "userName";
    public static final String USERS_PHONE = "phoneNumber";
    public static final String USERS_PASSWORD = "password";
    public static final String COMPANY_NAME = "companyName";
    public static final String COMPANY_PHONE_NO = "companyPhoneNo";
    public static final String COMPANY_BANK_ACCOUNT = "companyBankAccount";
    public static final String COMPANY_LICENSE = "companyLicense";

    //Local DataBase Tables And Keys
    public static final  String DATABASE_NAME = "smartpayseller.db";
    public static final  String TABLE_PAYMENT_TRANSACTION_TABLE_NAME = "PaymentsTransaction";
    public static final  String TABLE_PAYMENT_TRANSACTION_ID = "p_id";
    public static final  String TABLE_PAYMENT_TRANSACTION_UNIQUE_ID = "p_unique_id";
    public static final  String TABLE_PAYMENT_TRANSACTION_BILL_AMOUNT = "p_bill_Amount";
    public static final  String TABLE_PAYMENT_TRANSACTION_DATE = "p_payment_date";
    public static final  String TABLE_PAYMENT_TRANSACTION_TIME = "p_time";
    public static final  String TABLE_PAYMENT_TRANSACTION_COMPANY_ID = "p_company_id";
    public static final  String TABLE_PAYMENT_TRANSACTION_COMPANY_BANK_ACCUONT = "p_company_bank_account";
    public static final  String TABLE_PAYMENT_TRANSACTION_COMPANY_TYPE = "p_company_type";
    public static final  String TABLE_PAYMENT_TRANSACTION_COMPANY_NAME = "p_company_name";
    public static final String TABLE_PAYMENT_TRANSACTION_PURCHASER_ID = "p_purchaser_id";
    public static final  String TABLE_PAYMENT_TRANSACTION_PURCHASER_NAME = "p_purchaser_name";
    public static final  String TABLE_PAYMENT_TRANSACTION_PURCHASER_CARD_ID = "p_purchaser_card_id";
    public static final  String TABLE_PAYMENT_TRANSACTION_FLAG = "p_flag";
    public static final  String TABLE_PAYMENT_TRANSACTION_SEND_FLAG = "p_send";


    //PAYMENT INFO JSON KEYS
    public static final String PAYMENT_TRANSACTION_ID = "transactionId";
    public static final String PAYMENT_TRANSACTION_UNIQUE_ID = "uniqueId";
    public static final  String PAYMENT_TRANSACTION_BILL_AMOUNT = "billAmount";
    public static final  String PAYMENT_TRANSACTION_DATE = "date";
    public static final  String PAYMENT_TRANSACTION_TIME = "time";
    public static final  String PAYMENT_TRANSACTION_COMPANY_ID = "sellerId";
    public static final  String PAYMENT_TRANSACTION_COMPANY_BANK_ACCUONT = "sellerBankAccount";
    public static final  String PAYMENT_TRANSACTION_PURCHASER_ID = "purchaserId";
    public static final  String PAYMENT_TRANSACTION_PURCHASER_CARD_ID = "purchaserCardID";

    public static final String CODE ="code" ;
    public static final String MESSAGE ="message" ;
}

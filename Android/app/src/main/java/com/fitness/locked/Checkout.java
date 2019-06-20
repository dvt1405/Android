package com.fitness.locked;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fitness.MainActivity;
import com.fitness.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import model.PracticeGroup;
import sqlite.PracticeGroupDAO;
import vn.momo.momo_partner.AppMoMoLib;

public class Checkout extends AppCompatActivity {
    private PracticeGroup practiceGroup;
    private Button payByMoMo;
    private TextView practiceCheckout, priceCheckout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        init();
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT
        );
        payByMoMo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPayment();
            }
        });
    }
    private void init() {
        payByMoMo = findViewById(R.id.payByMoMo);
        practiceCheckout = findViewById(R.id.practiceCheckout);
        priceCheckout = findViewById(R.id.priceCheckout);
        Intent intent = getIntent();
        practiceGroup = (PracticeGroup) intent.getSerializableExtra("practice");
        practiceCheckout.setText(practiceGroup.getName());
        priceCheckout.setText(String.valueOf(practiceGroup.getLocked()));
    }
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);
        Map<String,Object> eventValue = new HashMap<>();
        eventValue.put("merchantname", Constranst.merchantName);
        eventValue.put("merchantcode", Constranst.merchantCode);
        eventValue.put("amount", 0); //So tien
        eventValue.put("orderId", "orderId123456789");
        eventValue.put("orderLabel", "Mã đơn hàng");

        eventValue.put("merchantnamelabel", "Dịch vụ");
        eventValue.put("fee", 0);
        eventValue.put("description", practiceGroup.getName());
        eventValue.put("requestId",  Constranst.merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", Constranst.merchantCode);
//        JSONObject objExtraData = new JSONObject();
//        try {
//            objExtraData.put("site_code", "008");
//            objExtraData.put("site_name", "CGV Cresent Mall");
//            objExtraData.put("screen_code", 0);
//            objExtraData.put("screen_name", "Special");
//            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
//            objExtraData.put("movie_format", "2D");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        eventValue.put("extraData", objExtraData.toString());
//        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    Intent intent = new Intent(this, MainActivity.class);
                    practiceGroup.setLocked(0);
                    new PracticeGroupDAO(this).updatePractice(practiceGroup);
                    startActivity(intent);
                    if(data.getStringExtra("data") != null && !data.getStringExtra("data").equals("")) {
                        // TODO:

                    } else {

                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    Log.e("STATUS", message);
                } else if(data.getIntExtra("status", -1) == 2) {
                } else {
                }
            } else {
            }
        } else {
        }
    }
}

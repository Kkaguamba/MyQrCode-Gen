package com.example.myqrcodegen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DetailsCenter extends AppCompatActivity {
    Button btn_Gen;
    EditText phone_no, cash_input;
    ImageView img_qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details_center);
        btn_Gen = findViewById(R.id.btn_generate);
        phone_no = findViewById(R.id.phone);
        cash_input = findViewById(R.id.amount_input);
        img_qr = findViewById(R.id.img_qr);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_Gen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {generateQR();}
            private void generateQR() {
                String phone = phone_no.getText().toString().trim();
                String cash = cash_input.getText().toString().trim();
                 if (!TextUtils.isEmpty(phone)){
                     if (phone.length() == 10){
                         if (!TextUtils.isEmpty(cash)){
                             MultiFormatWriter writer = new MultiFormatWriter();
                             Bitmap bitmap;
                             try {
                                 String text;
                                 String currencyPrefix = "Shs. ";
                                 text = phone + "\n" + currencyPrefix+cash;
                                 BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 300, 300);
                                 BarcodeEncoder encoder = new BarcodeEncoder();
                                 bitmap = encoder.createBitmap(matrix);
                                 img_qr.setImageBitmap(bitmap);
                                 img_qr.setVisibility(View.INVISIBLE);
                             } catch (WriterException e) {
                                 throw new RuntimeException(e);
                             }
                             Intent i = new Intent(DetailsCenter.this, QrGenerator.class);
                             i.putExtra("qr_image", bitmap);
                             startActivity(i);
                         }else {
                             cash_input.setError("Enter amount to generate QR code!");
                         }
                         }else {
                         phone_no.setError("Enter valid mobile number!");

                     }
                     }else {
                     phone_no.setError("Phone field can't be empty!");
                 }
            }
        });
    }
}
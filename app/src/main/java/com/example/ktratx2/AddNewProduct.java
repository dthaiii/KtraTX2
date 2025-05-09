package com.example.ktratx2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddNewProduct extends AppCompatActivity {
    TextView tvID;
    EditText edtProduct, edtPrice, edtEmail, edtRela;
    Button btnSave, btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        btnSave.setOnClickListener(v -> {
            Intent intent = getIntent();
            if ((edtProduct.getText().toString().trim().length() < 3) || (edtProduct.getText().toString().trim().length() < 3)) {
                Toast.makeText(AddNewProduct.this, "Dữ liệu nhập phải lớn hơn 2 ký tự", Toast.LENGTH_SHORT).show();
            } else {
                Product product = new Product(0, edtProduct.getText().toString(), Integer.parseInt(edtPrice.getText().toString()), edtEmail.getText().toString(), edtRela.getText().toString());
                intent.putExtra("product", product);
                setResult(999, intent);
                finish();
            }
        });
        btnClose.setOnClickListener(v -> finish());

        }
    private void mapping () {
        tvID = findViewById(R.id.tv_id);
        edtProduct = findViewById(R.id.edt_product);
        edtPrice = findViewById(R.id.edt_price);
        edtEmail = findViewById(R.id.edt_email);
        edtRela = findViewById(R.id.edt_rela);
        btnSave = findViewById(R.id.btn_save);
        btnClose = findViewById(R.id.btn_close);
    }
}

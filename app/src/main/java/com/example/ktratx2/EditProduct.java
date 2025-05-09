package com.example.ktratx2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProduct extends AppCompatActivity {
    private TextView tvId;
    private EditText edtTitle, edtContent, edtEmail, edtRela;
    private Button btnUpdate, btnClose;
    Product product;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mapping();
        intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        tvId.setText(""+product.getId());
        edtTitle.setText(product.getName());
        edtContent.setText(String.valueOf(product.getPrice()));
        edtEmail.setText(product.getEmail());
        edtRela.setText(product.getRela());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtTitle.getText().toString().trim().length() < 3 || edtContent.getText().toString().trim().length() < 3 || edtEmail.getText().toString().trim().length() < 3 || edtRela.getText().toString().trim().length() < 3) {
                    Toast.makeText(EditProduct.this, "Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    product.setName(edtTitle.getText().toString());
                    product.setPrice(Integer.parseInt(edtContent.getText().toString()));
                    product.setEmail(edtEmail.getText().toString());
                    product.setRela(edtRela.getText().toString());
                    intent.putExtra("product", product);
                    setResult(888, intent);
                    finish();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void mapping() {
        tvId = findViewById(R.id.tv_id_edit);
        edtTitle = findViewById(R.id.edt_title_edit);
        edtContent = findViewById(R.id.edt_content_edit);
        edtEmail = findViewById(R.id.edt_email_edit);
        edtRela = findViewById(R.id.edt_rela_edit);
        btnUpdate = findViewById(R.id.btn_update_edit);
        btnClose = findViewById(R.id.btn_close_edit);
    }
}

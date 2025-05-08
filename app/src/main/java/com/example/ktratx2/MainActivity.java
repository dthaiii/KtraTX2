package com.example.ktratx2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvProduct, tvPrice;
    ListView lvProduct;
    ArrayList<Product> arrProduct;
    ArrayAdapter adapter;
    Intent intent;
    ActivityResultLauncher activityResultLauncher;
    ProductManagement productManagement;
    public int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        arrProduct = new ArrayList<Product>();
        // tao csdl
        productManagement = new ProductManagement(this, "Product.db", null, 1);
        //tao bang
        String sql = "CREATE TABLE IF NOT EXISTS Product(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT)";
        productManagement.myExecute(sql);
        mapping();
        // nhan data ActivityResultLauch
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK){
                    Intent intent = o.getData();
                    Product product = (Product) intent.getSerializableExtra("product");
                    //them du lieu vao bang
                    String sql = "INSERT INTO Product VALUES(null, '"+product.getName()+"', '"+product.getPrice()+"')";
                    productManagement.myExecute(sql);
                    readProductManagement(productManagement);
                }
                if (o.getResultCode() == 888){
                    intent = o.getData();
                    Product product = (Product) intent.getSerializableExtra("product");
                    //cap nhat du lieu
                    String sql = "UPDATE Product SET title = '"+product.getName()+"', content = '"+product.getPrice()+"' WHERE id = "+product.getId();
                    productManagement.myExecute(sql);
                    readProductManagement(productManagement);
                }
            }
        });
        readProductManagement(productManagement);
        if (!arrProduct.isEmpty()){
            currentPosition = 0;
            myDisplay(arrProduct.get(currentPosition));
        }
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                myDisplay(arrProduct.get(currentPosition));

            }
        });
        // chuc nang xoa ban ghi trong database
        lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = arrProduct.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Chú ý xóa bản ghi");
                builder.setMessage("Bạn có chắc chắn không?");
                //Add button
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFromTable(product.getId());
                        readProductManagement(productManagement);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    private void deleteFromTable(int id) {
        String sql = "DELETE FROM Product WHERE id = "+id;
        productManagement.myExecute(sql);
    }

    private void myDisplay(Product product) {
        tvProduct.setText(product.getName());
        tvPrice.setText(String.valueOf(product.getPrice()));

    }

    private void readProductManagement(ProductManagement productManagement) {
        //read data
        String sql = "SELECT * FROM Product";
        Cursor cursor = productManagement.myGetSql(sql);
        arrProduct.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Float price = cursor.getFloat(2);
            arrProduct.add(new Product(id, name, price));
        }
        adapter.notifyDataSetChanged();
    }

    private void mapping(){
        tvProduct = findViewById(R.id.tv_product);
        tvPrice = findViewById(R.id.tv_price);
        lvProduct = findViewById(R.id.lv_note);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrProduct);
        lvProduct.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add_new){
            intent = new Intent(MainActivity.this, AddNewProduct.class);
            activityResultLauncher.launch(intent);
        } else {
            if (item.getItemId() == R.id.item_edit){
                intent = new Intent(MainActivity.this, EditProduct.class);
                intent.putExtra("product", arrProduct.get(currentPosition));
                activityResultLauncher.launch(intent);
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
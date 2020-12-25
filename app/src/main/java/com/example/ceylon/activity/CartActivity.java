package com.example.ceylon.activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ceylon.R;
import com.example.ceylon.adapters.CartAdapter;
import com.example.ceylon.model.GeneralFood;

import java.util.List;

import static com.example.ceylon.activity.MainActivity.cartFoods;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerviewCart;
    static TextView cartPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle("Ceylon Cart");
        mToolbar.setTitleTextColor(0xFFFFFFFF);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartActivity.super.onBackPressed();
            }
        });

        cartPrice = findViewById(R.id.cart_price);
        cartPrice.setText(Double.toString(grandTotal(cartFoods)));

        recyclerviewCart = findViewById(R.id.cart_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerviewCart.setLayoutManager(linearLayoutManager);
        recyclerviewCart.setNestedScrollingEnabled(false);
        recyclerviewCart.setAdapter(new CartAdapter(cartFoods, R.layout.recyclerview_cart, getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirmation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.order_button){
            Intent intent = new Intent(CartActivity.this, OrderActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public static int grandTotal(List< GeneralFood> cartFoods){

        int totalPrice = 0;
        for(int i = 0 ; i < cartFoods.size(); i++) {
            totalPrice += cartFoods.get(i).getPrice();
        }
        return totalPrice;
    }

    public static void priceAdjust(){
        cartPrice.setText(Double.toString(grandTotal(cartFoods)));
    }

}

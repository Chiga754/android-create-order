package com.example.cafeorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;


public class CreateOrderActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewAdditions;
    private CheckBox checkboxMilk;
    private CheckBox checkboxLemon;
    private CheckBox checkboxSugar;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String drink;
    private String name;
    private String password;
    private StringBuilder additions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        drink = getString(R.string.tea);
        Intent intent = getIntent();

        if(intent.hasExtra("name") && intent.hasExtra("password")){
            name = intent.getStringExtra("name");
            password = intent.getStringExtra("password");
        }else {
            name = getString(R.string.default_name);
            password = getString(R.string.default_password);
        }

        textViewTitle = findViewById(R.id.textViewHello);
        textViewTitle.setText(String.format(getString(R.string.create_order_title), name));

        textViewAdditions = findViewById(R.id.textViewAdditions);
        textViewAdditions.setText(String.format(getString(R.string.question), drink));
        checkboxMilk = findViewById(R.id.checkboxMilk);
        checkboxLemon = findViewById(R.id.checkboxLemon);
        checkboxSugar = findViewById(R.id.checkboxSugar);
        spinnerTea = findViewById(R.id.spinnerTea);
        spinnerCoffee = findViewById(R.id.spinnerCoffee);
        additions = new StringBuilder();

    }

    public void onClickChangeDrink(View view) {
        RadioButton radio = (RadioButton) view;
        int id = radio.getId();
        if(id == R.id.radioButtonTea) {
            drink = getString(R.string.tea);
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkboxLemon.setVisibility(View.VISIBLE);
        } else if(id == R.id.radioButtonCoffee) {
            drink = getString(R.string.coffee);
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkboxLemon.setVisibility(View.INVISIBLE);
        }
        String additions = String.format(getString(R.string.question), drink);
        textViewAdditions.setText(additions);
    }

    public void onClickSendOrder(View view) {
        additions.setLength(0);
        if(checkboxMilk.isChecked()) {
            additions.append(getString(R.string.milk)).append(" ");
        }
        if(checkboxLemon.isChecked()) {
            additions.append(getString(R.string.lemon)).append(" ");
        }
        if(checkboxSugar.isChecked() && drink == getString(R.string.tea)) {
            additions.append(getString(R.string.sugar)).append(" ");
        }
        String optionOfDrink = "";
        if(drink.equals(getString(R.string.tea))) {
            optionOfDrink = spinnerTea.getSelectedItem().toString();
        }else {
            optionOfDrink = spinnerCoffee.getSelectedItem().toString();
        }
        String order = String.format(getString(R.string.order), name, password, drink, optionOfDrink);
        String additionsTemp;
        if(additions.length() > 0) {
            additionsTemp = getString(R.string.need_additions) + additions.toString();
        } else {
            additionsTemp = "";
        }

        String fullOrder = order + additionsTemp;
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }
}
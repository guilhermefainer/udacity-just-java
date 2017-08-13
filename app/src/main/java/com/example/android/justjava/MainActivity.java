package com.example.android.justjava;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static java.text.NumberFormat.*;

public class MainActivity extends Activity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        boolean whippedCreamTopping = ((CheckBox)findViewById(R.id.whippedCream_check_box)).isChecked();
        boolean chocolateTopping = ((CheckBox)findViewById(R.id.chocolate_check_box)).isChecked();
        String name = ((EditText) findViewById(R.id.name_edit_text)).getText().toString();
        int price = calculatePrice(whippedCreamTopping, chocolateTopping);

        sendEmail(createOrderSummary(price, whippedCreamTopping, chocolateTopping, name));
    }

    private void sendEmail(String message)
    {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("*/*");
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "guilherme.fainer@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order));
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else
            Toast.makeText(this, getString(R.string.email_fail_send), Toast.LENGTH_SHORT).show();
    }

    public void increment(View view){
        if (quantity<100)
            quantity++;
        else
            Toast.makeText(this, getString(R.string.maximum_order), Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    public void decrement(View view){
        if (quantity>0)
            quantity--;
        else
            Toast.makeText(this, getString(R.string.minimum_order), Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    private int calculatePrice(boolean whippedCream, boolean chocolate)
    {
        int price = 5;

        if (whippedCream)
            price+=1;
        if (chocolate)
            price+=2;

        return price*quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_View);
        quantityTextView.setText("" + number);
    }

    private String createOrderSummary(int price, boolean whippedCream, boolean chocolate, String name){
        String message;
        message=getString(R.string.name)+": "+name+"\n";
        message+=getString(R.string.quantity)+": "+quantity+"\n";
        message+=getString(R.string.total)+": "+price+"\n";
        message+=getString(R.string.whipped_cream_topping)+"? "+whippedCream+"\n";
        message+=getString(R.string.chocolate_topping)+": "+chocolate+"\n";
        message+=getString(R.string.thank_you);

        return message;
    }

}

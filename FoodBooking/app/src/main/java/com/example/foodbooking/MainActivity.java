package com.example.foodbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.sax.Element;
import android.telephony.SmsManager;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    public static String PACKAGE_NAME;
    public static ArrayList<Integer> listOrder = new ArrayList<Integer>();
    private String phoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PACKAGE_NAME = getApplicationContext().getPackageName();

        final List<ItemList> list_food = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, list_food));

        Button placeOrder = (Button) findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String listFoodName = "";
                for (Integer e : listOrder) {
                    ItemList food = list_food.get(e);
                    String foodName = food.getFoodName();
                    listFoodName = listFoodName + " + " + foodName;
                }

                final String order = "Your order : " + listFoodName;

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please input your number !!!");

                // Set up the input
                final EditText input = new EditText(context);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_PHONE);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        phoneNumber = input.getText().toString();

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null,
                                order,
                                null, null);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    private List<ItemList> getListData() {
        List<ItemList> items = new ArrayList<ItemList>();

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Zucchini-Pear Soup",
                "f1",
                "https://www.cookingchanneltv.com/recipes/mark-bittman/zucchini-pear-soup-1961269"));
        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Yangzhou Fried Rice",
                "f2",
                "https://www.cookingchanneltv.com/recipes/ching-he-huang/yangzhou-fried-rice-1961585"));
        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Cauliflower Fried Rice",
                "f3",
                "https://www.cookingchanneltv.com/recipes/tiffani-thiessen/cauliflower-fried-rice-3629531"));


        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Steamed Sea Bass with Ginger and Chinese Mushrooms",
                "f4",
                "https://www.cookingchanneltv.com/recipes/ching-he-huang/steamed-sea-bass-with-ginger-and-chinese-mushrooms-2042296"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Baked Chinese Rice with Peas and Ginger",
                "f5",
                "https://www.cookingchanneltv.com/recipes/bill-granger/baked-chinese-rice-with-peas-and-ginger-1959076"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Arroz con Pollo",
                "f6",
                "https://www.cookingchanneltv.com/recipes/ingrid-hoffmann/arroz-con-pollo-1952724"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Chicken with Rice (Arroz con Pollo)",
                "f7",
                "https://www.cookingchanneltv.com/recipes/daisy-martinez/chicken-with-rice-arroz-con-pollo-1945235"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Chicken Cacciatore",
                "f8",
                "https://www.cookingchanneltv.com/recipes/debi-mazar-and-gabriele-corcos/chicken-cacciatore-2106913"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Trinidadian-Style Chicken",
                "f9",
                "https://www.cookingchanneltv.com/recipes/roger-mooking/trinidadian-style-chicken-1959070"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Chicken South Indian Style",
                "f10",
                "https://www.cookingchanneltv.com/recipes/bal-arneson/chicken-south-indian-style-2010126"));

        items.add(new ItemList("geo:0,0?q=14+Đồng+Đen+phường+14+Hồ+Chí+Minh+Bến+Nghé",
                "Martinique Coconut Chicken Curry",
                "f11",
                "https://www.cookingchanneltv.com/recipes/levi-roots/martinique-coconut-chicken-curry-2010043"));

        return items;
    }

}

package com.example.doitiennew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.R.layout.simple_spinner_dropdown_item;

// java.net.SocketException: socket failed: EPERM (Operation not permitted)
// uninstall app cài lại

//nếu muốn khi bật internet thì tiếp tục load thì dùng BroadcastReceiver

public class MainActivity extends AppCompatActivity {
//    TextView txt;
    Spinner spinner1, spinner2;
    EditText edt1, edt2;
    Button btn;
    ImageView img_convert;
    ProgressBar progressBar;
    ArrayList<Convert> listDescription;
    ListView lvDescription;
    DescriptionListViewAdapter DescriptionListViewAdapter;


    String url = "https://vnd.fxexchangerate.com/rss.xml"; //mặc định để tạo data for spinner

    List<String> currency_type_list, description_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        initView();





        //click vào chuyển đổi qua lại giữa 2 spinner

        img_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1 = 2 , 2 = 1
                int pos1 = spinner1.getSelectedItemPosition();
                int pos2 = spinner2.getSelectedItemPosition();
                spinner2.setSelection(pos1);
                spinner1.setSelection(pos2);


            }
        });


        //nếu có kết nối internet thì mới gọi webservice
        if (isNetworkAvailable(this)) {

            //

            new Asynctask_getXML().execute(url);


        } else {
            Toast.makeText(this, "Thiết bị của bạn chưa kết nối internet!", Toast.LENGTH_LONG).show();
        }

    }

    private void initView() {
//        txt = findViewById(R.id.txt);
        spinner1 = findViewById(R.id.mSpinner1);
        spinner2 = findViewById(R.id.mSpinner2);
        btn = findViewById(R.id.button);
        edt1 = findViewById(R.id.editText);
        edt2 = findViewById(R.id.editText2);
        img_convert = findViewById(R.id.img_convert);
        progressBar = findViewById(R.id.progressBar);
        lvDescription = (ListView) findViewById(R.id.listDescription);
    }

    //truyen vao chuoi description vn => ...
    public float lay_giatridoi(String s) {
        String arr[] = s.split("\\=");


        String arr1[] = arr[1].trim().split("\\s");
        float i = Float.parseFloat(arr1[0]);

        return i;
    }

    public String get_name_country_from_title(String s) {
        if (s.contains("(") && s.contains(")")) {
            s = s.replace("(", " ");
            s = s.replace(")", "");


        }
        String[] ar = s.split("\\s");
        return ar[0];
    }

    public String get_code_currency_from_title(String s) {

        String[] ar = s.split("\\s");

        String s1 = ar[ar.length - 1];

        if (s1.contains("(") && s1.contains(")")) {
            s1 = s1.replace("(", " ");
            s1 = s1.replace(")", "");


        }
        String[] s2 = s1.split("\\s");
        return s2[s2.length - 1];
    }


    //check internet
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    //get xml
    public class Asynctask_getXML extends AsyncTask<String, Void, String> {
        String xml = "";

        @Override
        protected void onPreExecute() {
            // Toast.makeText(MainActivity.this, "Asynctask running...", Toast.LENGTH_SHORT).show();
            super.onPreExecute();
//            txt.setText("Đợi 1 chút !");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {

            //get data from url
            try {

                URL url = new URL(strings[0]); //sử dụng URL của java để truy cập intenet
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder(); //builder string
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                xml = stringBuilder.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("a", e.toString() + "sa");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("a", e.toString() + "2");
            }


            return xml;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //biến mất progressBar
            progressBar.setVisibility(View.INVISIBLE);
//            txt.setText("Đã load xong !");



            //sử dụng XMLDomParse lấy về Document từng chuỗi lấy được từ API
            XMLDomParser xmlDomParser = new XMLDomParser();
            Document document = xmlDomParser.getDocument(s);

            //đọc theo từng <item>
            NodeList nodeList = document.getElementsByTagName("item");

            //duyệt nodelist
            String x = "";
            currency_type_list = new ArrayList<>();
            description_list = new ArrayList<>();
            listDescription = new ArrayList<>();

            String time = null;
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Element element = (Element) nodeList.item(i); //node thứ bao nhiêu
                //lấy title
                String title = xmlDomParser.getValue(element, "title");
                String main_s = title.replace("Vietnam Dong(VND)/", "");

                time = xmlDomParser.getValue(element, "pubDate");


                //lấy descrption
                String decription = xmlDomParser.getValue(element, "description");


                description_list.add(decription);
                currency_type_list.add(main_s);
                listDescription.add(new Convert(decription));
                DescriptionListViewAdapter = new DescriptionListViewAdapter(listDescription);
                lvDescription.setAdapter(DescriptionListViewAdapter);
            }
            //get_data_for_spinner(currency_type_list);
            if (spinner1.getSelectedItem() == null) {
                get_data_for_spinner(currency_type_list);
                //Toast.makeText(MainActivity.this, "set data spinner", Toast.LENGTH_SHORT).show();
            }

            //toast Date update

            Toast.makeText(MainActivity.this, time, Toast.LENGTH_SHORT).show();

            //viet btn.setOnClick
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //nếu description chứa spinner 2 thì lấy giá trị quy đổi
                    String edit = edt1.getText().toString();
                    int tien1 = Integer.parseInt(edit); //ép sang int từ giá trị edittext 1
                    String code2 = (String) spinner2.getSelectedItem();


                    for (int i = 0; i < description_list.size(); ++i) {


                        //1 Euro = 10.53537 Swedish KronaEuro
                        //neu có chứa tên quốc gia "Swedish"
                        if (description_list.get(i).contains(get_name_country_from_title(code2))) {
                            float value = lay_giatridoi(description_list.get(i));
                            float result = tien1 * value;

                            edt2.setText(result + " " + get_code_currency_from_title(code2)); // 12
                            // USD

//                            txt.setText(description_list.get(i));

                            break;
                        }

                    }
                    //nếu 2 spinner giống nhau thì trả về 1
                    if (spinner1.getSelectedItem().toString() == code2) {
                        edt2.setText(1 + "");

                    }
                }
            });

        }
    }

    private void get_data_for_spinner(List<String> data_list) {
        //spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                simple_spinner_dropdown_item, data_list);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);

        //chon spinner nào thì sinh url đó
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String code = get_code_currency_from_title(currency_type_list.get(position));
                url = "https://" + code.toLowerCase() + ".fxexchangerate.com/rss.xml";
                //execute Asynctask theo spinner selected item với url mới đã phát sinh
                if(isNetworkAvailable(getApplicationContext())){
                    new Asynctask_getXML().execute(url);
                }else{
                    Toast.makeText(MainActivity.this, "Thiết bị của bạn chưa kết nối internet!",
                            Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}


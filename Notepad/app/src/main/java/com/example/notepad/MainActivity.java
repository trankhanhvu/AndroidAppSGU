package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {

    public Context context;
    public Integer noteID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>♥ Notepad ♥</font>"));

        String path = this.getFilesDir().getAbsolutePath() + "/abc.xml";
        File file = new File(path);
        if(!file.exists())
        {
            File gpxfile = new File(String.valueOf(file));
            FileWriter writer = null;
            try {
                writer = new FileWriter(gpxfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<notes></notes>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ListView lstNotes = (ListView) findViewById(R.id.listNotes);
        AdapterNoteList adapter = new AdapterNoteList(getApplicationContext(),
                android.R.layout.simple_list_item_1, readByDOM(path)) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };
        lstNotes.setAdapter(adapter);

        lstNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noteID = position;
                Log.i("test", String.valueOf(noteID));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notepad_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent addIntent = new Intent(getApplicationContext(), EditActivity.class);
                addIntent.putExtra("noteID", "null");
                startActivity(addIntent);
                return true;
            case R.id.edit:
                if (noteID != null) {
                    Intent editIntent = new Intent(getApplicationContext(), EditActivity.class);
                    editIntent.putExtra("noteID", String.valueOf(noteID));
                    Log.i("test", String.valueOf(noteID));
                    startActivity(editIntent);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
                    builder.setTitle("Please select the note first !!!");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                return true;
            case R.id.delete:
                if (noteID != null) {
                    final String path = this.getFilesDir().getAbsolutePath() + "/abc.xml";
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
                    builder.setTitle("Are you sure !!!");
                    builder.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    builder.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    deleteNote(path, noteID);
                                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(main);
                                }
                            });
                    builder.show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyDialogTheme);
                    builder.setTitle("Please select the note first !!!");
                    builder.setPositiveButton("OK", null);
                    builder.show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static ArrayList<NoteItem> readByDOM(String file) {
        ArrayList<NoteItem> items = new ArrayList<NoteItem>();
        NoteItem item = null;
        String title = "", content = "";
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();

            FileInputStream fIn = new FileInputStream(file);
            Document doc = builder.parse(fIn);

            Element root = doc.getDocumentElement(); //lấy tag Root ra
            NodeList list = root.getChildNodes();// lấy toàn bộ node con của Root

            for (int i = 0; i < list.getLength(); i++)// duyệt từ node đầu tiên cho tới node cuối cùng
            {
                Node node = list.item(i);// mỗi lần duyệt thì lấy ra 1 node
                if (node instanceof Element) // kiểm tra node
                {
                    Element element = (Element) node; //lấy được tag Note ra
                    title = element.getAttribute("title");//title là thuộc tính của tag note

                    NodeList listChild = element.getElementsByTagName("content");// lấy tag name
                    content = listChild.item(0).getTextContent();//lấy nội dung của tag name

                    item = new NoteItem();
                    item.title = title;
                    item.content = content;
                    items.add(item);
                }
            }
        } catch (ParserConfigurationException e) {
        } catch (FileNotFoundException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        } finally {
            return items;
        }
    }

    public static void deleteNote(String file, int position) {
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();

            FileInputStream fIn = new FileInputStream(file);
            Document doc = builder.parse(fIn);

            Element root = doc.getDocumentElement(); //lấy tag Root ra
            NodeList list = root.getChildNodes();// lấy toàn bộ node con của Root

            int count = 0;
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);// mỗi lần duyệt thì lấy ra 1 node

                if (node instanceof Element) // kiểm tra node
                {
                    if (count == position) {
                        Element element = (Element) node; //lấy được tag Note ra
                        String title = element.getAttribute("title");//title là thuộc tính của tag note

                        NodeList listChild = element.getElementsByTagName("content");// lấy tag name
                        String content = listChild.item(0).getTextContent();//lấy nội dung của tag name

                        node.getParentNode().removeChild(node);
                    }
                    count += 1;
                }
            }

            EditActivity.saveToFile(doc, file);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }
}

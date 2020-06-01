package com.example.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class EditActivity extends AppCompatActivity {

    public String noteID = "null";
    public NoteItem noteItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        String path = this.getFilesDir().getAbsolutePath() + "/abc.xml";
        ArrayList<NoteItem> listNote = MainActivity.readByDOM(path);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            noteID = bundle.getString("noteID");
        }

        if (!noteID.equals("null")) {

            noteItem = listNote.get(Integer.parseInt(noteID));
            EditText title = (EditText) findViewById(R.id.title);
            title.setText(noteItem.getTitle());
            EditText content = (EditText) findViewById(R.id.content);
            content.setText(noteItem.getContent());

            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>Edit Notepad</font>"));
        } else
            actionBar.setTitle(Html.fromHtml("<font color='#FFFFFF'>New Notepad</font>"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.save:

                EditText title = (EditText) findViewById(R.id.title);
                EditText content = (EditText) findViewById(R.id.content);

                NoteItem note = new NoteItem();
                note.setTitle(title.getText().toString());
                note.setContent(content.getText().toString());

                String pathSave = this.getFilesDir().getAbsolutePath() + "/abc.xml";
                writeToDOM(pathSave, note);

                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(main);

                return true;
            case R.id.delete:

                final String pathDelete = this.getFilesDir().getAbsolutePath() + "/abc.xml";
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);
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
                                if (!noteID.equals("null"))
                                {
                                    MainActivity.deleteNote(pathDelete, Integer.parseInt(noteID));
                                }
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);
                            }
                        });
                builder.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void writeToDOM(String file, NoteItem item) {
        try {
            DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = fac.newDocumentBuilder();

            FileInputStream fIn = new FileInputStream(file);
            Document doc = builder.parse(fIn);

            Element root = doc.getDocumentElement(); //lấy tag Root ra

            if (noteID.equals("null")) {
                Element title = doc.createElement("note");
                title.setAttribute("title", item.getTitle());

                Element content = doc.createElement("content");
                content.appendChild(doc.createTextNode(item.getContent()));

                title.appendChild(content);
                root.appendChild(title);
            } else {
                NodeList list = root.getChildNodes();// lấy toàn bộ node con của Root

                int count = 0;
                for (int i = 0; i < list.getLength(); i++) {
                    Node node = list.item(i);// mỗi lần duyệt thì lấy ra 1 node

                    if (node instanceof Element) // kiểm tra node
                    {
                        if (count == Integer.parseInt(noteID)) {
                            Element element = (Element) node; //lấy được tag Note ra
                            element.setAttribute("title", item.getTitle());

                            NodeList listChild = element.getElementsByTagName("content");// lấy tag name
                            listChild.item(0).setTextContent(item.getContent());//lấy nội dung của tag name
                        }
                        count += 1;
                    }
                }
            }

            saveToFile(doc, file);

        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        } catch (SAXException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void saveToFile(Document doc, String file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(file));

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");
        } catch (TransformerFactoryConfigurationError | TransformerException transformerFactoryConfigurationError) {
            transformerFactoryConfigurationError.printStackTrace();
        }
    }
}

package com.example.pablo.searchjob;

import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pablo.searchjob.data.JobPostDbContract;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import static android.app.PendingIntent.getActivity;

public class Insertar extends AppCompatActivity {


    public EditText editTextTitle;
    public EditText editTextDescription;
    private EditText editTextNumber;
    private Button buttonSave;
    private final String TAG="imprime Log:";
    private static final String URLPOSTS="http://dipandroid-ucb.herokuapp.com/work_posts.json";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextTitle=(EditText)findViewById(R.id.editTextTitle1);
        editTextDescription=(EditText)findViewById(R.id.editTextDescription1);
        editTextNumber=(EditText)findViewById(R.id.editTextNumber1);
        buttonSave = (Button)findViewById(R.id.botonsave);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    saveJob(v);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**guardar insertar datos base de datos
     * **/

////pruebas lllll
    public void saveJob(View view) throws JSONException, UnsupportedEncodingException {
        AsyncHttpClient cliente=new AsyncHttpClient();
        String data_titulo,data_desc,data_contacts;
        data_titulo=editTextTitle.getText().toString();
        data_desc=editTextDescription.getText().toString();
        data_contacts=editTextNumber.getText().toString();




        JSONObject posjson=new JSONObject();
        posjson.put("title",data_titulo);
        posjson.put("description", data_desc);

        posjson.put("contacts", new JSONArray(Arrays.toString(data_contacts.split(","))));

        JSONObject mydata=new JSONObject();
        mydata.put("work_post", posjson);


        System.out.println(Arrays.toString(data_contacts.split(",")));
        System.out.println("EL JSON A ENVIAR");
        System.out.println(mydata.toString());


        StringEntity entity=new StringEntity(mydata.toString());
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

        cliente.post(this, URLPOSTS, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.getInt("id") > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Insertar.this);
                        builder.setTitle("Exito !").setMessage("Se Ingresaron los datos al servidor con exito!");
                        builder.setPositiveButton("Volver al listado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        });
                        AlertDialog dialog = builder.create();

                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("onFailure", "onFailure", throwable);
            }

        });
    }
    ///fin purebas
}

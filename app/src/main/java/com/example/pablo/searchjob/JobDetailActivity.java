package com.example.pablo.searchjob;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pablo.searchjob.data.JobPostDbContract;

import org.w3c.dom.Text;

public class JobDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        //TextView idTextView = (TextView)findViewById(R.id.job_id_text_view);
        //TextView titleTextView = (TextView)findViewById(R.id.job_title_text_view);

        EditText Title =(EditText)findViewById(R.id.editText_title);
        EditText Description =(EditText)findViewById(R.id.editText_Description);
        EditText Contact =(EditText)findViewById(R.id.editText_contact);


         String[] contacts;

        String cont = Long.toString(getIntent().getLongExtra("ID", 0));

        String[] columnas_contacts={JobPostDbContract.JobEntry._ID, JobPostDbContract.JobEntry.COLUMN_TITLE,
                JobPostDbContract.JobEntry.COLUMN_DESCRIPTION};
        String[] whereArgs={cont+""};


        Cursor cursor_contacts=(Cursor)getContentResolver().query(JobPostDbContract.JobEntry.CONTENT_URI,
                columnas_contacts, JobPostDbContract.JobEntry._ID + "=?",
                whereArgs, JobPostDbContract.JobEntry._ID + " ASC");
        contacts=new String[cursor_contacts.getCount()];

         ///////////////////////////////////////////////////////////

        String[] colunma_number={JobPostDbContract.ContactEntry.COLUMN_NUMBER};
        Log.d("id a verificar:",cont);
        Cursor cursor_nomber=(Cursor)getContentResolver().query(JobPostDbContract.ContactEntry.CONTENT_URI,colunma_number,
                JobPostDbContract.ContactEntry.COLUMN_JOB_ID + "=?",whereArgs,JobPostDbContract.ContactEntry.COLUMN_JOB_ID + " ASC");

        String ter=String.valueOf(cursor_nomber.getCount());
        String[] numero=new String[cursor_nomber.getCount()];
        Log.d("contador:",ter);
        String NumeroConcat="0";
        for (int i = 0; i < cursor_nomber.getCount(); i++) {
            Log.d("contador:","los datos vulven::");
            cursor_nomber.moveToNext();
            numero[i] = cursor_nomber.getString(0);
            NumeroConcat = NumeroConcat+"  "+numero[i];
            Log.d("datos::",numero[i]+"---");
        }
        Log.d("numero concatenado",NumeroConcat);


      //  String ter=String.valueOf(cursor_contacts.getCount());
      //  Log.d("contador:",ter);
        for (int i = 0; i < cursor_contacts.getCount(); i++) {

            cursor_contacts.moveToNext();
            contacts[i]=cursor_contacts.getString(2);

            Title.setText(cursor_contacts.getString(1));
            Description.setText(cursor_contacts.getString(2));
            Log.d("datos::",contacts[i]+"---");

        }
        //idTextView.setText(Long.toString(getIntent().getLongExtra("ID", 0)));
        //titleTextView.setText(getIntent().getStringExtra("DESCRIPTION"));

        Contact.setText(NumeroConcat);

    }
}

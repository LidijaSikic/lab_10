package vsite.hr.lab_10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import vsite.hr.lab_10.RegContract.Reg;

public class MainActivity extends AppCompatActivity {
    Uri uri = null;

    EditText et_ime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText et_ime = findViewById(R.id.editName);
        et_ime.requestFocus();

        Uri.Builder graditeljUri = new Uri.Builder();
        graditeljUri
                .scheme("content")
                .encodedAuthority(FirstContentProvider.AUTHORITY)
                .appendPath(FirstContentProvider.PATH_REG);
        uri = graditeljUri.build();
    }

    public void onClick(View v){
        RegHelper pomagac = new RegHelper(this);
        if (v.getId() == R.id.btnReg){
            try {
                SQLiteDatabase baza = pomagac.getWritableDatabase();
                EditText et_mail = findViewById(R.id.editMail);
                String mail = et_mail.getText().toString();
                //EditText et_ime = findViewById(R.id.editName);
                String ime = et_ime.getText().toString();
                EditText et_lozinka = findViewById(R.id.editPass);
                String lozinka = et_lozinka.getText().toString();
                ContentValues vrijednosti = new ContentValues();
                vrijednosti.put(Reg.COLUMN_POSTA, mail);
                vrijednosti.put(Reg.COLUMN_IME, ime);
                vrijednosti.put(Reg.COLUMN_PASSWORD, lozinka);

                long id = baza.insert(Reg.TABLE_NAME, null, vrijednosti);
                String poruka;
                if (id == -1)
                    poruka = " Nije uspjela registracija";
                else
                    poruka = "Unesen novi korisnik sa ID-om: " + id;
                Toast.makeText(this, poruka, Toast.LENGTH_LONG).show();
            } catch (SQLException ex){
                ex.printStackTrace();
                Toast.makeText(this,ex.getMessage(), Toast.LENGTH_LONG ).show();
            }
        } else if (v.getId() == R.id.btnCitaj){
            SQLiteDatabase baza = pomagac.getReadableDatabase();

            String[] kolone = new String[]{
                    Reg.COLUMN_POSTA,
                    Reg.COLUMN_IME
            };
            Cursor kursor = baza.query(
                    Reg.TABLE_NAME,
                    kolone,
                    null,
                    null,
                    null,
                    null,
                    null);
            StringBuilder graditelj = new StringBuilder();
            while(kursor.moveToNext()){
                graditelj.append(" \nmail korisnika: ");
                graditelj.append(kursor.getString(kursor.getColumnIndex(Reg.COLUMN_POSTA)));
                graditelj.append("\nKorisnik: ");
                graditelj.append(kursor.getString(kursor.getColumnIndex(Reg.COLUMN_IME)));

            }
            Toast.makeText(this, graditelj.toString(), Toast.LENGTH_LONG).show();
        }



    }

    public void naSpremi(View v){
        EditText et_mail = findViewById(R.id.editMail);
        EditText et_ime = findViewById(R.id.editName);
        EditText et_lozinka = findViewById(R.id.editPass);
        String mail = et_mail.getText().toString();
        String ime = et_ime.getText().toString();
        String lozinka = et_lozinka.getText().toString();
        ContentValues vrijednosti = new ContentValues();
        vrijednosti.put(Reg.COLUMN_POSTA, mail);
        vrijednosti.put(Reg.COLUMN_IME, ime);
        vrijednosti.put(Reg.COLUMN_PASSWORD, lozinka);
        ContentResolver resolver = getContentResolver();
        Uri noviRedUri = resolver.insert(uri, vrijednosti );
        String poruka = noviRedUri == Uri.EMPTY ? "Nije uspio unos!": noviRedUri.toString();
        Toast.makeText(this, poruka, Toast.LENGTH_SHORT).show();
    }

    public void naCitaj(View v){
        ContentResolver resolver = getContentResolver();
        String[] kolone = new String[]{
                Reg.COLUMN_POSTA,
                Reg.COLUMN_IME
        };
        Cursor cursor = resolver.query(uri, kolone,null, null, null);
        if (cursor == null) return;
        StringBuilder graditelj = new StringBuilder();
        while(cursor.moveToNext()) {
            graditelj.append(" \nmail korisnika: ");
            graditelj.append(cursor.getString(cursor.getColumnIndex(Reg.COLUMN_POSTA)));
            graditelj.append("\nKorisnik: ");
            graditelj.append(cursor.getString(cursor.getColumnIndex(Reg.COLUMN_IME)));
        }
        ((TextView)findViewById(R.id.tv_korisnici)).setText(graditelj.toString());

    }
}
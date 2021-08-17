package com.example.a12tomasik;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Kontakt> lista;
    BazaDanych baza ;
    Kontakt kontakt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lista = new ArrayList<>();
        baza = new BazaDanych(getApplicationContext());
        Insert();


    }
    BaseAdapter bs = new BaseAdapter() {
        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int i) {
            return lista.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {

                convertView = getLayoutInflater().inflate(R.layout.adblay, null);


                ImageView im1 = convertView.findViewById(R.id.imageView);
                im1.setImageResource(R.drawable.sqr);
                im1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                                int lp;
                                lp = Integer.parseInt(lista.get(i).lp);
                                baza.UsunKontakt(lp);
                                Select();


                }});
                ImageView im2 = convertView.findViewById(R.id.imageView2);
                im2.setImageResource(R.drawable.edit);
                im2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            int lp;
                            lp = Integer.parseInt(lista.get(i).lp);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Podaj tel.:");
                            final EditText input = new EditText(MainActivity.this);
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            builder.setView(input);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tel = input.getText().toString();
                                    baza.ZmienKontakt(lp, tel);
                                    Select();
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
                TextView tt = convertView.findViewById(R.id.textView);
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s %s %s %s\n", lista.get(i).lp, lista.get(i).Imie, lista.get(i).Nazwisko, lista.get(i).NR));
                tt.setText(sb);


            }
            return convertView;
        }
    };

    private void Select() {
        if (baza != null) {
            lista.clear();
            Cursor cur = baza.select(null, null);
            boolean ok = cur.moveToFirst();
            if (ok) {

                do {
                    kontakt = new Kontakt();
                    kontakt.lp = Integer.toString(cur.getInt(0));
                    kontakt.Imie = cur.getString(1);
                    kontakt.Nazwisko = cur.getString(2);
                    kontakt.NR = cur.getString(3);
                    lista.add(kontakt);
                } while (cur.moveToNext());
                cur.close();lv.setAdapter(bs);
            }
        }
    }
    Random rnd = new Random();
    private void Insert() {
        if (baza != null) {
            String tel = "";
            int j = 0;
            while (tel.length() < 11)
            {
                if (j == 3) {
                    j = -1;
                    tel += " ";
                } else{
                    tel += String.valueOf(rnd.nextInt(10));
                }
                j++;
            }
            baza.dodajKontakt("Marel", "DD", tel);
            Select();
        }
    }

    public void cccc(View view) {
        lv.setAdapter(bs);
    }


    public void Insert(View view) {
        Insert();
    }
}
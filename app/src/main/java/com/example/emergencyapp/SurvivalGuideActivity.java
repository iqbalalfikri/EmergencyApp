package com.example.emergencyapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SurvivalGuideActivity extends AppCompatActivity {

    ListView listView;
    String name[] = {"Teknik Hidup di Alam Bebas", "Hal yang Dapat Kamu Lakukan ketika Tersesat"};
    int image[] = {R.drawable.alam_bebas, R.drawable.tersesat};
    String desc[] = {"teknik hidup di alam bebas--\n" +
            "terkadang, manusia dapat berada dalam kondisi dimana ia harus bertahan hidup di alam bebas. Apa saja yang dapat dilakukan oleh mereka untuk bertahan hidup? Ada 3 teknik yang dapat dilakukan:\n" +
            "a. Beritahu orang kemana kamu akan pergi, minimal orang terdekatmu.\n" +
            "b. Jangan melawan kekuatan alam.\n" +
            "c. Membawa peralatan keselamatan."
            , "hal yang dapat kamu lakukan ketika tersesat--\n" +
            "ketika tersesat, terkadang kita bingung harus melakukan apa. Kita takut hilang, takut mengalami bencana, dan segala bahaya lainnya. Namun, untung mengantisipasi hal-hal yang tidak diinginkan, ayo lakukan hal-hal ini:\n" +
            "1. JANGAN PANIK! Tenangkan diri, berhitunglah sampai 10, minun air atau makan makanan kecil hingga pikiran kembali jernih dan dapat memikirkan jalan keluar.\n" +
            "2. Pikirkan tentang posisimu sekarang. Lihat, apakah ada pepohonan, jalan berbelok atau persimpangan? Kembalilah ke titik tersebut dan pikirkan kembali apa yang akan kau lakukan.\n" +
            "3. Perhatikan dan ingat kembali bagaimana kau bisa berada di lokasi saat ini. Ingat kembali prosesnya dan kembali ke titik awal ketika kau yakin. Tetap berada di posisi agar regu penolong dapat mencari kau lebih mudah."};
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_call);

        listView = findViewById(R.id.listView);
        MyAdapter myAdapter = new MyAdapter(this, name, image);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                alert(i);
            }
        });

    }

    private void alert(int i){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle(name[i]);

        alertDialog.setMessage(desc[i]);

        alertDialog.setCancelable(false);

        alertDialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alert = alertDialog.create();

        alert.show();
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter (Context c, String title[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);

            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);




            return row;
        }
    }

}

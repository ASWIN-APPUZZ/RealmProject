package com.example.realmproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private EditText cnameedt,cduredt,cdescedt,ctrackedt;
    private Realm realm;

    private String cname,cdur,cdesc,ctrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realm = Realm.getDefaultInstance();
        cnameedt = findViewById(R.id.c_name);
        cdescedt = findViewById(R.id.c_disc);
        cduredt = findViewById(R.id.c_dur);
        ctrackedt = findViewById(R.id.c_trk);

        Button submitbtn = findViewById(R.id.btn_add);
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cname=cnameedt.getText().toString();
                cdesc=cdescedt.getText().toString();
                cdur=cduredt.getText().toString();
                ctrack=ctrackedt.getText().toString();

                if (TextUtils.isEmpty(cname)){
                    cnameedt.setError("Please enter the Course Name");
                } else if (TextUtils.isEmpty(cdesc)){
                    cnameedt.setError("Please enter the Course Description");
                } else if(TextUtils.isEmpty(cdur)){
                    cnameedt.setError("Please enter the Course Duration");
                } else if(TextUtils.isEmpty(ctrack)){
                    cnameedt.setError("Please enter the Course Track");
                } else {
                    addDataToDatabase(cname,cdesc,cdur,ctrack);
                    Toast.makeText(MainActivity.this, "Course Added to the Database", Toast.LENGTH_SHORT).show();
                    cnameedt.setText("");
                    cduredt.setText("");
                    cdescedt.setText("");
                    ctrackedt.setText("");
                }
            }
        });
    }

    private void addDataToDatabase(String cname, String cdesc, String cdur, String ctrack) {
        DataModel model = new DataModel();
        Number id = realm.where(DataModel.class).max("id");
        long nextId = 1;

        if (id==null){
            nextId=1;
        }else {
            nextId=id.intValue()+1;
        }
        model.setId(nextId);
        model.setC_name(cname);
        model.setC_desc(cdesc);
        model.setC_dur(cdur);
        model.setC_track(ctrack);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(model);
            }
        });
    }
}

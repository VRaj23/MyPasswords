package varadraj.mypasswords;


import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;
import varadraj.model.Record;

/**
 * Created by varad on 2/6/17.
 */

public class HomeActivity extends AppCompatActivity implements OnRecordClickListener,OnDataChangeListener{

    private RecyclerView recyclerView;
    private RecordAdapter adapter;
    private Realm realm;
    private  OnDataChangeListener d;
    public  static  final Parcelable.Creator<HomeActivity> CREATOR = new Parcelable.Creator<HomeActivity>(){
        @Override
        public HomeActivity createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public HomeActivity[] newArray(int i) {
            return new HomeActivity[0];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeactivity);
        realm = Realm.getDefaultInstance();
        recyclerView = (RecyclerView)findViewById(R.id.rv_home);
        adapter = new RecordAdapter(this,getAllRecords(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        d = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public RealmResults<Record> getAllRecords(){
         RealmResults<Record> results = realm.where(Record.class).findAll();
         return  results;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuinflater = getMenuInflater();
        menuinflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_add:{//TODO add fab
                FragmentManager fm = getFragmentManager();
                AddRecordFragment addR = new AddRecordFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("parcel",d);
                addR.setArguments(bundle);
                addR.show(fm,"Add New Record");
                break;
            }
            case R.id.menu_settings:{
                Intent i = new Intent(this,Settings.class);
                startActivity(i);
                finish();
                break;
            }

        }
        return true;
    }

    @Override
    public void onBackPressed() {
          new AlertDialog.Builder(this)
                .setMessage("Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No",null)
                .show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    public void onRecordClick(Record r) {
        android.app.FragmentManager fm = getFragmentManager();
        ViewPassword vp = new ViewPassword();
        Bundle bundle = new Bundle();
        bundle.putString("description",r.getDescription());
        bundle.putString("username",r.getUsername());
        bundle.putString("password",r.getPassword());
        bundle.putParcelable("deleteListener",d);
        vp.setArguments(bundle);
        vp.show(fm,r.getDescription());
    }

    @Override
    public void onDataChanged() {
        adapter = new RecordAdapter(this,getAllRecords(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}

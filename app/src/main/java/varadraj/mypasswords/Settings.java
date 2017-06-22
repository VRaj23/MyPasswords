package varadraj.mypasswords;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmResults;
import varadraj.model.AppPassword;

/**
 * Created by varad on 5/6/17.
 */

public class Settings extends Activity implements View.OnClickListener {

    EditText et_SetPassword1, et_SetPassword2;
    Button savePassword;
    private Realm realm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        realm = Realm.getDefaultInstance();
        savePassword = (Button)findViewById(R.id.bt_savepassword);
        savePassword.setOnClickListener(this);
        et_SetPassword1 = (EditText)findViewById(R.id.et_setpassword1);
        et_SetPassword2 = (EditText)findViewById(R.id.et_setpassword2);
    }

    @Override
    public void onBackPressed() {
        if(passwordExists()){
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
            finish();
        }
        else{
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private  boolean passwordExists(){
        AppPassword ap = realm.where(AppPassword.class).findFirst();
        realm.close();
        if (ap == null)
            return false;
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_savepassword){ //TODO instead of savepassword and use it as encryption key
            if (et_SetPassword1.getText().toString().trim().equals(et_SetPassword2.getText().toString().trim())){
                Realm realm = Realm.getDefaultInstance();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //TODO password rules
                        realm.where(AppPassword.class).findAll().deleteAllFromRealm();
                        AppPassword ap = realm.createObject(AppPassword.class);
                        ap.setAppPassword(et_SetPassword1.getText().toString().trim());

                    }
                });
                realm.close();
                Toast.makeText(this,"Master Password Set",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show();

            }
        }
    }
}

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

public class LoginActivity extends Activity implements View.OnClickListener {

    EditText loginPassword;
    Button login;
    Realm realm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm = Realm.getDefaultInstance();

        if (!passwordExists()){
            Intent i = new Intent(this,Settings.class);
            startActivity(i);
            finish();
        }
        setContentView(R.layout.activity_login);
        loginPassword = (EditText)findViewById(R.id.et_loginpassword);
        login = (Button)findViewById(R.id.bt_login);
        login.setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private boolean passwordExists(){
        AppPassword ap = realm.where(AppPassword.class).findFirst();
        if(ap == null)
            return false;
        else
            return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_login){
            RealmResults<AppPassword> result = realm.where(AppPassword.class).findAll();
            if(result.get(0).getAppPassword().equals(loginPassword.getText().toString())){
                Intent i = new Intent(this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Toast.makeText(this,"Wrong Password",Toast.LENGTH_SHORT).show();
                loginPassword.setText("");
                loginPassword.setHint("Re-enter Password");
            }
        }
    }
}

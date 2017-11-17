package mchehab.com.permissionsdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {

    private String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission
            .WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rxPermissions = new RxPermissions(this);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(e-> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestPermissions(){
        rxPermissions.request(permissions)
            .subscribe(granted -> {
                if(granted){
    //                        code goes here
                }else{
                    for(String permission : permissions){
                        if(shouldShowRequestPermissionRationale(permission)){
                            new AlertDialog.Builder(this)
                                    .setTitle("Error")
                                    .setMessage("Your message here")
                                    .setPositiveButton("Allow", (dialog, which) -> requestPermissions())
                                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                                    .create()
                                    .show();
                            return;
                        }
                    }
                }
            });
    }
}
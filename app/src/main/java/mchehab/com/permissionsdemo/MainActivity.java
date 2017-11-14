package mchehab.com.permissionsdemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(e-> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED){
                    writeToExternalStorage();
                }else{
                    requestWriteExternalStoragePermission();
                }
            }
        });
    }

    private void requestWriteExternalStoragePermission(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 101);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 101){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                writeToExternalStorage();
            }else{
                if(shouldShowRequestPermissionRationale(permissions[0])){
                    new AlertDialog.Builder(this)
                            .setMessage("Your error message here")
                            .setPositiveButton("Allow", (dialog, which) -> requestWriteExternalStoragePermission())
                            .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                            .create()
                            .show();
                }
            }
        }
    }

    private void writeToExternalStorage(){
        File root = Environment.getExternalStorageDirectory();
        File directory = new File(root.getAbsolutePath() + "/pathThatYouWant");
        if(!directory.exists())
            directory.mkdirs();
        File file = new File(directory, "fileName.txt");
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("this is my text here");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
package com.example.smarteping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private Button login, registrar, cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login= findViewById(R.id.login);
        registrar= findViewById(R.id.registrar);
        cerrarsesion= findViewById(R.id.cerrarsesion);

        auth= FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            login.setVisibility(View.GONE);
            registrar.setVisibility(View.GONE);
            cerrarsesion.setVisibility(View.VISIBLE);
        }
    }

    public void iniciar(View view) {
        Intent i = new Intent(this, loginActivity.class);
        startActivity(i);
    }

    public void registrar (View view) {
        Intent i = new Intent(this, registrarActivity.class);
        startActivity(i);
    }

    public void Gonoticias(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elespectador.com/ambiente/blog-el-rio/"));
        startActivity(i);
    }

    public void Goconsejos(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elespectador.com/ambiente/blog-el-rio/"));
        startActivity(i);
    }

    public void Golibros(View view){
        if(auth.getCurrentUser()!=null){
            Intent i = new Intent(this, librosActivity.class);
            startActivity(i);
        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Debes registrate o iniciar sesion para ver el contenido");
            builder1.setCancelable(true);
            builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
    }

    public void Gorazas(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elespectador.com/ambiente/blog-el-rio/"));
        startActivity(i);
    }

    public void Gotipos(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.elespectador.com/ambiente/blog-el-rio/"));
        startActivity(i);
    }

    public void Goclose(View view){
        auth.signOut();
        if(auth.getCurrentUser()==null){
            login.setVisibility(View.VISIBLE);
            registrar.setVisibility(View.VISIBLE);
            cerrarsesion.setVisibility(View.GONE);
        }
    }

    public void Goventa(View view){
        if(auth.getCurrentUser()!=null){
            Intent i = new Intent(this, ventasActivity.class);
            startActivity(i);
        }else{
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setMessage("Debes registrate o iniciar sesion para acceder a esta funcion");
            builder1.setCancelable(true);
            builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert1 = builder1.create();
            alert1.show();
        }
    }
}
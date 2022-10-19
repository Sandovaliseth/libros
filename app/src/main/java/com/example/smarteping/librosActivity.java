package com.example.smarteping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Timer;

public class librosActivity extends AppCompatActivity {

    private ListView listviewlib;
    private ArrayList<String> libros;
    private StorageReference mstorageRef;

    private InterstitialAd mInterstitialAd;
    private String TAG;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libros);

        progressBar= findViewById(R.id.progressBar);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-1107118951929383/2716256193", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });


        listviewlib= findViewById(R.id.listviewlibros);
        libros= new ArrayList<>();

        //inicializar el objeto de firebase storage
        mstorageRef= FirebaseStorage.getInstance().getReference();

        //traer los datos del bucket
        StorageReference ref= mstorageRef.child("libros");
        ref.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                Log.e("libros", "entrando a recuperrar libros");
                for (StorageReference item : listResult.getItems()) {
                    libros.add(item.getName() + "");
                    Log.e("libro: --->>>", item.getPath() + "..." + item.getName());
                }

                progressBar.setVisibility(View.GONE);

                //configurar adaptador de la lista
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, libros);
                listviewlib.setAdapter(adapter);

                listviewlib.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(librosActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }

                        final String titulo_libro_seleccionado = listviewlib.getItemAtPosition(position).toString();
                        Intent i = new Intent(getApplicationContext(), VisorPDFActivity.class);
                        i.putExtra("TITULO LIBRO", titulo_libro_seleccionado);
                        startActivity(i);
                    }
                  });
                 }
                }).addOnFailureListener((e) -> {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(librosActivity.this);
                    builder1.setMessage("Ha ocurrido un error al cargar los libros, Por favor revise la conexion a internet");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert1 = builder1.create();
                    alert1.show();
            });

        /* libros.add("Libros 1");
        libros.add("Libros 2");
        libros.add("Libros 3");
        libros.add("Libros 4");
        libros.add("Libros 5");

        ArrayAdapter <String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,libros);
        listviewlib.setAdapter(adapter); */
    }
}
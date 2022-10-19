package com.example.smarteping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarteping.modelo.registrokilosdiarios;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

public class ventasActivity extends AppCompatActivity {

    private CalendarView calendarview;
    private TextView ventaKilos, fecha_kilos;
    private EditText edit_ventakilos;
    private Button botonguardar;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas);

        calendarview = findViewById(R.id.calendarView);
        ventaKilos = findViewById(R.id.ventaKilos);
        fecha_kilos = findViewById(R.id.fecha_kilos);
        edit_ventakilos = findViewById(R.id.edit_ventakilos);
        botonguardar= findViewById(R.id.btn_guardarkilos);

        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("registro_kilos");

        calendarview.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofmonth) {
                String fechaseleccionada = dayofmonth+"-"+month+"-"+year;
                myRef.child(auth.getCurrentUser().getUid()).child(fechaseleccionada).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        registrokilosdiarios registrokilosdiarios_temp = dataSnapshot.getValue(registrokilosdiarios.class);
                        if(registrokilosdiarios_temp!=null){
                            ventaKilos.setText(registrokilosdiarios_temp.getKilosproducidos()+"");
                            fecha_kilos.setText(registrokilosdiarios_temp.getFechaDia());
                        }else{
                            fecha_kilos.setText(fechaseleccionada);
                            ventaKilos.setText(0+"");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                fecha_kilos.setText(fechaseleccionada);
                ventaKilos.setText(edit_ventakilos.getText());
            }
        });

        botonguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarkilosdiarios(fecha_kilos.getText().toString(), Double.parseDouble(edit_ventakilos.getText().toString()+""));
            }
        });
    }

    public void guardarkilosdiarios (String fecha, Double kilos){
        registrokilosdiarios registrokilosdiarios = new registrokilosdiarios(fecha, kilos);
        if(auth.getCurrentUser()!=null){
            myRef.child(auth.getCurrentUser().getUid()).child(fecha).setValue(registrokilosdiarios).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void avoid) {
                    Toast.makeText(ventasActivity.this,"Se ha guardado correctamente", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ventasActivity.this,"Erros, no se ha podido guardar", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
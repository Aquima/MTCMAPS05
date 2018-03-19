package com.mtc.transportes.mtcmaps;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

import API.Model.AddressComponent;
import API.Model.Districts;
import API.Model.Result;
import API.Service.ApiClient;

import java.sql.Connection;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    TextView textDistrict;
    RecyclerView recyclerView;
    Button btnMapas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMapas = findViewById(R.id.btnMaps);
        textDistrict = findViewById(R.id.editTextDistrict);
        recyclerView = findViewById(R.id.rvAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        btnMapas.setVisibility(View.INVISIBLE);
       // this.getPermmission();
     }

    public void getAddressFromDistrict(View view){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getString(R.string.url_base))
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        ApiClient client = retrofit.create(ApiClient.class);

        String param = textDistrict.getText().toString();
        String apikey = getString(R.string.API_KEY);

        Call<Districts> call = client.addressForDistrict(param,apikey);
        String urlConcat = call.request().url().toString();
        call.enqueue(new Callback<Districts>() {
            @Override
            public void onResponse(Call<Districts> call, Response<Districts> response) {
                Districts result = response.body();
                List<Result> res = result.getResults();
                if (res.size() > 0) {
                    List<AddressComponent> address = res.get(0).getAddressComponents();
                    AdapterAddress adapter = new AdapterAddress(address,HomeActivity.this);
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<Districts> call, Throwable t) {
                Toast.makeText(HomeActivity.this,"Houston i have a problem",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void goToMaps(View view){
        Intent intent = new Intent(HomeActivity.this,MapsActivity.class);
        startActivity(intent);
    }//com.android.support:support-v4:23.0.0
    public void getPermmission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            // permission has been granted, continue as usual
            btnMapas.setVisibility(View.VISIBLE);
            btnMapas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this,MapsMTCActivity.class);
                    HomeActivity.this.startActivity(intent);
                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        2);
            } else {
                // Permission was denied. Display an error message.
                this.getPermmission();
            }
        }
    }
}

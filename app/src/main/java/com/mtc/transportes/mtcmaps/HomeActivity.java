package com.mtc.transportes.mtcmaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import API.Model.AddressComponent;
import API.Model.Districts;
import API.Model.Result;
import API.Service.ApiClient;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    TextView textDistrict;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textDistrict = findViewById(R.id.editTextDistrict);
        recyclerView = findViewById(R.id.rvAddress);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
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

}

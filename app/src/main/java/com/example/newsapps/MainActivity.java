package com.example.newsapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    SearchView searchViewid;
    private RecyclerView recyclerView;
    ProgressBar progressBar;
    private ProductAdapter adapter;
    Button myoder;
    ImageSlider imageSlider;

    private BottomNavigationView bottomNavigationView;

    private ArrayList<ProductModel> courseModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageSlider =findViewById(R.id.image_slider);

        ArrayList<SlideModel> imageList = new ArrayList<>();


        imageList.add(new SlideModel(R.drawable.two, ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.one,ScaleTypes.FIT));
        imageList.add(new SlideModel(R.drawable.th,ScaleTypes.FIT));

        imageSlider.setImageList(imageList);


        progressBar = findViewById(R.id.progressBar2);
        recyclerView = findViewById(R.id.recyclerview);
        searchViewid = findViewById(R.id.searchViewt);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new ProductAdapter(courseModelArrayList, MainActivity.this);

        recyclerView.setAdapter(adapter);

        searchViewid.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        String url = "https://hanirrohan785845.000webhostapp.com/newsapi.json";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Log.d("hanif", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String imageUrl = jsonObject.getString("thumbnail");

                        ProductModel product = new ProductModel(title, description,imageUrl);
                        courseModelArrayList.add(product);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                adapter.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void filter(String text) {
        ArrayList<ProductModel> filteredlist = new ArrayList<>();

        for (ProductModel item : courseModelArrayList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }



}

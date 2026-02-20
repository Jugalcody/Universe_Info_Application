package com.example.roverinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class Home extends AppCompatActivity {

    RecyclerView rec;
    ProgressBar p2;
    ImageView sicon;
    EditText title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rec=findViewById(R.id.rec);
        p2=findViewById(R.id.pBar2);
        title=findViewById(R.id.sbar);
        sicon=findViewById(R.id.sicon);

        title.setText("asteroid");

        getWindow().setStatusBarColor(getColor(R.color.main));
        title.setOnEditorActionListener((v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE) {

                        loadData(title.getText().toString().trim());
                        return true;
                    }
                    return false;
                });
        loadData(title.getText().toString().trim());

        sicon.setOnClickListener(v -> loadData(title.getText().toString().trim()));

    }
    private void loadData(String key){
        rec.setVisibility(View.GONE);
        String url="https://images-api.nasa.gov/search?q="+key+"&media_type=image";
        p2.setVisibility(View.VISIBLE);

        JsonObjectRequest request=new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {

                    try{
                        rec.setVisibility(View.VISIBLE);
                        p2.setVisibility(View.GONE);
                        JSONObject collection=response.getJSONObject("collection");
                        JSONArray items=collection.getJSONArray("items");

                        String[][] arr=new String[items.length()][3];

                        for(int i=0;i<items.length();i++){

                            JSONObject item=items.getJSONObject(i);

                            /* NAME + DESCRIPTION */
                            JSONObject data=item.getJSONArray("data").getJSONObject(0);

                            arr[i][0]=data.getString("title");
                            arr[i][1]=data.getString("description");

                            /* IMAGE */
                            if(item.has("links")){
                                JSONArray links=item.getJSONArray("links");
                                arr[i][2]=links.getJSONObject(0).getString("href");
                            }else{
                                arr[i][2]="";
                            }
                        }

                        rec.setAdapter(new AdapterImage(arr,this));
                        rec.setVisibility(View.VISIBLE);
                        p2.setVisibility(View.GONE);

                    }
                    catch(Exception e){
                        p2.setVisibility(View.GONE);
                        Toast.makeText(Home.this,"Parsing Error",Toast.LENGTH_LONG).show();
                    }

                },
                error -> {
                    p2.setVisibility(View.GONE);
                    Toast.makeText(Home.this,"API Error",Toast.LENGTH_LONG).show();
                });

        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(request);
    }
}
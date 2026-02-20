package com.example.roverinfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class AdapterImage extends RecyclerView.Adapter<AdapterImage.Holder>{

    String arr[][];
    Context c;

    public AdapterImage(String[][] arr, Context c){
        this.arr=arr;
        this.c=c;
    }

    class Holder extends RecyclerView.ViewHolder{

        TextView title,desc;
        ImageView img;

        public Holder(View v){
            super(v);

            title=v.findViewById(R.id.title);
            desc=v.findViewById(R.id.desc);
            img=v.findViewById(R.id.img);
        }
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(c)
                .inflate(R.layout.rover_row,parent,false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h,int position){

        h.title.setText(arr[position][0]);
        h.desc.setText(arr[position][1]);

        Picasso.get()
                .load(arr[position][2])
                .into(h.img);

        /* CLICK EVENT */
        h.itemView.setOnClickListener(v -> {

            Intent i=new Intent(c,Camera2.class);

            i.putExtra("title",arr[position][0]);
            i.putExtra("desc",arr[position][1]);
            i.putExtra("img",arr[position][2]);

            c.startActivity(i);
        });
    }

    @Override
    public int getItemCount(){
        return arr.length;
    }
}
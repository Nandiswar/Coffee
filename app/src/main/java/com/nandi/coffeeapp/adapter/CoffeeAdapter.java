package com.nandi.coffeeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nandi.coffeeapp.DetailsActivity;
import com.nandi.coffeeapp.R;
import com.nandi.coffeeapp.model.Coffee;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nandi_000 on 10-11-2015.
 */
public class CoffeeAdapter extends ArrayAdapter<Coffee> implements AdapterView.OnItemClickListener {

    private Context context;
    private List<Coffee> coffees;
    private final int ROW_LAYOUT_ID;

    public CoffeeAdapter(Context context, int resource, List<Coffee> coffeeList) {
        super(context, resource, coffeeList);
        this.context = context;
        coffees = coffeeList;
        ROW_LAYOUT_ID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(ROW_LAYOUT_ID, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Coffee coffee = coffees.get(position);
        holder.coffeeNameView.setText(coffee.name);
        holder.coffeeDescView.setText(coffee.desc);
        String imgUrl = coffee.image_url;
        if(!imgUrl.equals("")) {
            imgUrl = imgUrl.replace("http://", "https://");
            Uri uri = Uri.parse(imgUrl);
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(uri).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        /*holder.detailsClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Coffee coffee = coffees.get(position);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("coffeeId", coffee.id);
        intent.putExtra("coffeeImgUrl", coffee.image_url);
        context.startActivity(intent);
    }

    class ViewHolder {
        TextView coffeeNameView;
        TextView coffeeDescView;
        ImageView imageView;
        ImageView detailsClick;

        public ViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.coffeeImage);
            coffeeNameView = (TextView) convertView.findViewById(R.id.coffeName);
            coffeeDescView = (TextView) convertView.findViewById(R.id.coffeeDesc);
            detailsClick = (ImageView) convertView.findViewById(R.id.nextClick);
        }
    }
}

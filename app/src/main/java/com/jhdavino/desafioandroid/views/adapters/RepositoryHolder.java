package com.jhdavino.desafioandroid.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jhdavino.desafioandroid.R;
import com.jhdavino.desafioandroid.models.Repository;
import com.jhdavino.desafioandroid.views.MainActivity;

/**
 * Created by josehenrique on 31/10/17.
 */

public class RepositoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    private TextView name;
    private TextView description;
    private TextView full_name;
    private TextView username;
    private ImageView image;
    private TextView num_fork;
    private TextView num_start;
    private Repository repository;

    public RepositoryHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.repository_list_item, parent, false));

        context = itemView.getContext();

        itemView.setOnClickListener(this);

        name = (TextView) itemView.findViewById(R.id.text_title);
        description = (TextView) itemView.findViewById(R.id.text_body);
        image = (ImageView) itemView.findViewById(R.id.image_author);
        username = (TextView) itemView.findViewById(R.id.text_user_name);
        full_name = (TextView) itemView.findViewById(R.id.text_full_name);
        num_start = (TextView) itemView.findViewById(R.id.text_num_start);
        num_fork = (TextView) itemView.findViewById(R.id.text_num_fork);
    }

    protected void setValues(Repository repo) {
        //get current repository
        this.repository = repo;

        name.setText(this.repository.getName());
        description.setText(this.repository.getDescription());
        full_name.setText(this.repository.getFull_name());
        username.setText(this.repository.getUsername());
        Glide.with(context).load(this.repository.getUrl_img()).apply(RequestOptions.circleCropTransform()).into(image);
        num_fork.setText(String.valueOf(this.repository.getNum_fork()));
        num_start.setText(String.valueOf(this.repository.getNum_start()));
    }

    @Override
    public void onClick(View view) {
        MainActivity.openDetailsRepository(context, repository);
    }
}

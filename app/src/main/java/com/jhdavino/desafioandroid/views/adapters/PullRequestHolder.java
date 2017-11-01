package com.jhdavino.desafioandroid.views.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jhdavino.desafioandroid.R;
import com.jhdavino.desafioandroid.models.PullItem;

/**
 * Created by josehenrique on 31/10/17.
 */


public class PullRequestHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;

    private TextView title;
    private TextView body;
    private TextView created_at;
    private TextView login;
    private ImageView image_author;
    private TextView full_name;
    private PullItem item;

    public PullRequestHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.pull_requests_list_item, parent, false));

        context = itemView.getContext();

        itemView.setOnClickListener(this);

        login = (TextView) itemView.findViewById(R.id.text_author);
        image_author = (ImageView) itemView.findViewById(R.id.image_author);
        title = (TextView) itemView.findViewById(R.id.text_title);
        created_at = (TextView) itemView.findViewById(R.id.text_created_at);
        body = (TextView) itemView.findViewById(R.id.text_body);
    }

    public void bind(PullItem pull, int opened, int closed) {
        this.item = pull;

        login.setText(this.item.getLogin());
        Glide.with(context).load(this.item.getAvatar_url()).apply(RequestOptions.circleCropTransform()).into(image_author);
        title.setText(this.item.getTitle());
        created_at.setText(pull.getCreated_at());
        body.setText(this.item.getBody());
    }

    @Override
    public void onClick(View view) {

        //Abrir navegador
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtml_url()));
        context.startActivity(browserIntent);

    }
}

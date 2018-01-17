package com.desmistificando.app_beta;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.List;

/**
 * Created by Igor Stefani Buttarello on 12/14/17.
 */

public class ArticleHeaderAdapter extends RecyclerView.Adapter<
        ArticleHeaderAdapter.ArticleHeaderViewHolder> {

    private Context context;
    private List<ArticleHeader> articleHeaderList;

    ArticleHeaderAdapter(Context context, List<ArticleHeader> list){
        this.context = context;
        this.articleHeaderList = list;
    }

    @Override
    public ArticleHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.article_card, parent,
                false);
        ArticleHeaderViewHolder articleHeaderViewHolder = new ArticleHeaderViewHolder(view);
        return articleHeaderViewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleHeaderViewHolder holder, int pos) {
        final ArticleHeader articleHeader = this.articleHeaderList.get(pos);
        holder.title.setText(articleHeader.getTitle());
        holder.description.setText(articleHeader.getDescription());
        holder.authorName.setText(articleHeader.getAuthorName());
        Glide.with(context).load(articleHeader.getThumbURL()).into(holder.authorThumb);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent readArticleIntent = new Intent(context, ShowArticleActivity.class);
                readArticleIntent.putExtra(context.getString(R.string.EXTRA_ARTICLE_LINK),
                        articleHeader.getLink());
                context.startActivity(readArticleIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.articleHeaderList.size();
    }

    public static class ArticleHeaderViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{
        public TextView title, description, authorName;
        public ImageView authorThumb;
        public CardView cardView;

        ArticleHeaderViewHolder(View itemView){
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.card_articleTitleTextView);
            description = (TextView)itemView.findViewById(R.id.card_articleDescriptionTextView);
            authorName = (TextView)itemView.findViewById(R.id.card_articleAuthorNameTextView);
            authorThumb = (ImageView)itemView.findViewById(R.id.card_authorThumbImageView);
            cardView = (CardView)itemView.findViewById(R.id.articleCardView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){

        }

    }

}

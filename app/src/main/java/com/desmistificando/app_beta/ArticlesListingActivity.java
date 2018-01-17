package com.desmistificando.app_beta;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticlesListingActivity extends Activity {

    StringBuilder articleLink = new StringBuilder();
    StringBuilder authorName = new StringBuilder();
    StringBuilder authorThumbURL = new StringBuilder();
    StringBuilder articleDesc = new StringBuilder();
    StringBuilder articleTitle = new StringBuilder();
    List<ArticleHeader> articlesHeadersList = new ArrayList<>();
    Activity thisActivity;
    RecyclerView articlesRecyclerView;
    ArticleHeaderAdapter articleHeaderAdapter;
//    WebView scrapingWebView;
//    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_listing);
        thisActivity = this;
//        txtView = findViewById(R.id.textView);
        articlesRecyclerView = findViewById(R.id.articlesRecyclerView);
        articlesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleHeaderAdapter = new ArticleHeaderAdapter(this, articlesHeadersList);
        articlesRecyclerView.setAdapter(articleHeaderAdapter);
        loadArticlesHeaders();

//        scrapingWebView = new WebView(this);
//        scrapingWebView.getSettings().setJavaScriptEnabled(true);
//        scrapingWebView.addJavascriptInterface(new JSInterface(), "JSInterface");
//        scrapingWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url){
//                scrapingWebView.loadUrl("javascript:window.JSInterface.handleHTML(document.documentElement.outerHTML)");
//            }
//        });
//        scrapingWebView.loadUrl("https://www.jusbrasil.com.br/artigos");

    }

    private void loadArticlesHeaders(){

        AsyncLoadArticlesHeadersData loadArticlesHeadersDataTask = new AsyncLoadArticlesHeadersData();
        loadArticlesHeadersDataTask.execute();

    }

//    public void readArticle(View view){
//
//        Intent readArticleIntent = new Intent(this, ShowArticleActivity.class);
//        readArticleIntent.putExtra(EXTRA_ARTICLE_LINK, articleLink.toString());
//        startActivity(readArticleIntent);
//
//    }

//    private class JSInterface {
//
//        @JavascriptInterface
//        public void handleHTML(String html){
//            Document doc = Jsoup.parse(html);
//            txtView.setText(html);
//        }
//
//    }

    private class AsyncLoadArticlesHeadersData extends AsyncTask<Void, Void, Void>{

        protected Void doInBackground(Void... arg){

            try {
                ArticleHeader articleHeader;
                Document doc = Jsoup.connect("https://www.jusbrasil.com.br/artigos").get();
                Elements articles =
                        doc.select("div.FeedDocumentCard.card.FeedDocumentCardList-item");

                for(Element article : articles){
                    if(article.childNodeSize() > 0) {
                        //Clear all strings
                        authorName.setLength(0);
                        authorThumbURL.setLength(0);
                        articleTitle.setLength(0);
                        articleDesc.setLength(0);
                        articleLink.setLength(0);
                        //-----------------------

                        //Find the author's name within html document
                        authorName.append(article
                                .select("span.DocumentInfo-publisherName").text());
                        //-----------------------

                        //Find article's title within html document
                        articleTitle.append(article
                                .select("h1.FeedDocumentCard-title.document-title").text());
                        //-----------------------

                        //Find article's description within html document
                        articleDesc.append(article
                                .select("article.FeedDocumentCard-description.document-content")
                                .text());
                        //-----------------------

                        try {
                            //Find the link to the article within html document
                            articleLink.append("https:");
                            articleLink.append(article
                                    .selectFirst("a.FeedDocumentCard-link").attr("href"));
                            //-----------------------

                            //Find URL of the author's thumbnail
                            authorThumbURL.append(article
                                    .select("img.avatar.avatar--circle")
                                    .attr("data-src"));
                            //replacing the 32x32 part of the URL path with 400x400
                            //will lead to the author's profile picture
                            //which has a better resolution
                            int initPos = authorThumbURL.indexOf("32x32");
                            authorThumbURL.replace(initPos, (initPos + 5), "400x400");
                            //-----------------------

                            articleHeader = new ArticleHeader(articleTitle.toString(),
                                    articleDesc.toString(),
                                    authorName.toString(),
                                    authorThumbURL.toString(),
                                    articleLink.toString());

                            articlesHeadersList.add(articleHeader);
                        } catch (Exception e) {
                            Log.e(getClass().getName(), "Error adding article header to" +
                                    " headers list: " + e.getMessage());
                        }
                    }

                }

            }catch(IOException e){
                Log.e(getClass().getName(), "Error running background async task: "
                        + e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void v){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    articleHeaderAdapter.notifyDataSetChanged();
                }
            });
        }

    }
}

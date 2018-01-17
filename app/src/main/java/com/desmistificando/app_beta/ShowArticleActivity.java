package com.desmistificando.app_beta;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class ShowArticleActivity extends AppCompatActivity {

    String articleURL;
    WebView articleWebView;
    Document articleDoc;
    WebResourceResponse resp;
    WebResourceRequest req;
    TextView textViewArticle;
//    JSONObject jsonPayload;
//    JSONObject jsonResponse = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_article);
//        try{
//            jsonPayload = new JSONObject("{'id':'q0','query':'query RootContainerProps {root {...Fy}} fragment F0 on Profile {pid,url,fullname,username,createdAt,profileType,user {email,id},plans {price,presentationName},id} fragment F1 on Profile {pid,fullname,thumborAvatar {_thumbUrl2nA9ir:thumbUrl(size:\\\"16x16\\\"),_thumbUrlxTPux:thumbUrl(size:\\\"24x24\\\"),_thumbUrl3amDF7:thumbUrl(size:\\\"32x32\\\"),_thumbUrl1psVFm:thumbUrl(size:\\\"48x48\\\"),_thumbUrl2vvZuZ:thumbUrl(size:\\\"96x96\\\"),_thumbUrl2hAscG:thumbUrl(size:\\\"192x192\\\")},occupation {title,id},id} fragment F2 on Profile {fullname,url,user {isAdmin,id},id,...F1} fragment F3 on Profile {url,user {_profiles3PuLJE:profiles(first:3) {pageInfo {hasNextPage,hasPreviousPage},edges {node {fullname,url,isActive,id,...F1},cursor}},id},id} fragment F4 on Profile {id,...F2,...F3} fragment F5 on Profile {user {isAdmin,id},id} fragment F6 on Profile {id,...F5} fragment F7 on Profile {pid,id,...F6} fragment F8 on Profile {pid,id} fragment F9 on Profile {id,...F8} fragment Fa on Profile {id,...F9} fragment Fb on Profile {user {isAdmin,id},id,...F7,...F8,...Fa,...F9} fragment Fc on Profile {pid,id,...Fb} fragment Fd on Profile {sponsoredTopicsId,occupation {tid,id},id} fragment Fe on Profile {id,...Fc,...Fd} fragment Ff on Profile {hasFeed,id,...F4,...Fe} fragment Fg on Profile {pid,isLawyer,id} fragment Fh on Document {date,publisher {pid,fullname,url,id,...F1},id} fragment Fi on Document {id,docId,artifact,viewerLiked,votesAmount} fragment Fj on Document {votesAmount,negativeVotesAmount,id} fragment Fk on Document {votesAmount,id,...Fj} fragment Fl on Document {docId,artifact,viewerLiked,votesAmount,id,...Fi,...Fk} fragment Fm on Document {id,docId,artifact,viewerSaved} fragment Fn on Document {viewerSaved,docId,artifact,id,...Fm} fragment Fo on Document {id,docId,artifact} fragment Fp on Document {docId,artifact,id} fragment Fq on Document {url,id,...Fp} fragment Fr on Document {url,title,id,...Fp} fragment Fs on Document {id,...Fq,...Fr} fragment Ft on Document {url,editUrl,publisher {isViewer,id},viewerDisliked,numComments,artifact,id,...Fl,...Fi,...Fn,...Fo,...Fs,...Fq,...Fr} fragment Fu on Document {artifact,docId,title,description,images {path},url,id,...Fh,...Ft} fragment Fv on Feed {_hotDocuments1DwpEX:hotDocuments(first:10) {pageInfo {hasNextPage,hasPreviousPage},edges {node {id,...Fu},cursor}}} fragment Fw on Feed {...Fv} fragment Fx on Topic {id} fragment Fy on RootApi {me {pid,id,...F0,...Ff,...F8,...Fg},_feed1RXpqC:feed(artifact:\\\"ARTIGO\\\") @include(if:true) {...Fw},_topic1ffAhA:topic(tid:26547120) @skip(if:false) {id,...Fx}}','variables':{}}");
//        }catch(Exception e) {
//            jsonPayload = new JSONObject();
//        }

//        final RequestQueue queue = Volley.newRequestQueue(this);

        textViewArticle = findViewById(R.id.textViewArticle);

        articleWebView = findViewById(R.id.articleViewer_webView);
        articleWebView.getSettings().setJavaScriptEnabled(true);

        articleWebView.addJavascriptInterface(new JSInterface(), "jsInterface");
//        articleWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);

//                view.loadUrl("javascript:(function(){" +
//                        "       if('serviceWorker' in navigator){" +
//                        "           navigator.serviceWorker.register('/home/igao/AndroidStudioProjects/app_beta/app/sw.js')" +
//                        "               .then(function(reg){" +
//                        "                   console.log('Service Worker registration succeeded!!!. Scope is: ' + reg.scope);" +
//                        "               }).catch(function(error){" +
//                        "                   console.log('Service Worker registration failed :( with error: ' + error);" +
//                        "               });" +
//                        "           if(navigator.serviceWorker.controller){" +
//                        "               console.log('This page is controlled by: ' + navigator.serviceWorker.controller);" +
//                        "           }else{" +
//                        "               console.log('No service worker controlling this page!');" +
//                        "           }" +
//                        "       }" +
//                        "   }"+
//                        ")();");

//                view.loadUrl("javascript:(function(send){" +
//                        "       XMLHttpRequest.prototype.send = function(body){" +
//                        "           jsInterface.write();" +
//                        "           console.log('JSIF XMLHttpRequest.send => ' + body);" +
//                        "           send.call(body);" +
//                        "       };" +
//                        "   }"+
//                        ")(XMLHttpRequest.prototype.send);");
//
//
//                view.loadUrl("javascript:(function(open){" +
//                        "       XMLHttpRequest.prototype.open = function(method, url){" +
//                        "           this.addEventListener('readystatechange', function(){" +
//                        "                   if(navigator.serviceWorker.controller){" +
//                        "                       console.log('This page is controlled by: ' + navigator.serviceWorker.controller);" +
//                        "                   } else{" +
//                        "                       console.log('No service worker controlling this page!');" +
//                        "                   }" +
//                        "                   jsInterface.write();" +
//                        "                   console.log('JSIF XMLHttpRequest.open => ' + 'method: ' + method + ', URL: ' + url + ', RESP: ' + this.responseText);" +
//                        "           }, false);" +
//                        "           open.call(this, method, url);" +
//                        "       };" +
//                        "   }"+
//                        ")(XMLHttpRequest.prototype.open);");
//
//                view.loadUrl("javascript:(function(setRequestHeader){" +
//                        "       XMLHttpRequest.prototype.setRequestHeader = function(header, value){" +
//                        "           jsInterface.write();" +
//                        "           console.log('JSIF XMLHttpRequest.setRequesHeader => ' + 'Header = ' + header + ' Value = ' + value);" +
//                        "           setRequestHeader.call(this, header, value);" +
//                        "       };" +
//                        "   }"+
//                        ")(XMLHttpRequest.prototype.setRequestHeader);");
//            }

//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
//                if(request.getUrl().toString().equals("https://www.jusbrasil.com.br/feed/graphql")) {
//
//                    JsonObjectRequest jsObjReq = new JsonObjectRequest(Request.Method.POST, "https://www.jusbrasil.com.br/feed/graphql",
//                            jsonPayload, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            jsonResponse = response;
//                            textViewArticle.setText(response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            textViewArticle.setText("ERROR RECEIVING SERVER RESPONSE!");
//                        }
//                    });
//                    queue.add(jsObjReq);
//                    return null;
//                }
//                return null;
//            }

//            @Override
//            public void onLoadResource(WebView view, String url){
//                String s = url;
//            }

//        });
        articleWebView.setWebChromeClient(new MyWebChromeClient());
//        articleWebView.loadUrl("https://www.jusbrasil.com.br/artigos");


        articleURL = getIntent().getStringExtra(getString(R.string.EXTRA_ARTICLE_LINK));
        loadArticle();
    }

    private void loadArticle(){

        AsyncLoadArticle loadArticleTask = new AsyncLoadArticle();
        loadArticleTask.execute();

    }

    private class JSInterface {

        @JavascriptInterface
        public void write(final String responseText){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textViewArticle.setText(responseText);
                }
            });
        }

    }

    private class MyWebChromeClient extends WebChromeClient{
        public boolean onConsoleMessage(ConsoleMessage message){
            Log.i("CHROME CONSOLE", message.message());
            return true;
        }
    }

    private class AsyncLoadArticle extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... arg){

            try {

                articleDoc = Jsoup.connect("https://www.jusbrasil.com.br/artigos").get();
                articleDoc.selectFirst("script[src*=/feed/][src$=.js]").attr("src","file:///home/igao/workspace_web/jb_articles_html_source/WebContent/mod_feed.js");
//                articleDoc = Jsoup.connect(articleURL).get();
//                articleDoc.select("div.JB-2").first()
//                    .html("");
//                articleDoc.select("div.comments").first()
//                        .html("");

            }catch(IOException e){
                Log.e(getClass().getName(), e.getMessage());
            }
            return null;
        }

        protected void onPostExecute(Void v){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    articleWebView.loadDataWithBaseURL(articleURL, articleDoc.html(), "text/html", "UTF-8", null);
//                    articleWebView.loadDataWithBaseURL("https://www.jusbrasil.com.br/artigos", articleDoc.html(), "text/html", "UTF-8", null);
                    articleWebView.loadData(articleDoc.html(), "text/html", "UTF-8");
                }
            });
        }

    }
}

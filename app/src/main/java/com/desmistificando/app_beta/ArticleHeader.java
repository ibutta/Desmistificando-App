package com.desmistificando.app_beta;

/**
 * Created by Igor Stefani Buttarello on 12/13/17.
 */

public class ArticleHeader {
    private String title, description, link, authorName, thumbURL;

    ArticleHeader(String articleTitle, String articleDesc, String authorName, String thumbURL, String articleLink) {
        this.title = articleTitle;
        this.description = articleDesc;
        this.authorName = authorName;
        this.thumbURL = thumbURL;
        this.link = articleLink;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getAuthorName(){
        return this.authorName;
    }

    public String getThumbURL(){
        return this.thumbURL;
    }

    public String getLink(){
        return this.link;
    }

}

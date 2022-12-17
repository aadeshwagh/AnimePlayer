package com.aadesh.AnimePlayer.entity;

public class Result {

    private String title;
    private String imgUrl;
    private String animeUrl;
    private String releasedDate;

    @Override
    public String toString() {
        return ("Title: "+title+"\n"+
                "Image: "+imgUrl+"\n"+
                "Anime "+animeUrl+"\n"+
                "Released Date :"+releasedDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getAnimeUrl() {
        return animeUrl;
    }

    public void setAnimeUrl(String animeUrl) {
        this.animeUrl = animeUrl;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) {
        this.releasedDate = releasedDate;
    }
}

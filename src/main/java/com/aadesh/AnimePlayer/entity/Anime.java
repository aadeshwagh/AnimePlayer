package com.aadesh.AnimePlayer.entity;

import java.util.ArrayList;

public class Anime {
    private String title;
    private String url;

    private ArrayList<String> fillers;
    private String playerUrl;

    private int totalEpisodes;

    private int episode;

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public ArrayList<String> getFillers() {
        return fillers;
    }

    public void setFillers(ArrayList<String> fillers) {
        this.fillers = fillers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayerUrl() {
        return playerUrl;
    }

    public void setPlayerUrl(String playerUrl) {
        this.playerUrl = playerUrl;
    }

    public int getTotalEpisodes() {
        return totalEpisodes;
    }

    public void setTotalEpisodes(int totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }

    @Override
    public String toString() {
        return "Title :"+title+"\n"+"Url "+ playerUrl +"\n"+"Total Episodes: "+totalEpisodes;
    }
}

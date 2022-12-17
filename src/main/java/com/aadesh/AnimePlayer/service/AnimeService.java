package com.aadesh.AnimePlayer.service;
import com.aadesh.AnimePlayer.entity.Anime;
import com.aadesh.AnimePlayer.entity.Details;
import com.beust.ah.A;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AnimeService {

    private final String animeBaseUrl = "https://ww3.gogoanime2.org";
    private final String fillerBaseUrl = "https://www.animefillerguide.com/search?q=";
    public Anime getAnime(String url , String name)  {
        try {
            //Todo:: Implement Error handling
            int episode = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));
            CompletableFuture<Details> future1 = CompletableFuture.supplyAsync(() -> getDetails(url));
            CompletableFuture<ArrayList<String>> future2 = CompletableFuture.supplyAsync(() -> getFillerList(name));

            CompletableFuture<Anime> combined = future1.thenCombine(future2, (details, fillers) ->{
                Anime anime = new Anime();
                anime.setEpisode(episode);
                anime.setUrl(url);
                anime.setTitle(name);
                anime.setPlayerUrl(details.getPlayUrl());
                anime.setTotalEpisodes(details.getTotalEpisodes());
                anime.setFillers(fillers);

                return anime;
            });

            return combined.get();
        }catch (Exception e){
            throw new RuntimeException();
        }
    }

    public Details getDetails(String url){
        Details details = new Details();
        try {
            Document document = Jsoup.connect(url).get();

            Element element = document.getElementById("playerframe");
            assert element != null;
            String src = element.attr("src");
            String playerUrl = "https://"+src.replace("//","");
            if(src.contains("embed")){
                playerUrl = animeBaseUrl+src;
            }
            Element episodeList = document.getElementById("episode_related");
            assert episodeList != null;
            int totalEpisodes = episodeList.select("li").size();
            details.setPlayUrl(playerUrl);
            details.setTotalEpisodes(totalEpisodes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return details;
    }

    public String getModifiedUrl(String url, int shift){
        int episode = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));
        episode += shift;
        return url.substring(0, url.lastIndexOf("/") + 1) + episode;

    }
    public Anime getEpisode(Anime anime , String newUrl){
        try {
            Document document = Jsoup.connect(newUrl).get();
            Element element = document.getElementById("playerframe");
            assert element != null;
            String src = element.attr("src");
            String playerUrl = "https://"+src.replace("//","");
            if(src.contains("embed")){
                playerUrl = animeBaseUrl+src;
            }
            int episode = Integer.parseInt(newUrl.substring(newUrl.lastIndexOf("/") + 1));

            anime.setEpisode(episode);
            anime.setUrl(newUrl);
            anime.setPlayerUrl(playerUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Get the source code of the page as a string

        return anime;
    }
    public ArrayList<String> getFillerList(String title){
        String name = preprocessName(title);
       name =  name.replace("-","");
        if(name.contains("dub")){
            name = name.replace("dub","");
        }
        ArrayList<String> fillerList = new ArrayList<>();
        try {
            Document document = Jsoup.connect(fillerBaseUrl+name).get();
            String jumpUrl = document.getElementsByClass("jump-link").get(0).select("a").attr("href");
            Document document1 = Jsoup.connect(jumpUrl).get();
            ArrayList<Integer> fillers = new ArrayList<>(document1.getElementsByClass("red").select("span").stream().filter(e-> e.text().matches("[0-9].*")).map(e-> {
               Matcher matcher = Pattern.compile("\\d+\\b").matcher(e.text());
                if (matcher.find()) {
                    return Integer.parseInt(matcher.group());
                }
               return -1;
            }).toList());
            fillerList.add("Fillers ");
            fillers = new ArrayList<>(fillers.stream().filter(n->n!=-1).toList());
            int first = fillers.get(0);
            for(int i=0 ;i<fillers.size()-1;i++){
                if(fillers.get(i+1)-fillers.get(i)!=1){
                    fillerList.add(first+"-"+fillers.get(i));
                    first = fillers.get(i+1);
                }
            }
            fillerList.add(""+fillers.get(fillers.size()-1));


        } catch (Exception e) {
             fillerList.add("No Fillers Found");
        }
        return fillerList;

    }

    public String preprocessName(String name){
        name = name.replaceAll("[!$%^&*()_+.:-]"," ");
        StringBuilder title = new StringBuilder();
        for(String s : name.trim().split("[ ]{1,}")){
            title.append(s.toLowerCase()).append("-");
        }
        String res = title.toString().substring(0,title.length()-1);
        return res;
    }
}

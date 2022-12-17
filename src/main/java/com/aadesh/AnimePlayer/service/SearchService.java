package com.aadesh.AnimePlayer.service;

import com.aadesh.AnimePlayer.entity.Result;
import com.beust.ah.A;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class SearchService {
    @Autowired
    private AnimeService animeService;

    private final String baseUrl= "https://ww3.gogoanime2.org";


    public ArrayList<Result> getResults(String name){
        String title =animeService.preprocessName(name);

        ArrayList<Result> results = new ArrayList<>();

        String url = baseUrl+"/search/"+title;

        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.getElementsByClass("items").select("li");
            elements.forEach(element -> {
                    Result result = new Result();
                   result.setImgUrl(baseUrl+element.select("img").attr("src"));
                   result.setAnimeUrl(baseUrl+element.select("a").first().attr("href").replace("anime","watch")+"/1");
                   result.setTitle(element.select("a").first().attr("title"));
                   result.setReleasedDate(element.select("p").get(1).text().replace("Released:","").trim());

                   results.add(result);

            });
            return results;


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

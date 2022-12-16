package com.aadesh.AnimePlayer.controller;

import com.aadesh.AnimePlayer.entity.Anime;
import com.aadesh.AnimePlayer.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class AnimeController {
    @Autowired
    private AnimeService animeService;

    private Anime anime;

    @GetMapping("/getAnime")
    public String getAnime(@RequestParam("url") String url, String name ,Model model){
        //Todo:: add exception handling here
        long startTime = System.nanoTime();
        anime = animeService.getAnime(url,name);


        // Print the elapsed time

        model.addAttribute("anime",anime);
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in Get Anime: " + elapsedTime/1000000 + " ms");

        return "anime";
    }

    @PostMapping("/getEpisode")
    public String getEpisode(@RequestBody MultiValueMap<String,String> formData,Model model){
        long startTime = System.nanoTime();
        //Todo:: add exception handling here
        int episodeNo = Integer.parseInt(formData.getFirst("episodeNo"));

        String url = anime.getUrl();
        String newUrl = url.substring(0, url.lastIndexOf("/") + 1) + episodeNo;

        Anime modifiedAnime = animeService.getEpisode(anime,newUrl);
        model.addAttribute("anime",modifiedAnime);
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in Get Episode: " + elapsedTime/1000000 + " ms");
        return "anime";
    }
    @GetMapping ("/getNextEpisode")
    public String getNextEpisode(Model model){
        long startTime = System.nanoTime();
        Anime modifiedAnime = animeService.getEpisode(anime,animeService.getModifiedUrl(anime.getUrl(),1));
        model.addAttribute("anime" , modifiedAnime);
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in Get Next Episode: " + elapsedTime/1000000 + " ms");
        return "anime";
    }

    @GetMapping("/getPreviousEpisode")
    public String getPreviousEpisode(Model model){
        long startTime = System.nanoTime();
        Anime modifiedAnime = animeService.getEpisode(anime,animeService.getModifiedUrl(anime.getUrl(),-1));
        model.addAttribute("anime",modifiedAnime);
        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("Elapsed time in Get Previous Episode: " + elapsedTime/1000000 + " ms");
        return "anime";
    }
}

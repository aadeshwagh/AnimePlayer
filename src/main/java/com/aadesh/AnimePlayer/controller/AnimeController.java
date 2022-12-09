package com.aadesh.AnimePlayer.controller;

import com.aadesh.AnimePlayer.entity.Anime;
import com.aadesh.AnimePlayer.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
@Controller
public class AnimeController {
    @Autowired
    private AnimeService animeService;

    @GetMapping("/getAnime")
    public String getAnime(@RequestParam("url") String url, Model model){
//        System.out.println(url);
        //Todo:: add exception handling here
         Anime anime = animeService.getAnime(url);

//        model.addAttribute("url", anime.getPlayerUrl());
//        model.addAttribute("totalEpisodes",anime.getTotalEpisodes());
//        model.addAttribute("title",anime.getTitle());
//        model.addAttribute("episode", anime.getEpisodeNumber());
          model.addAttribute("anime",anime);

        return "anime";
    }

    @PostMapping("/getEpisode")
    public String getEpisode(@RequestBody MultiValueMap<String, String> formData,Model model){
        String episodeNo = formData.getFirst("episodeNo");
        String url = formData.getFirst("url");
        //Todo:: add exception handling here
        String baseUrl = url.substring(0,url.length()-1);
        return getAnime(baseUrl+episodeNo,model);
    }
    @GetMapping("/getNextEpisode")
    public String getNextEpisode(@RequestParam("url") String url,Model model){
        String baseUrl = url.substring(0,url.length()-1);
        String episodeNo = ""+url.charAt(url.length()-1);
        int nextEpisode =Integer.parseInt(episodeNo) +1;
       return getAnime(baseUrl+nextEpisode,model);
    }

    @GetMapping("/getPreviousEpisode")
    public String getPreviousEpisode(@RequestParam("url") String url,Model model){
        String baseUrl = url.substring(0,url.length()-1);
        String episodeNo = ""+url.charAt(url.length()-1);
        int previousEpisode =Integer.parseInt(episodeNo) -1;
        return getAnime(baseUrl+previousEpisode,model);
    }
}

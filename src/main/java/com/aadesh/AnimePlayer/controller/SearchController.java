package com.aadesh.AnimePlayer.controller;

import com.aadesh.AnimePlayer.entity.Anime;
import com.aadesh.AnimePlayer.entity.Result;
import com.aadesh.AnimePlayer.service.AnimeService;
import com.aadesh.AnimePlayer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private  AnimeService animeService;



    @PostMapping("/getSearchResults")
    public String getSearchResults(@ModelAttribute("name") String name, Model model){

        String title =animeService.preprocessName(name);

        long startTime = System.nanoTime();
        String keyWord = "/search/"+title;

        ArrayList<Result> results = searchService.getResults(keyWord);
        model.addAttribute("results",results);

        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("\nElapsed time in Get Results: " + elapsedTime/1000000 + " ms");




        return "search-results";

    }
    @GetMapping("/getNewRelease")
    public String getNewRelease(Model model){
        String keyWord = "/home";
        ArrayList<Result> results = searchService.getResults(keyWord);
        model.addAttribute("results",results);

        return "new";
    }
    @GetMapping("/getPopular/{pageNo}")
    public String getPopular(@PathVariable String pageNo, Model model){
        String keyWord = "/popular/"+pageNo;
        ArrayList<Result> results = searchService.getResults(keyWord);
        model.addAttribute("results",results);


        return "popular";
    }
}

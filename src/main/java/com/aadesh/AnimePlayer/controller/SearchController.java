package com.aadesh.AnimePlayer.controller;

import com.aadesh.AnimePlayer.entity.Anime;
import com.aadesh.AnimePlayer.entity.Result;
import com.aadesh.AnimePlayer.service.AnimeService;
import com.aadesh.AnimePlayer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/getSearchResults")
    public String getSearchResults(@ModelAttribute("name") String name, Model model){

        long startTime = System.nanoTime();

        ArrayList<Result> results = searchService.getResults(name);
        model.addAttribute("results",results);

        long endTime = System.nanoTime();

        long elapsedTime = endTime - startTime;
        System.out.println("\nElapsed time in Get Results: " + elapsedTime/1000000 + " ms");




        return "search-results";

    }
}

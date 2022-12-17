package com.aadesh.AnimePlayer.controller;

import com.aadesh.AnimePlayer.service.AnimeService;
import org.checkerframework.checker.regex.qual.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    AnimeController animeController;

    @Autowired
    private AnimeService animeService;

    @GetMapping("/")
    public String home(){
        return "home";
    }
//    @PostMapping("/getAnimeByName")
//    public String getAnimeByName(@ModelAttribute("name") String name, Model model) throws IOException {
//        String title = animeService.preprocessName(name);
//        String searchName = title.substring(0,title.length()-1).trim();
//        String url = "https://ww3.gogoanime2.org/watch/"+searchName+"/1";
//
//        return animeController.getAnime(url,name,model);
//    }
}

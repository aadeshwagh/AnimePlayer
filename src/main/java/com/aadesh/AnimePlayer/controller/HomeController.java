package com.aadesh.AnimePlayer.controller;

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

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @PostMapping("/getAnimeByName")
    public String getAnimeByName(@ModelAttribute("name") String name, Model model) throws IOException {
        name = name.replaceAll("[!$%^&*()_+.:-]"," ");
        StringBuilder title = new StringBuilder();
        for(String s : name.trim().split("[ ]{1,}")){
            title.append(s.toLowerCase()).append("-");
        }

        String searchName = title.substring(0,title.length()-1).trim();
        String url = "https://ww3.gogoanime2.org/watch/"+searchName+"/1";

        return animeController.getAnime(url,model);
    }
}

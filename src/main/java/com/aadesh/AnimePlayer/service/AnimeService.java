package com.aadesh.AnimePlayer.service;

import com.aadesh.AnimePlayer.entity.Anime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class AnimeService {

    public Anime getAnime(String url) {

            Anime anime = new Anime();
            String resourceName = "chromedriver.exe";

            ClassLoader classLoader = getClass().getClassLoader();
            String path = Objects.requireNonNull(classLoader.getResource(resourceName)).getPath();

            //Todo:: Implement Error handling
            System.setProperty("webdriver.chrome.driver", path);

            WebDriver driver = new ChromeDriver(getChromeOptions());
            driver.get(url);

            String pageSource = driver.getPageSource();

            Document document = Jsoup.parse(pageSource);
            Element element = document.getElementById("playerframe");
            String src = element.attr("src");
            String playerUrl = "https://"+src.replace("//","");
            if(src.contains("embed")){
                playerUrl = "https://ww3.gogoanime2.org"+src;
            }

            AtomicInteger totalEpisodes = new AtomicInteger();
            AtomicReference<String> episodeNumber = new AtomicReference<>("-1");
            Element episodeList = document.getElementById("episode_related");
            episodeList.select("li").forEach(element1 -> {
                totalEpisodes.getAndIncrement();
            });

            String title = document.getElementsByClass("title_name").text();
            for(String s :title.split(" ")){
               if(s.matches("-?\\d+")){
                   episodeNumber = new AtomicReference<>(s);
               }
            }
            anime.setUrl(url);
            anime.setTitle(title.replace("for free on gogoanime",""));
            anime.setPlayerUrl(playerUrl);
            anime.setTotalEpisodes(totalEpisodes.get());
            anime.setEpisodeNumber(Integer.parseInt(episodeNumber.get()));
            driver.quit();

            return anime;
    }
    private ChromeOptions getChromeOptions(){
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.setExperimentalOption("useAutomationExtension", false);

        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--window-size=1580,1280");

        final HashMap<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        return chromeOptions;
    }
}

package com.github.kokecena.skymus.models.mappers;

import com.github.kokecena.skymus.commons.Utils;
import com.github.kokecena.skymus.models.Track;
import org.jsoup.nodes.Element;

public class TrackMapper implements Mapper<Track, Element> {

    @Override
    public Track mapTo(Element element) {
        String artist = element.getElementsByClass("artist-name").text();
        String trackName = element.getElementsByClass("track-name").text();
        String duration = element.getElementsByClass("time-text").text();
        String downloadUrl = element.getElementsByClass("mp3").attr("href");
        String purgeUrl = downloadUrl.replaceFirst("//data.mp3-", "http://data.mp3-");
        String purgeUrlToPlay = purgeUrl.replaceFirst("/download-track/", "/play-track/");
        String[] ex = purgeUrlToPlay.split("/");
        String playUrl = ex[0] + "//" + ex[2] + "/" + ex[3] + "/" + ex[4] + "/" + ex[5] + "/" + ex[6];
        return new Track(artist, trackName, Utils.parseToSeconds(duration), purgeUrl, playUrl);
    }
}

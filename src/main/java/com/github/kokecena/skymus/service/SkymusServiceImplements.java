package com.github.kokecena.skymus.service;

import com.github.kokecena.skymus.models.Track;
import com.github.kokecena.skymus.models.mappers.Mapper;
import com.github.kokecena.skymus.models.mappers.TrackMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the SkymusService interface for web scraping track information from skymus.org.
 * <p>
 * This class utilizes Jsoup for web scraping to fetch track information from the Skymus website.
 * It implements the SkymusService interface, providing methods to retrieve tracks based on a given query.
 */
public class SkymusServiceImplements implements SkymusService {

    private static final Logger logger = Logger.getLogger(SkymusServiceImplements.class.getName());
    private static final String URL_SITE = "https://skymus.org";
    private final Mapper<Track, Element> mapper;

    /**
     * Constructor for SkymusServiceImplements class.
     * Initializes the mapper for mapping HTML elements to Track objects.
     */
    public SkymusServiceImplements() {
        mapper = new TrackMapper();
    }

    /**
     * Connects to the specified URL and retrieves the HTML document.
     *
     * @param href Relative URL to connect to.
     * @return Jsoup Document representing the HTML content.
     * @throws IOException If an error occurs during the connection or document retrieval.
     */
    private Document connect(String href) throws IOException {
        return Jsoup.connect(URL_SITE.concat(href))
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36")
                .header("Accept-Language", "*")
                .get();
    }

    /**
     * Retrieves a Flux of Track objects based on the provided query.
     *
     * @param query Search query for fetching tracks.
     * @return Flux of Track objects.
     */
    @Override
    public Flux<Track> getTracks(String query) {
        return getDocument("/tracks/".concat(query))
                .onErrorContinue((throwable, o) -> logger.log(Level.SEVERE, throwable.getMessage()))
                .flatMapMany(document -> {
                    Element sitePaginator = document.getElementById("site-paginator");

                    if (Objects.isNull(sitePaginator)) {
                        Elements elements = document.getElementsByClass("f-table");
                        if (elements.isEmpty()) {
                            return Flux.empty();
                        }
                        return Flux.fromIterable(elements)
                                .concatMap(element -> Flux.just(mapper.mapTo(element)));
                    }
                    Elements links = sitePaginator.select("a[href]");
                    return Flux.fromIterable(links)
                            .flatMap(link -> getDocument(link.attr("href")))
                            .onErrorContinue((throwable, o) -> logger.log(Level.SEVERE, throwable.getMessage()))
                            .flatMap(doc -> {
                                Elements table = doc.getElementsByClass("f-table");
                                return Flux.fromIterable(table).concatMap(element -> Flux.just(mapper.mapTo(element)));
                            });
                });
    }

    /**
     * Retrieves a Mono of Jsoup Document based on the provided query.
     *
     * @param query Relative URL to retrieve the document.
     * @return Mono of Jsoup Document.
     */
    private Mono<Document> getDocument(String query) {
        try {
            return Mono.just(connect(query));
        } catch (IOException e) {
            return Mono.error(e);
        }
    }
}
package com.github.kokecena.skymus.service;

import com.github.kokecena.skymus.models.Track;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

public interface SkymusService {
    Flux<Track> getTracks(String query) ;
}

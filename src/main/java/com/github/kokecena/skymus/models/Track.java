package com.github.kokecena.skymus.models;

import lombok.*;


@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Track {
    private String artist;
    private String title;
    private long duration;
    private String downloadUrl;
    private String playUrl;
}

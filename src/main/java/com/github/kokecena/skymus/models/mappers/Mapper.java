package com.github.kokecena.skymus.models.mappers;

public interface Mapper<V,K> {
    V mapTo(K element);
}

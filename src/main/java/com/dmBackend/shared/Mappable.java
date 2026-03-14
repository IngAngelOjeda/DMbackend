package com.dmBackend.shared;

public interface Mappable<E, D>{

    E toEntity();

    D toDto();
}

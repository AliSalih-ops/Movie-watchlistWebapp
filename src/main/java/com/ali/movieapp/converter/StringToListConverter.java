
package com.ali.movieapp.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StringToListConverter implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(source.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
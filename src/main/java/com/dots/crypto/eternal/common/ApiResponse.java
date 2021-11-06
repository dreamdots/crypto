package com.dots.crypto.eternal.common;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;


@Slf4j
@Getter(AccessLevel.PROTECTED)
@RequiredArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class ApiResponse<T> {
    private final static TypeFactory factory = TypeFactory.defaultInstance();
    private final InputStream is;
    private final JavaType responseType;
    private final ApiRequest<T> request;

    @SneakyThrows
    public ResponseWrapper<T> deserialize() {
        final ObjectMapper mapper = new ObjectMapper();
        final ObjectReader reader = mapper.readerFor(factory.constructParametricType(ResponseWrapper.class, responseType));

        ResponseWrapper<T> result = reader.readValue(is);

        if (result != null) {
            log.info("Successfully executing request -> " + request.toString());
        }

        return result;
    }
}

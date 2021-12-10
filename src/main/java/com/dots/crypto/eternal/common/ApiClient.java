package com.dots.crypto.eternal.common;

import java.io.IOException;

public interface ApiClient<T extends ApiResponse<?>, R extends ApiRequest<?>> {
    T execute(R request) throws IOException;
}

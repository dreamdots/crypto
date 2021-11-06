package com.dots.crypto.eternal.common;

import java.io.IOException;

public interface ApiClient {

    <T, R extends ApiRequest<T>> ApiResponse<T> execute(R request) throws IOException;

}

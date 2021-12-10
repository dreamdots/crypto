package com.dots.crypto.eternal.bscscan;

import com.dots.crypto.eternal.common.ApiClient;
import com.dots.crypto.eternal.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Modifier;

@Slf4j
@Component
public class BscscanClient implements ApiClient<ApiResponse<?>, BscscanApiRequest<?>> {
    private final static String url = "https://api.bscscan.com/api";
    private final UrlBuilder urlBuilder = new UrlBuilder();
    private final HttpClient client;

    @Value("${eternal.bscscan.token}")
    private String token;

    public BscscanClient() {
        this.client = HttpClients.createMinimal();
    }

    @Override
    public ApiResponse<?> execute(final BscscanApiRequest<?> request) throws IOException {
        final String url = urlBuilder.buildUrl(request);
        final HttpGet req = new HttpGet(url);

        return new ApiResponse<>(client.execute(req).getEntity().getContent(), request.getType(), request);
    }

    private class UrlBuilder {
        private final static String KEY_PARAM = "&apikey=";
        private final static String ACTION_PARAM = "&action=";
        private final static String MODULE_PARAM = "?module=";

        private String buildUrl(final BscscanApiRequest<?> request) {
            final StringBuilder result = new StringBuilder(url);
            result.append(MODULE_PARAM).append(request.getModule().getParam());
            result.append(KEY_PARAM).append(token);
            result.append(ACTION_PARAM).append(request.getAction().getAction());

            ReflectionUtils.doWithFields(
                    request.getClass(),
                    f -> {
                        ReflectionUtils.makeAccessible(f);

                        final String value = String.valueOf(ReflectionUtils.getField(f, request));

                        if (!value.equals("null")) {
                            final String name = f.getName();

                            result.append("&").append(name).append("=").append(value);
                        }
                    },
                    f -> !Modifier.isFinal(f.getModifiers())
            );

            return result.toString();
        }
    }
}

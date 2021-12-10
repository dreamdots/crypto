package com.dots.crypto.eternal.bscscan;

import com.dots.crypto.eternal.common.ApiRequest;

public abstract class BscscanApiRequest<T> extends ApiRequest<T> {
    public abstract RequestModule getModule();
    public abstract RequestAction getAction();
}

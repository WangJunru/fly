package com.fly.sdk.http;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

public class MyHttpGet extends HttpEntityEnclosingRequestBase{
	public static final String METHOD_NAME = "GET";

    public String getMethod() {
        return METHOD_NAME;
    }

    public MyHttpGet(final String uri) {
        super();
        setURI(URI.create(uri));
    }

    public MyHttpGet(final URI uri) {
        super();
        setURI(uri);
    }

    public MyHttpGet() {
        super();
    }
}

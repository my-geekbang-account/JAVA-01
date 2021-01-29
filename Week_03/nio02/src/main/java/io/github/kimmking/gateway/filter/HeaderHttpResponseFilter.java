package io.github.kimmking.gateway.filter;

import io.netty.handler.codec.http.FullHttpResponse;

public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
      // response filter
        response.headers().set("kk", "java-1-nio");
        // NOTE: Homework 3 filter, customize response headers
        response.headers().set("gateway-add-header", "value added by gateway");
    }
}

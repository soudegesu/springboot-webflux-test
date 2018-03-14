package com.soudegesu.example.netty.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@ChannelHandler.Sharable
public class HttpControllerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Autowired
    private PathHandleProvider provider;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        HttpResponseStatus status = HttpResponseStatus.OK;
        String responseBody = "";
        AsciiString type = HttpHeaderValues.APPLICATION_JSON;

        try {
            Function handler = provider.getHandler(request);

            if (handler == null) {
                writeResponse(ctx, HttpResponseStatus.NOT_FOUND, HttpHeaderValues.TEXT_PLAIN, "Not Found.");
                return;
            }

            Object response = handler.apply(request);

            if (response instanceof String) {
                responseBody = (String)response;
            } else if (response != null){
                responseBody = toJson(response);
            }
        } catch (Exception e) {
            status = HttpResponseStatus.INTERNAL_SERVER_ERROR;
            responseBody = e.getMessage();
            type = HttpHeaderValues.TEXT_PLAIN;
        }
        writeResponse(ctx, status, type, responseBody);
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    private void writeResponse(ChannelHandlerContext ctx, HttpResponseStatus status, AsciiString mimeType, String body) {
        ByteBuf buf = Unpooled.wrappedBuffer(body.getBytes());
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);

        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeType.toString() + "; charset=UTF-8");
        HttpUtil.setKeepAlive(response, true);

        ctx.writeAndFlush(response);
    }
}

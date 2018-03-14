package com.soudegesu.example.netty.server;

import com.soudegesu.example.netty.annotation.RequestMapping;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

@Component
public class PathHandleProvider {

    @Autowired
    private ApplicationContext context;

    private Map<String, Function<FullHttpRequest, ?>> storage = new TreeMap<>();

    @PostConstruct
    private void init() {
        Collection beans = context.getBeansWithAnnotation(Controller.class).values();
        beans.stream().forEach(bean -> {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method m : methods) {
                Annotation pathAnnotation = m.getAnnotation(RequestMapping.class);
                if (pathAnnotation == null) {
                    continue;
                }

                String path = ((RequestMapping)pathAnnotation).value();

                if (storage.containsKey(((RequestMapping) pathAnnotation).value())) {
                    throw new RuntimeException("already exists");
                } else {
                    storage.put(path, (request) -> {
                        Object res = null;
                        try {
                             res = m.invoke(bean, request);
                        } catch (InvocationTargetException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        return res;
                    });
                }
            }
        });
    }

    public Function getHandler(FullHttpRequest request) {
        for (String entry : storage.keySet()) {
            if(request.getUri().startsWith(entry)) {
                return storage.get(entry);
            }
        }
        throw new NullPointerException();
    }
}

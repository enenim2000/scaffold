package com.enenim.scaffold.interceptor;

import com.enenim.scaffold.constant.CommonConstant;
import com.enenim.scaffold.util.RequestUtil;
import com.enenim.scaffold.util.Security;
import com.enenim.scaffold.util.message.SpringMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class RequestInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        String requestBody = IOUtils.toString(requestWrapper.getInputStream(), UTF_8);
        String requestUrl = requestWrapper.getRequestURL().toString();
        JsonNode requestJson = new ObjectMapper().readTree(requestBody);
        HttpMethod requestMethod = HttpMethod.valueOf(requestWrapper.getMethod());
        Enumeration headerNames = requestWrapper.getHeaderNames();
        HttpHeaders requestHeaders = new HttpHeaders();
        while (headerNames.hasMoreElements()){
            String headerName = (String) headerNames.nextElement();
            requestHeaders.add(headerName, requestWrapper.getHeader(headerName));
        }
        RequestEntity<JsonNode> requestEntity = new RequestEntity<>(requestJson, requestHeaders, requestMethod, URI.create(requestUrl));
        LOGGER.info("Logging Http Request {} ", requestEntity);

        HttpStatus responseStatus = HttpStatus.valueOf(responseWrapper.getStatusCode());
        HttpHeaders responseHeaders = new HttpHeaders();
        for(String headerName : responseWrapper.getHeaderNames()){
            responseHeaders.add(headerName, responseWrapper.getHeader(headerName));
        }
        String responseBody = IOUtils.toString(responseWrapper.getContentInputStream(), UTF_8);
        JsonNode responseJson = new ObjectMapper().readTree(responseBody);
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(responseJson, responseHeaders, responseStatus);
        LOGGER.info("Logging http response {}", responseEntity);
        responseWrapper.copyBodyToResponse();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void buildRequestUtil(ContentCachingRequestWrapper requestWrapper) throws IOException{
        String requestBody = IOUtils.toString(requestWrapper.getInputStream(), UTF_8);
        RequestUtil.setLang(SpringMessage.msg("lang"));
        RequestUtil.setUserAgent(requestWrapper.getParameter(CommonConstant.USER_AGENT));
        RequestUtil.setIpAdress(requestWrapper.getParameter(CommonConstant.IP_ADDRESS));
        RequestUtil.setRID(Security.encypt(requestWrapper.getRequestURI() + requestBody + requestWrapper.getMethod()));
    }
}
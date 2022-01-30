package dachertanov.pal.palbackendservice.controller.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class RequestsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        filterChain.doFilter(requestWrapper, response);

        String requestLog = "\n===========================request begin================================================\n" +
                "URI         : { " + ((HttpServletRequest) request).getRequestURI() + " }\n" +
                "Method      : { " + ((HttpServletRequest) request).getMethod() + " }\n" +
                "Request body: { " + new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8) + " }\n" +
                "==========================request end================================================";
        log.info(requestLog);
    }
}

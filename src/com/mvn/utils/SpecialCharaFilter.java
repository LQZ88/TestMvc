package com.mvn.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class SpecialCharaFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        filterChain.doFilter(requestWrapper, response);
    }

    public String filterDangerString(String value) {
        if (value == null) {
            return null;
        }
        value = value.replaceAll("<script", "《script");
        value = value.replaceAll("\\'", "’");
        value = value.replaceAll("<", "&lt;");
        value = value.replaceAll(">", "&gt;");
        return value;
    }

    /**
     * 重写HttpServletRequestWrapper
     * 
     * 
     */
    class ParameterRequestWrapper extends HttpServletRequestWrapper {
        public ParameterRequestWrapper(HttpServletRequest request) {
            super(request);
        }
        @Override
        // 重写getParameter
        public String getParameter(String name) {
            // 返回值之前 先进行过滤
            return filterDangerString(super.getParameter(name));
        }
        @Override
        // 重写getParameterValues
        public String[] getParameterValues(String name) {
            // 返回值之前 先进行过滤
            String[] values = super.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    values[i] = filterDangerString(values[i]);
                }
            }
            return values;
        }
    }
}

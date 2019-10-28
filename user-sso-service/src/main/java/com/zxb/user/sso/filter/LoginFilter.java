package com.zxb.user.sso.filter;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zxb.message.thrift.user.dto.UserDTO;
import com.zxb.message.thrift.user.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * class
 *
 * @author Mr.zxb
 * @date 2019-10-28 20:11
 */
@Slf4j
public abstract class LoginFilter implements Filter {

    /**
     * 缓存实例
     */
    private static final Cache<String, UserDTO> CACHE = CacheBuilder.newBuilder().maximumSize(10000)
            .expireAfterWrite(3, TimeUnit.MINUTES).build();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(token)) {
                    token = cookie.getValue();
                }
            }
        }
        UserDTO userDTO = null;
        if (StringUtils.isNotBlank(token)) {
            // 从缓存里获取
            userDTO = CACHE.getIfPresent(token);
            if (userDTO == null) {
                userDTO = requestUserInfo(token);
            }
        }
        if (userDTO == null) {
            response.sendRedirect("http://localhost:8082/user/login");
            return;
        }

        // 存入缓存
        CACHE.put(token, userDTO);

        login(request, response, userDTO);

        filterChain.doFilter(request, response);
    }

    /**
     * 抽象业务方法，供所需要的继续实现
     * @param request
     * @param response
     * @param userDTO
     */
    protected abstract void login(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO);

    private UserDTO requestUserInfo(String token) {
        String url = "http://localhost:8082/user/authentication";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("token", token);
        InputStream inputStream = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("request user info failed! http status: " + statusCode);
            }
            inputStream = response.getEntity().getContent();
            byte[] bytes = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            int len;
            while ((len = inputStream.read(bytes)) > 0) {
                stringBuilder.append(new String(bytes, 0, len));
            }
            UserDTO userDTO = JsonUtil.fromJson(stringBuilder.toString(), UserDTO.class);
            log.info("LoginFilter get userDto: {}", userDTO);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}

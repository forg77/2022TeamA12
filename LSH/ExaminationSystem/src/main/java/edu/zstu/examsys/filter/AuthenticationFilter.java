package edu.zstu.examsys.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.zstu.examsys.util.IOUtil;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = null;
            String password = null;
            try {
                JSONObject body = JSON.parseObject(IOUtil.readAll(request.getReader()));
                username = body.getString("username");
                password = body.getString("password");
            } catch (IOException e) {
                e.printStackTrace();
            }

            username = username != null ? username : "";
            username = username.trim();

            password = password != null ? password : "";

            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }
}

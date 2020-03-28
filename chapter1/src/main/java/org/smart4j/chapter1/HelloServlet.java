package org.smart4j.chapter1;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@WebServlet(name = "HelloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String currentTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        request.setAttribute("currentTime", currentTime);
        log.info("currentTime: {}", currentTime);
        try {
            request.getRequestDispatcher("/jsp/hello.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

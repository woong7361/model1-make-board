package com.study.controller.front;

import com.study.exception.CustomException;
import com.study.exception.WrapCheckedException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * HTTP request가 들어오는 최상단 클래스로 exception을 catch해 처리한다.
 */
@WebServlet(urlPatterns = "/controller/*")
public class FrontControlServlet extends HttpServlet {
    private final ControllerMapper controllerMapper = new ControllerMapper();

    /***
     * controllerMapper로 url mapping을 위임한 후
     * exception을 catch하여 error handling한다.
     * @param request http request
     * @param response http response
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            request.setCharacterEncoding("UTF-8");
            controllerMapper.mapping(request, response);
        } catch (WrapCheckedException e) {
            e.printStackTrace();
            System.out.println("e.getMessage() = " + e.getMessage());

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        } catch (CustomException e) {
            System.out.println("e.getMessage() = " + e.getMessage());

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        } catch (Exception e) {
            e.printStackTrace();

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        }
    }
}

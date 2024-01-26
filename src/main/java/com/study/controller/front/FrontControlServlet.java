package com.study.controller.front;

import com.study.exception.CustomException;
import com.study.exception.WrapCheckedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.study.constant.ViewUriConstant.INTERNAL_ERROR_PAGE_VIEW_URI;


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
        Logger logger = LoggerFactory.getLogger(FrontControlServlet.class);

        //TODO error logging의 위치가 잘못된듯한 느낌
        // throw 할때 error의 정보와 같이 던져야 할듯
        try {
            request.setCharacterEncoding("UTF-8");
            controllerMapper.mapping(request, response);
        } catch (WrapCheckedException e) {
            e.printStackTrace();
            logger.error("", e);

            response.sendRedirect(INTERNAL_ERROR_PAGE_VIEW_URI);
        } catch (CustomException e) {
            logger.error("",  e);

            response.sendRedirect(INTERNAL_ERROR_PAGE_VIEW_URI);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("",  e);

            response.sendRedirect(INTERNAL_ERROR_PAGE_VIEW_URI);
        }
    }
}

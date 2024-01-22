package com.study.controller;

import com.study.exception.CustomException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.study.constant.ControllerUriConstant.*;


@WebServlet(urlPatterns = "/controller/*")
public class FrontControlServlet extends HttpServlet {

    private final Map<String, Controller> controllerMap = new HashMap<>();

    /**
     * URI endpoint 와 controller mapping
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        controllerMap.put(BOARD_WRITE_CONTROLLER_URI, new WriteController());
        controllerMap.put(BOARD_VIEW_CONTROLLER_URI, new BoardViewController());

        super.init();
    }

    /**
     * 요청온 URI endpoint에 해당하는 controller실행
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String requestURI = request.getRequestURI();
        Controller controller = controllerMap.get(requestURI);

        //TODO error catch Servelt 새로 만들기
        try {
            controller.service(request, response);
        } catch (CustomException e) {
            e.printStackTrace();

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        } catch (SQLException e) {
            e.printStackTrace();

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        } catch (Exception e) {
            e.printStackTrace();

            // TODO ERROR log 출력

            response.sendRedirect("/error/error.jsp");
        }
    }
}

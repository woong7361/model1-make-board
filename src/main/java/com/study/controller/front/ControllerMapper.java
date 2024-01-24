package com.study.controller.front;

import com.study.controller.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.study.constant.ControllerUriConstant.*;

/**
 * 요청 들어온 URI를 controller에 매핑해주는 클래스
 */
public class ControllerMapper {
    private static final Map<String, Controller> controllerMap = new HashMap<>();

    public ControllerMapper() {
        controllerMap.put(BOARD_WRITE_CONTROLLER_URI, new WriteController());
        controllerMap.put(BOARD_VIEW_CONTROLLER_URI, new BoardViewController());
        controllerMap.put(BOARD_LIST_VIEW_CONTROLLER_URI, new BoardListViewController());
        controllerMap.put(BOARD_UPDATE_VIEW_CONTROLLER_URI, new BoardUpdateViewController());
        controllerMap.put(BOARD_UPDATE_CONTROLLER_URI, new BoardUpdateController());
        controllerMap.put(BOARD_DELETE_CONTROLLER_URI, new BoardDeleteController());
        controllerMap.put(BOARD_WRITE_VIEW_CONTROLLER_URI, new BoardWriteViewController());
        controllerMap.put(COMMENT_CREATE_CONTROLLER_URI, new CommentWriteController());
        controllerMap.put(FILE_DOWNLOAD_CONTROLLER_URI, new FileDownloadController());
    }

    /**
     * URI와 대응하는 Controller를 매핑한다.
     *
     * @param request
     * @param response
     */
    public void mapping(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        Controller controller = controllerMap.get(requestURI);

        controller.service(request, response);
    }
}

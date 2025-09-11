package edu.egg.tinder.controlador;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorError implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView renderErrorPage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400 -> errorMsg = "El recurso solicitado no existe";
            case 401 -> errorMsg = "No se encuentra autorizado";
            case 403 -> errorMsg = "No tiene permisos para acceder al recurso";
            case 404 -> errorMsg = "El recurso solicitado no fue encontrado";
            case 500 -> errorMsg = "Ocurrió un error interno";
            default -> errorMsg = "Se produjo un error";
        }
        errorPage.addObject("codigo", httpErrorCode);
        errorPage.addObject("mensaje", errorMsg);
        return errorPage;
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        Object status = httpRequest.getAttribute("jakarta.servlet.error.status_code");
        if (status instanceof Integer code) {
            return code;
        }
        return 500;
    }
}

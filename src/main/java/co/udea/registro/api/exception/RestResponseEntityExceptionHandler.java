package co.udea.registro.api.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler({MissDataException.class})
    protected ResponseEntity handleMissDataException(
            HttpServletRequest request, MissDataException ex) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({InvalidDateException.class})
    protected ResponseEntity handleInvalidDateException(
            HttpServletRequest request, InvalidDateException ex) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({DataNotFoundException.class})
    protected ResponseEntity handleDataNotFoundException(
            HttpServletRequest request, DataNotFoundException ex) {
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity handleUnhandledException(
            HttpServletRequest request, Throwable ex) {
        //TODO: Enviar correo electrónico con error no manejado
        logger.error(request.getRequestURL().toString(), ex);
        return new ResponseEntity<>( "No se ha podido procesar su solicitud. Contacte al administrador.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

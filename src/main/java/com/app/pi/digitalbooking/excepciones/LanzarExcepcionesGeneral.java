package com.app.pi.digitalbooking.excepciones;

import com.app.pi.digitalbooking.model.dto.InfoError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class LanzarExcepcionesGeneral {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InfoError> methodArgumentNotValidException(MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        // convert errors to standard string
        StringBuilder errorMessage = new StringBuilder();
        fieldErrors.forEach(f -> errorMessage.append(f.getField() + " " + f.getDefaultMessage() +  " "));

        // return error info object with standard json
        InfoError errorInfo = new InfoError(errorMessage.toString(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({NombreProductoExisteExcepcion.class})
    public ResponseEntity<InfoError> procesoExcepcionBadRequest(NombreProductoExisteExcepcion e) {
        InfoError errorInfo = new InfoError(e.getMessage(), HttpStatus.FOUND.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.FOUND);
    }

    @ExceptionHandler({RegistroNoEncontradoException.class})
    public ResponseEntity<InfoError> procesoExcepcionBadRequest(RegistroNoEncontradoException e) {
        InfoError errorInfo = new InfoError(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RegistroExistenteException.class})
    public ResponseEntity<InfoError> procesoExcepcionRegistroExistente(RegistroExistenteException e) {
        InfoError errorInfo = new InfoError(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<InfoError> procesoIllegalStateException(IllegalStateException e) {
        InfoError errorInfo = new InfoError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}

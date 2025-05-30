package com.app.projectstyleecommerce.constant;


import com.app.projectstyleecommerce.model.ErrorModel;
import org.springframework.http.HttpStatus;

public class AppMessageConstant {
    public static final ErrorModel ENTITY_NOT_FOUND = ErrorModel.of(HttpStatus.INTERNAL_SERVER_ERROR.value(),"Entity not found");
    private AppMessageConstant(){

    }
}

package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.service.CommonService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public abstract class CommonController <T,ID,S  extends CommonService<T,ID>> {
private final S service;
public CommonController(S service) {
    this.service = service;
}
    protected  S getService() {return service;}
    @GetMapping
    public List<T> getAll() {
        return service.getAll();
    }
}

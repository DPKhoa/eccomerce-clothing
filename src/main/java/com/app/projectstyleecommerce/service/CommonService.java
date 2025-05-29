package com.app.projectstyleecommerce.service;

import java.util.List;

public interface CommonService<T,ID> {
    T save(T entity);
    T getById(ID id);
    List<T> getAll();
void deleteById(ID id);
boolean existsById(ID id);
void deleteByIdIns(List<ID> ids);
}

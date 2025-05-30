package com.app.projectstyleecommerce.service;

import java.util.List;
import java.util.Map;

public interface CommonService<T,ID> {
    T save(T entity) throws Exception;
    T findById(ID id) throws Exception;
    List<T> findAll();
    void deleteById(ID id) throws Exception;
    boolean existsById(ID id) throws Exception;
    void deleteByIdIns(List<ID> ids);

}

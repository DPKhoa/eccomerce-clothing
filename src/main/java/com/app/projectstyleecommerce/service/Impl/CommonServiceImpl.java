package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.constant.AppMessageConstant;
import com.app.projectstyleecommerce.exception.AppException;
import com.app.projectstyleecommerce.repository.CommonJpaRepository;
import com.app.projectstyleecommerce.service.CommonService;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class CommonServiceImpl<T,ID, R extends CommonJpaRepository<T, ID>> implements CommonService<T,ID> {
    private final R repo;

    public CommonServiceImpl(R repo) {
        this.repo = repo;
    }
    @Override
    public T save(T entity) throws Exception {
        return repo.save(entity);
    }

    @Override
    public T getById(ID id) {
        Optional<T> entityOptional = repo.findById(id);
        if (!entityOptional.isPresent()) {
            throw AppException.of(AppMessageConstant.ENTITY_NOT_FOUND);
        }
        return entityOptional.get();
    }

    @Override
    public List<T> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteById(ID id) throws Exception {
        if(!existsById(id)) {
            repo.deleteById(id);
        }
    }

    @Override
    public boolean existsById(ID id) throws Exception {
        return false;
    }

    @Override
    public void deleteByIdIns(List<ID> ids) {

    }


}

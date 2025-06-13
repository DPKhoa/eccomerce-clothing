package com.app.projectstyleecommerce.service.Impl;

import com.app.projectstyleecommerce.entity.UserEntity;
import com.app.projectstyleecommerce.repository.UserRepository;
import com.app.projectstyleecommerce.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl extends CommonServiceImpl<UserEntity,Long, UserRepository> implements UserService {
    public UserServiceImpl(UserRepository repo) {
        super(repo);
    }

    @Override
    public UserEntity save(UserEntity entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public UserEntity findById(Long id) throws Exception {
        Optional<UserEntity> userId = getRepo().findById(id);
        return userId.orElse(null);
    }

    @Override
    public List<UserEntity> findAll() {
        return getRepo().findAll();
    }

    @Override
    public boolean existsById(Long id) throws Exception {
        return getRepo().existsById(id);
    }

    public UserEntity update(Long id, Map<String, Object> updates) {
        UserEntity user = getRepo().findById(id).orElseThrow(()->new EntityNotFoundException("User not found"));
        if(Objects.nonNull(user)){
            updates.forEach((k,v)->{
                switch (k) {
                    case "name":
                        user.setUsername(String.valueOf(v));
                        break;
                    case "email":
                        user.setEmail(String.valueOf(v));
                        break;
                }
            });
        }
        return getRepo().save(user);
    }
}

package com.app.projectstyleecommerce.service;

import com.app.projectstyleecommerce.entity.UserEntity;

import java.util.Map;

public interface UserService extends CommonService<UserEntity,Long> {
    UserEntity update (Long id, Map<String, Object> updates);
}

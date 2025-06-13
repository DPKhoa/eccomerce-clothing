package com.app.projectstyleecommerce.controller;

import com.app.projectstyleecommerce.entity.UserEntity;
import com.app.projectstyleecommerce.service.UserService;
import com.app.projectstyleecommerce.util.UrlUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(UrlUtil.USER_URL)
public class UserController extends CommonController<UserEntity,Long, UserService> {
    public UserController(UserService service) {
        super(service);
    }
    @GetMapping()
    public List<UserEntity> getAll() {
        return getService().findAll();
    }
    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable Long id) throws Exception {
        return getService().findById(id);
    }
    @PostMapping()
    public UserEntity createUser(@RequestBody UserEntity user) throws Exception {
        return getService().save(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> updates) throws Exception {
        UserEntity user =getService().update(id, updates);
        return ResponseEntity.ok(user);
    }
@DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) throws Exception {
        getService().deleteById(id);
return ResponseEntity.ok(true);
}

}

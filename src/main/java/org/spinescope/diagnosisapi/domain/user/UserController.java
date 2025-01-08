package org.spinescope.diagnosisapi.domain.user;

import org.spinescope.diagnosisapi.domain.base.AbstractCrudController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/users")
public class UserController extends AbstractCrudController<UserEntity, Integer, UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @Override
    public boolean entityExists(UserEntity user) {
        return service.getByUsername(user.getUsername()).isPresent();
    }

}

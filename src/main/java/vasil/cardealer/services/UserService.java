package vasil.cardealer.services;


import org.springframework.security.core.userdetails.UserDetailsService;
import vasil.cardealer.models.service.UserServiceModel;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel register(UserServiceModel model);

    UserServiceModel findUserByUserName(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword);

    List<UserServiceModel> findAllUsers();

    void setUserRole(String id, String role);
}

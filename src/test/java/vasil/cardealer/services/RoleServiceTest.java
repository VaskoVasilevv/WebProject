package vasil.cardealer.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import vasil.cardealer.base.BaseTest;
import vasil.cardealer.models.entity.Role;
import vasil.cardealer.models.service.RoleServiceModel;
import vasil.cardealer.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RoleServiceTest extends BaseTest {
    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleService roleService;

    @Test
   public void testFindAllRoleWhenRolesExistsShouldReturnCorrect(){
        Role role1=new Role("ROLE_USER");
        Role role2=new Role("ROLE_MODERATOR");
        Role role3=new Role("ROLE_ADMIN");
        Role role4=new Role("ROLE_ROOT");
        List<Role> roles=new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roles.add(role4);

        Mockito.when(roleRepository.findAll()).thenReturn(roles);
        Set<RoleServiceModel>result=roleService.findAllRoles();

        Assert.assertEquals(roles.size(),result.size());
    }

    @Test
   public void testFindByAuthorityWhenInputIsValidShouldReturnCorrect(){
        String authority="moderator";
        Role role=new Role();
        role.setAuthority("ROLE_Moderator");
        Mockito.when(roleRepository.findByAuthority(authority)).thenReturn(role);
        RoleServiceModel result=roleService.findByAuthority(authority);

        Assert.assertEquals(role.getAuthority(),result.getAuthority());
    }
}
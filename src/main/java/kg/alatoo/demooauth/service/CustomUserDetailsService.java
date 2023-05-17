package kg.alatoo.demooauth.service;


import kg.alatoo.demooauth.config.CustomUserDetails;
import kg.alatoo.demooauth.model.Role;
import kg.alatoo.demooauth.model.User;
import kg.alatoo.demooauth.repo.RoleRepository;
import kg.alatoo.demooauth.repo.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomUserDetails(
                userRepository.findByUsername(username));
    }

    @Override
    public User createUser(User newUser) {
        return createUserWithRole(newUser, "USER");
    }

    private User createUserWithRole(User newUser, String roleName) {
        //TODO: check for already existence username, if the user with username already exists throw Exception
        newUser.setId(null);
/*
        newUser.setPassword(encoder.encode(newUser.getPassword()));
*/
        newUser.getRoles().add(getOrCreateRole(roleName));
        return userRepository.save(newUser);

    }

    protected Role getOrCreateRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findById(roleName);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        Role role = new Role(roleName);
        return roleRepository.save(role);
    }


    @Override
    public User createAdmin(User newAdmin) {
        return createUserWithRole(newAdmin, "ADMIN");
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return false;
    }
}

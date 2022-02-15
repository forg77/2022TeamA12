package edu.zstu.examsys.service;

import edu.zstu.examsys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (!username.equals("admin"))
//            throw new UsernameNotFoundException("user not found");
//        String password = passwordEncoder.encode("123");
//        User user = new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return user;
    }
}

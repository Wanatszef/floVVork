package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.Security.LoggedUser;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationUserDetailService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByUsername(username);
        if(user ==null)
        {
            throw new UsernameNotFoundException(username);
        }


        return new LoggedUser(user);
    }
}

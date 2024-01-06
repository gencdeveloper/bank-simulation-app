package com.cydeo.service.impl;

import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrinciple;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override //overwrite to loadUserByUsername
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we need to get our own user from database. how?
            User user = userRepository.findByUsername(username);

        //return some exception if user doesn't exist
        if(user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        //return user information as a UserDetails

        return new UserPrinciple(user);


    }
}
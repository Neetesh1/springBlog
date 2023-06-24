package com.blog.app.blog.services.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.app.blog.config.AppConstant;
import com.blog.app.blog.entities.Role;
import com.blog.app.blog.entities.User;
import com.blog.app.blog.exceptions.ResourceNotFoundException;
import com.blog.app.blog.payloads.Userdto;
import com.blog.app.blog.repositories.RoleRepo;
import com.blog.app.blog.repositories.UserRepo;
import com.blog.app.blog.services.UserService;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;


    @Override
    public Userdto createuser(Userdto userdto) {
        User user = dtoTouser(userdto);
        user = userRepo.save(user);
        userdto = userTodto(user);
        return userdto;
    }

    @Override
    public Userdto updateuser(Userdto userdto, int userid) {

        User user = userRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));
        user.setName(userdto.getName());
        user.setEmail(userdto.getEmail());
        user.setPassword(userdto.getPassword());
        user.setAbout(userdto.getAbout());
        User updatedUser = userRepo.save(user);
        Userdto userdto1 = this.userTodto(updatedUser);
        return userdto1;

    }

    @Override
    public Userdto getuserById(int userid) {

        User user = userRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));
        Userdto userdto = this.userTodto(user);
        return userdto;
    }

    @Override
    public List<Userdto> getallusers() {

        List<User> users = this.userRepo.findAll();
        // List<Userdto> userdtos = new ArrayList<>();
        // for(User user : users){
        // Userdto userdto = userTodto(user);
        // userdtos.add(userdto);
        // }
        List<Userdto> userdtos = users.stream().map(user -> this.userTodto(user)).toList();
        return userdtos;
    }

    @Override
    public void deleteuser(int userid) {
        User user = userRepo.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));
        userRepo.delete(user);

    }

    private User dtoTouser(Userdto userdto) {
        User user = this.modelMapper.map(userdto, User.class);
        // User user = new User();
        // user.setId(userdto.getId());
        // user.setName(userdto.getName());
        // user.setEmail(userdto.getEmail());
        // user.setPassword(userdto.getPassword());
        // user.setAbout(userdto.getAbout());
        return user;
    }

    private Userdto userTodto(User user) {
        Userdto userdto = this.modelMapper.map(user, Userdto.class);
        return userdto;
    }

    @Override
    public Userdto registerNewUser(Userdto userdto) {
        
        User user = dtoTouser(userdto);
        //encoded the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //get the roles
        Role role = this.roleRepo.findById(AppConstant.DEFAULT_ROLE_ID).get();
        //set the role to user
        user.getRoles().add(role);
        
        user = userRepo.save(user);

        userdto = userTodto(user);

        return userdto;
    }

}

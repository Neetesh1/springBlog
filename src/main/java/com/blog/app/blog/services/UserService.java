package com.blog.app.blog.services;

import java.util.List;

import com.blog.app.blog.payloads.Userdto;

public interface UserService {

    Userdto createuser(Userdto userdto);
    Userdto updateuser(Userdto userdto , int userid);
    Userdto getuserById(int userid);
    List<Userdto> getallusers();
    void deleteuser(int userid);

}

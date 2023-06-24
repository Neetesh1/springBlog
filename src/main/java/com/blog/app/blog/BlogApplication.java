package com.blog.app.blog;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.app.blog.config.AppConstant;
import com.blog.app.blog.entities.Role;
import com.blog.app.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		String password = this.passwordEncoder.encode("12345");
		System.out.println(password);

		try {
			
			//admin role
			Role role = new Role();
			role.setId(AppConstant.ADMIN_ROLE_ID);
			role.setName(AppConstant.ADMIN_ROLE_NAME);

			//defult role
			Role role2 = new Role();
			role2.setId(AppConstant.DEFAULT_ROLE_ID);
			role2.setName(AppConstant.DEFAULT_ROLE_NAME);

			List<Role> roles = List.of(role,role2);

			List<Role> resList =  this.roleRepo.saveAll(roles);

			resList.forEach( r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}

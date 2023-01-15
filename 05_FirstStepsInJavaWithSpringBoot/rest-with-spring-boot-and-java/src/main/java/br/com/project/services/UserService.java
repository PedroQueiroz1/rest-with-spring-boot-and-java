package br.com.project.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.stereotype.Service;

import br.com.project.controllers.PersonController;
import br.com.project.exceptions.ResourceNotFoundException;
import br.com.project.mapper.DozerMapper;
import br.com.project.model.User;
import br.com.project.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	UserRepository userRepository;


	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	// --------- FIND-BY-ID ------------


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Finding one user by name "+ username + "!");
		
		var user = userRepository.findByUsername(username);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username: " + username +" not found!");
		}
	}


}

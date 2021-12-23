package com.ckp.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ckp.blog.dto.JWTAuthResponseDto;
import com.ckp.blog.dto.LoginDto;
import com.ckp.blog.dto.SingUpDto;
import com.ckp.blog.model.Role;
import com.ckp.blog.model.User;
import com.ckp.blog.repository.RoleRepository;
import com.ckp.blog.repository.UserRepository;
import com.ckp.blog.security.JwtTokenProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Auth controller exposes siginin and signup REST APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@ApiOperation(value = "REST API to Register or Signup user to Blog app")
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// get token form tokenProvider
		String token = jwtTokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTAuthResponseDto(token));
	}
	
	@ApiOperation(value = "REST API to Register or Signup user to Blog app")
	@PostMapping("/singup")
	public ResponseEntity<?> userRegister(@RequestBody SingUpDto singUpDto) {
		// checking for username
		if (userRepository.existsByUsername(singUpDto.getUsername())) {
			return new ResponseEntity<>("username is alrady taken", HttpStatus.BAD_REQUEST);
		}
		// checking for email
		if (userRepository.existsByEmail(singUpDto.getEmail())) {
			return new ResponseEntity<>("email is alrady available ", HttpStatus.BAD_REQUEST);
		}

		User user = new User();
		user.setName(singUpDto.getName());
		user.setUsername(singUpDto.getUsername());
		user.setEmail(singUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(singUpDto.getPassword()));

		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));

		userRepository.save(user);

		return new ResponseEntity<>("user registered successfully", HttpStatus.OK);

	}

}

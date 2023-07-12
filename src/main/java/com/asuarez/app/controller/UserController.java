package com.asuarez.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asuarez.app.entity.User;
import com.asuarez.app.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	//Crear nuevo usuario
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
	}
	
	//Consultar un usuario
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable(value = "id") Long userId){
		Optional<User> oUser = userService.findById(userId);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
	}
	
	//Actualizar un usuario
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody User userUpdate, @PathVariable(value = "id") Long userId){
		
		Optional<User> user = userService.findById(userId);
		
		if(user.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		//Esta es otra forma de copiar todas las propiedades de un objeto, incluso el id.
		//BeanUtils.copyProperties(userUpdate, user.get());
		user.get().setName(userUpdate.getName());
		user.get().setLastname(userUpdate.getLastname());
		user.get().setEmail(userUpdate.getEmail());
		user.get().setEnable(userUpdate.getEnable());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
	}
	
	//Borrar un usuario
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") Long userId){
		
		if(userService.findById(userId).isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteById(userId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<User> readAllUsers(){
		List<User> users = ((List<User>)userService.findAll());
		
		//List<User> users2 = StreamSupport.stream(userService.findAll().spliterator(), false).collect(Collectors.toList());
		
		return users;
	}
	
}//end

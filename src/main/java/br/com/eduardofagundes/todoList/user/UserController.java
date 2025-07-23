package br.com.eduardofagundes.todoList.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;




@PostMapping("/")
    public ResponseEntity creat(@RequestBody UserModel userModel){
    var user = this.userRepository.findByUsername(userModel.getUserName());

    if (user != null){
        System.out.println("Usuário já existe");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
    }

    var passwordHashred =BCrypt.withDefaults().hashToString(12,userModel.getPassword().toCharArray());

    UserModel.setPassword(passwordHashred);
    var userCreated = this.userRepository.save(userModel);
    return ResponseEntity.(HttpStatus.CREATED).body(userCreated);


}}

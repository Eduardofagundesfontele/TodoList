package br.com.eduardofagundes.todoList.user;

import at.favre.lib.crypto.bcrypt.BCrypt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @RestController e definir rota base para o recurso
@RestController
@RequestMapping("/users")
public class UserController {

    //injeção de dependência para o repositório
    @Autowired
    private IUserRepository userRepository;

    // ✅ POST para criar um novo usuário
    @PostMapping("/")
    public ResponseEntity creat(@RequestBody UserModel userModel) {
        //Verifica se já existe usuário com o mesmo nome
        var user = this.userRepository.findByUsername(userModel.getUserName());

        if (user != null) {

            System.out.println("Usuário já existe");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        // usar BCrypt para gerar hash seguro da senha
        var passwordHashed = BCrypt
                .withDefaults()
                .hashToString(12, userModel.getPassword().toCharArray());


        userModel.setPassword(passwordHashed); // objeto com a senha criptografada

        // Salva o usuário no banco
        var userCreated = this.userRepository.save(userModel);

        //  Retorna 201 Created com o objeto criado
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}

package br.com.eduardofagundes.todoList.user;


import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import jakarta.persistence.Entity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity(name ="tb_users")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")

    private UUID id;
    @Column(unique = true)
    private String userName;
    private String name;
    private String password;

    //quando foi criado
    @CreationTimestamp
    private LocalDateTime createdAt;

}

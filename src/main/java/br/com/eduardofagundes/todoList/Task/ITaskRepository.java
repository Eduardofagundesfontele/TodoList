package br.com.eduardofagundes.todoList.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

//é uma interface que simplifica o acesso a dados
public interface ITaskRepository extends JpaRepository<TaskModel,UUID> {
    List<TaskModel> findByUser(UUID idUser);
    TaskModel findByIdAndIdUser( UUID id , UUID idUser);
}

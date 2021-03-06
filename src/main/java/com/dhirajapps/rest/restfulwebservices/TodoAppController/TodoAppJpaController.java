package com.dhirajapps.rest.restfulwebservices.TodoAppController;

import com.dhirajapps.rest.restfulwebservices.TodoAppController.todo.Todo;
import com.dhirajapps.rest.restfulwebservices.TodoAppController.todo.TodoHardCodedService;
import com.dhirajapps.rest.restfulwebservices.TodoAppController.todo.TodoJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class TodoAppJpaController {

    @Autowired
    private TodoHardCodedService todoService;

    @Autowired
    private TodoJpaRepository todoJpaRepository;

    @GetMapping("/jpa/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoJpaRepository.findByUsername(username);
//        return todoService.findAll();
    }

    @GetMapping("/jpa/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable long id) {

        return todoJpaRepository.findById(id).get();
//        return todoService.findById(id);
    }

    @PutMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo (@PathVariable String username, @PathVariable long id, @RequestBody Todo todo) {
        todo.setUsername(username);
        Todo todoUpdated = todoJpaRepository.save(todo);
        return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
    }

    @PostMapping("/jpa/users/{username}/todos/")
    public ResponseEntity<Todo> createTodo (@PathVariable String username, @RequestBody Todo todo) {

        todo.setUsername(username);
        Todo createdTodo = todoJpaRepository.save(todo);

        // Location
        // get current resource url and update id of new url with created todo id

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/jpa/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long id) {

        todoJpaRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}

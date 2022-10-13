package com.blog.demo.controller;

import com.blog.demo.entities.PostEntity;
import com.blog.demo.service.PostService;
import com.blog.demo.dto.PostDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    @ApiOperation("Este método es para crear un usuario, sólo el rol ROLE_USER podrá realizar esto.")
    @Operation(description = "Si no la realiza un rol USER, dará un 401 como código de error")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401"),
            @ApiResponse(responseCode = "400")

    })
    public ResponseEntity createPost(@RequestBody PostDTO postDto) {
        postService.createPost(postDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation("Este método es para mostrar todos los posts, no es necesario estar logueado para tener los datos")
    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    @ApiOperation("Método para modificar post mediante id. Sólo puede actualizar el Post un rol USER")
    public ResponseEntity<PostDTO>updatePost(@PathVariable String id, @RequestBody PostDTO postDTO){
        postService.updatePost(id, postDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    @ApiOperation("Método para eliminar post mediante id. Sólo puede eliminar el Post un rol USER")
    public ResponseEntity<?>deletePost(@PathVariable String id){
        postService.deletePostIfExist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @ApiOperation("Método que nos muestra un post, mediante el id")
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable @RequestBody String  id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

}

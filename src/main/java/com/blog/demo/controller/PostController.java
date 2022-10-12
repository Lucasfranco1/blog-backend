package com.blog.demo.controller;

import com.blog.demo.entities.PostEntity;
import com.blog.demo.service.PostService;
import com.blog.demo.dto.PostDTO;
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
    public ResponseEntity createPost(@RequestBody PostDTO postDto) {
        postService.createPost(postDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDTO>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PostDTO>updatePost(@PathVariable String id, @RequestBody PostDTO postDTO){
        postService.updatePost(id, postDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?>deletePost(@PathVariable String id){
        postService.deletePostIfExist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable @RequestBody String  id) {
        return new ResponseEntity<>(postService.readSinglePost(id), HttpStatus.OK);
    }

}

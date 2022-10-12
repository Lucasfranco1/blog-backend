package com.blog.demo.service;

import com.blog.demo.auth.security.JwtProvider;
import com.blog.demo.auth.service.UserService;
import com.blog.demo.dto.PostDTO;
import com.blog.demo.entities.PostEntity;
import com.blog.demo.exceptions.PostNotFoundException;
import com.blog.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private UserService authService;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Transactional
    public List<PostDTO> showAllPosts() {
        List<PostEntity> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    @Transactional
    public void createPost(PostDTO postDto) {
        PostEntity post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }

    public void updatePost(String id, PostDTO postDTO){
        postRepository.findById(id).map(postJpa ->{
            postJpa.setTitle(postDTO.getTitle());
            postJpa.setContent(postDTO.getContent());
            postJpa.setUpdatedOn(Instant.now());
            postJpa.setUsername(postDTO.getUsername());
            return postRepository.save(postJpa);
        }).orElseThrow(()->new PostNotFoundException(id));
    }

    public void deletePostIfExist(String id){
        postRepository.findById(id).ifPresent(postRepository::delete);
    }

    @Transactional
    public PostDTO readSinglePost(String id) {
        PostEntity post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }

    private PostDTO mapFromPostToDto(PostEntity post) {
        PostDTO postDto = new PostDTO();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private PostEntity mapFromDtoToPost(PostDTO postDto) {
        PostEntity post = new PostEntity();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        post.setUsername(username);
        post.setCreatedOn(Instant.now());
        post.setUpdatedOn(Instant.now());
        return post;
    }

}

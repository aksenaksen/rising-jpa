package com.example.demo.repository;

import com.example.demo.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepositoryCustom {
    List<User> findByName(String name);

    Optional<User> findById(Integer id);

    List<User> findAll();

}

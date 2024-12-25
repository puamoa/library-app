package com.group.libraryapp.service.user;

import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.domain.user.UserRepository;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.request.UserUpdateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceV2 {
  private final UserRepository userRepository;

  public UserServiceV2(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public void saveUsers() {
    userRepository.save(new User("A", 10));
    userRepository.save(new User("B", 20));
    userRepository.save(new User("C", 30));
  }

  @Transactional(readOnly = true)
  public void findUsers() {
    User user1 = userRepository.findById(1L).get();
    User user2 = userRepository.findById(1L).get();
    User user3 = userRepository.findById(1L).get();
  }

  @Transactional
  public void saveUser(UserCreateRequest request) {
    userRepository.save(new User(request.getName(), request.getAge()));
    //throw new IllegalArgumentException();
  }

  @Transactional(readOnly = true)
  public List<UserResponse> getUsers() {
    return userRepository.findAll().stream()
        .map(UserResponse::new)
        .collect(Collectors.toList());
  }


  @Transactional
  public void updateUser(UserUpdateRequest request) {
    User user = userRepository.findById(request.getId())
        .orElseThrow(IllegalArgumentException::new);

    user.updateName(request.getName());
  }


  @Transactional
  public void deleteUser(String name) {
    User user = userRepository.findByName(name).orElseThrow(IllegalArgumentException::new);
    userRepository.delete(user);
  }


}



package com.group.libraryapp.domain.user;

import com.group.libraryapp.domain.user.loanhistory.UserLoanHistory;
import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id = null;

  @Column(nullable = false, length = 20)
  private String name;

  private Integer age;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserLoanHistory> userLoanHistories = new ArrayList<>();

  protected User() {  }

  public User(String name, Integer age) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException(String.format("잘못된 name(%s)이 들어왔습니다", name));
    }
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public Integer getAge() {
    return age;
  }

  public Long getId() {
    return id;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void removeOneHistory() {
    userLoanHistories.removeIf(history -> "책1".equals(history.getBookNamne()));
  }

  public void loanBook(String bookNamne) {
    this.userLoanHistories.add(new UserLoanHistory(this, bookNamne));
  }

  public void returnBook(String bookNamne) {
    UserLoanHistory targetHistory = this.userLoanHistories.stream()
        .filter(history -> history.getBookNamne().equals(bookNamne))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    targetHistory.doReturn();
  }
}

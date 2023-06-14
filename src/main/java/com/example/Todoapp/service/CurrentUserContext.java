package com.example.Todoapp.service;


import com.example.Todoapp.entity.User;

import java.util.SplittableRandom;

public class CurrentUserContext {
  private static ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();

  public static synchronized String getCurrentUser() {
    return CURRENT_USER.get();
  }

  public static synchronized void setCurrentUser(String username) {
    CURRENT_USER.set(username);
  }
}
package controller;

import entities.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserController
{
    private final Map<String, User> users = new ConcurrentHashMap<>();

    public User createUser(String name)
    {
        User user = new User(name);
        users.put(user.getId(), user);
        return user;
    }

    public User getUser(String userId)
    {
        return users.get(userId);
    }
}

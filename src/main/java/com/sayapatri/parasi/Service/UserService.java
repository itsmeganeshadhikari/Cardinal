package com.sayapatri.parasi.Service;

import com.sayapatri.parasi.Model.User;
import com.sayapatri.parasi.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByConfirmationToken(String token) {
        return  userRepository.findByConfirmationToken(token);
    }

    public void saveuser(User user) {
        userRepository.save(user);
    }

    public User findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email,password);
    }
}

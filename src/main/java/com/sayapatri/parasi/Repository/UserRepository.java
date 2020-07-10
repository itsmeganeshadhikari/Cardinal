package com.sayapatri.parasi.Repository;

import com.sayapatri.parasi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>
{
    User findByEmail(String email);

     User findByConfirmationToken(String token);

    User findByEmailAndPassword(String email, String password);

//    User getUserByUsername(String username);
}

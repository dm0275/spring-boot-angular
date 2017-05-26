package com.gmail.dm0275.repositories;

import com.gmail.dm0275.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Daniel on 5/26/2017.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);
}

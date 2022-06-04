package com.cms.deutsche.repository;

import com.cms.deutsche.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String emailId);
}

package com.masflam.library.data;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
	public User findByUsername(String username) {
		return find("username", username).firstResult();
	}
}

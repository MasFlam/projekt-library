package com.masflam.library;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.masflam.library.data.Book;
import com.masflam.library.data.BookRepository;
import com.masflam.library.data.User;
import com.masflam.library.data.UserRepository;

import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
@IfBuildProfile("dev")
public class Meta {
	
	@Inject
	public UserRepository userRepo;
	
	@Inject
	public BookRepository bookRepo;
	
	@Transactional
	public void populateDb(@Observes StartupEvent evt) {
		var user = new User();
		user.setUsername("user");
		user.setPassword(BcryptUtil.bcryptHash("pass"));
		user.setRoles("");
		userRepo.persist(user);
		
		var book = new Book();
		book.setIsbn("123456789");
		book.setTitle("Alpha Arguments");
		book.setAuthors("Alex Amberg");
		book.setDescription("Lorem ipsum");
		bookRepo.persist(book);
		
		book = new Book();
		book.setIsbn("987654321");
		book.setTitle("Beta Bloom");
		book.setAuthors("Bob Berg");
		book.setDescription("Dolor");
		bookRepo.persist(book);
		
		book = new Book();
		book.setIsbn("4242424242");
		book.setTitle("Gamma Groove");
		book.setAuthors("Carl Crackman");
		book.setDescription("Sit amet");
		bookRepo.persist(book);
	}
}

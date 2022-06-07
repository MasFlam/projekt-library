package com.masflam.library;

import java.net.URI;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.masflam.library.data.Book;
import com.masflam.library.data.BookRepository;
import com.masflam.library.data.User;
import com.masflam.library.data.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;

@Path("/")
@Produces(MediaType.TEXT_HTML)
@ApplicationScoped
public class WebResource {
	
	@Inject
	public UserRepository userRepo;
	
	@Inject
	public BookRepository bookRepo;
	
	@GET
	@Path("/")
	@PermitAll
	public TemplateInstance index(@QueryParam("formerror") String formError, @Context SecurityContext sec) {
		User user;
		if (sec.getUserPrincipal() != null) {
			user = userRepo.findByUsername(sec.getUserPrincipal().getName());
		} else {
			user = null;
		}
		return Templates.index(formError, user);
	}
	
	@Path("/signup")
	@PermitAll
	@POST
	@Transactional
	public Response signup(@FormParam("username") String username, @FormParam("password") String password) {
		if (username == null || password == null || username.isBlank() || password.isBlank()) {
			return Response.status(Response.Status.FOUND).location(URI.create("/?formerror=Username%20and%20password%20cannot%20be%20blank")).build();
		}
		if (userRepo.findByUsername(username) != null) {
			return Response.status(Response.Status.FOUND).location(URI.create("/?formerror=Username%20taken")).build();
		}
		var user = new User();
		user.setUsername(username);
		user.setPassword(BcryptUtil.bcryptHash(password));
		user.setRoles("");
		userRepo.persist(user);
		return Response.status(Response.Status.FOUND).location(URI.create("/")).build();
	}
	
	@Authenticated
	@GET
	@Path("/books")
	public TemplateInstance books(
		@QueryParam("searched") boolean searched,
		@QueryParam("isbn") String isbn,
		@QueryParam("title") String title,
		@QueryParam("authors") String authors,
		@QueryParam("description") String description
	) {
		List<Book> books;
		if (searched) {
			books = bookRepo.find(
				"(isbn LIKE CONCAT('%', ?1, '%')) AND " +
				"(title LIKE CONCAT('%', ?2, '%')) AND " +
				"(authors LIKE CONCAT('%', ?3, '%')) AND " +
				"(description LIKE CONCAT('%', ?4, '%'))",
				isbn, title, authors, description
			).list();
		} else {
			books = bookRepo.listAll();
		}
		return Templates.books(searched, books);
	}
	
	@Authenticated
	@GET
	@Path("/add")
	public TemplateInstance add() {
		return Templates.add();
	}
	
	@Authenticated
	@Path("/add")
	@POST
	@Transactional
	public Response addPost(
		@FormParam("isbn") String isbn,
		@FormParam("title") String title,
		@FormParam("authors") String authors,
		@FormParam("description") String description
	) {
		var book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setAuthors(authors);
		book.setDescription(description);
		bookRepo.persist(book);
		return Response.status(Response.Status.FOUND).location(URI.create("/books")).build();
	}
	
	@Authenticated
	@GET
	@Path("/edit")
	public Response edit(@QueryParam("id") long id) {
		Book book = bookRepo.findById(id);
		if (book == null) {
			return Response.status(Response.Status.FOUND).location(URI.create("/books")).build();
		}
		return Response.ok(Templates.edit(book)).build();
	}
	
	@Authenticated
	@Path("/edit")
	@POST
	@Transactional
	public Response editPost(
		@FormParam("id") long id,
		@FormParam("isbn") String isbn,
		@FormParam("title") String title,
		@FormParam("authors") String authors,
		@FormParam("description") String description
	) {
		Book book = bookRepo.findById(id);
		if (book != null) {
			book.setIsbn(isbn);
			book.setTitle(title);
			book.setAuthors(authors);
			book.setDescription(description);
			bookRepo.persist(book);
		}
		return Response.status(Response.Status.FOUND).location(URI.create("/books")).build();
	}
	
	@Authenticated
	@Path("/delete")
	@POST
	@Transactional
	public Response deletePost(@QueryParam("id") long id) {
		bookRepo.deleteById(id);
		return Response.status(Response.Status.FOUND).location(URI.create("/books")).build();
	}
	
	@Authenticated
	@GET
	@Path("/search")
	public TemplateInstance search() {
		return Templates.search();
	}
}

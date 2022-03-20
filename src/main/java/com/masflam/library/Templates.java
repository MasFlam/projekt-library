package com.masflam.library;

import java.util.List;

import com.masflam.library.data.Book;
import com.masflam.library.data.User;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate
public class Templates {
	public static native TemplateInstance index(String formError, User user);
	public static native TemplateInstance books(boolean searched, List<Book> books);
	public static native TemplateInstance add();
	public static native TemplateInstance edit(Book book);
	public static native TemplateInstance search();
}

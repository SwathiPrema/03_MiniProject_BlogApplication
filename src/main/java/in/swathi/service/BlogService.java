package in.swathi.service;

import java.util.List;

import in.swathi.binding.CommentForm;
import in.swathi.binding.LoginForm;
import in.swathi.binding.PostForm;
import in.swathi.binding.RegisterForm;
import in.swathi.entity.Blogs;
import in.swathi.entity.Comments;

public interface BlogService {

	public boolean saveUserDetails(RegisterForm form);

	public boolean userLogin(LoginForm login);

	public String userPost(PostForm post);

	List<Blogs> getPosts(PostForm form);

	public String peopleComments(CommentForm form);

	List<Comments> getComments(CommentForm form);
	
	List<Comments> getBlogComments(CommentForm form);
	
	//List<Blogs> getSearchPosts(PostForm form);

}

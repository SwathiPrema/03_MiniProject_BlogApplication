package in.swathi.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.swathi.binding.CommentForm;
import in.swathi.binding.LoginForm;
import in.swathi.binding.PostForm;
import in.swathi.binding.RegisterForm;
import in.swathi.entity.Blogs;
import in.swathi.entity.Comments;
import in.swathi.entity.UserDetails;
import in.swathi.repo.BlogRepository;
import in.swathi.repo.CommentRepository;
import in.swathi.repo.UserRepository;
import in.swathi.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private HttpSession session;

	@Autowired
	private BlogRepository blogRepo;

	@Autowired
	private CommentRepository commentRepo;

	@Override
	public boolean saveUserDetails(RegisterForm form) {

		UserDetails findByemail = userRepo.findByemail(form.getEmail());

		if (findByemail != null) {

			return false;
		}

		UserDetails user = new UserDetails();

		BeanUtils.copyProperties(form, user);

		userRepo.save(user);

		return true;
	}

	@Override
	public boolean userLogin(LoginForm login) {

		UserDetails entity = userRepo.findByemailAndPassword(login.getEmail(), login.getPassword());

		if (entity == null) {

			return false;
		}

		session.setAttribute("userId", entity.getUserId());
		return true;
	}

	@Override
	public String userPost(PostForm post) {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDetails> user = userRepo.findById(userId);
		UserDetails userDetails = user.get();

		if (post.getBlogId() != null) {

			Optional<Blogs> findById = blogRepo.findById(post.getBlogId());

			Blogs blogs = findById.get();

			blogs.setTitle(post.getTitle());
			blogs.setContent(post.getContent());
			blogs.setDescription(post.getDescription());
			blogs.setUserDetails(userDetails);

			blogRepo.save(blogs);
			return "Record is Updated successfully";
		}

		Blogs blog = new Blogs();

		BeanUtils.copyProperties(post, blog);

		blog.setUserDetails(userDetails);

		blogRepo.save(blog);

		return "Record saved successfully";

	}

	@Override
	public List<Blogs> getPosts(PostForm form) {

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDetails> findById = userRepo.findById(userId);

		if (findById.isPresent()) {

			UserDetails detailsEntity = findById.get();

			List<Blogs> blogDetails = detailsEntity.getBlogDetails();

			return blogDetails;

		}
		return null;
	}

	@Override
	public String peopleComments(CommentForm form) {

		Integer blogId = (Integer) session.getAttribute("blogId");

		Optional<Blogs> blogs = blogRepo.findById(blogId);

		Blogs blogs2 = blogs.get();

		Comments comment = new Comments();

		BeanUtils.copyProperties(form, comment);

		comment.setBlogDetails(blogs2);

		commentRepo.save(comment);

		return "Record saved successfully";
	}

	@Override
	public List<Comments> getComments(CommentForm form) {

		CommentForm commentform = new CommentForm();

		Integer userId = (Integer) session.getAttribute("userId");

		Optional<UserDetails> findById = userRepo.findById(userId);

		if (findById.isPresent()) {

			UserDetails userDetails = findById.get();

			List<Blogs> blogDetails = userDetails.getBlogDetails();

			List<Comments> listOfComments = userDetails.getBlogDetails().stream()
					.flatMap(e -> e.getCommentDetails().stream()).collect(Collectors.toList());

			return listOfComments;

		}

		return null;
	}

	@Override
	public List<Comments> getBlogComments(CommentForm form) {

		Integer blogId = (Integer) session.getAttribute("blogId");

		Optional<Blogs> findById = blogRepo.findById(blogId);

		if (findById.isPresent()) {

			Blogs blogs = findById.get();

			List<Comments> commentDetails = blogs.getCommentDetails();

			return commentDetails;

		}

		return null;
	}

	
}

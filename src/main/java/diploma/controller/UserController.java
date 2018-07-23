package diploma.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import diploma.entity.Product;
import diploma.entity.User;
import diploma.entity.UserProduct;
import diploma.repository.ProductRepository;
import diploma.repository.UserProductRepository;
import diploma.repository.UserRepository;
import diploma.responses.Response;
import diploma.security.jwt.JwtUtil;
import diploma.service.ProductService;
import diploma.service.UserService;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserRepository userDao;

	@Autowired
	ProductRepository productDao;

	@Autowired
	UserProductRepository userProductDao;

	@Autowired
	ProductService productService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping
	@RequestMapping(value = "/newUser")
	public ResponseEntity<Response<User>> create(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		Response<User> response = new Response<User>();
		try {
			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) userService.createUser(user);
			response.setData(userPersisted);
		} catch (DuplicateKeyException dE) {
			response.getErrors().add("E-mail already registered");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult result) {
		if (user.getEmail() == null && user.getPassword() == null) {
			result.addError(new ObjectError("User", "Email no information"));
			result.addError(new ObjectError("User", "Password no information"));
		} else if (user.getPassword() == null) {
			result.addError(new ObjectError("User", "Password no information"));
		} else if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "Email no information"));
		}
	}

	@GetMapping(value = "/{page}/{count}")
	public ResponseEntity<Response<Page<User>>> findAll(@PathVariable int page, @PathVariable int count) {
		Response<Page<User>> response = new Response<Page<User>>();
		Page<User> users = userService.findAll(page, count);
		response.setData(users);
		return ResponseEntity.ok(response);
	}

	// ALL PRODUCTS OF USERS
	@GetMapping("/allCart")
	public List<UserProduct> findUserProducts() {
		return userProductDao.findAll();
	}

	// RETURNS ALL PRODUCTS OF SPECIFIC USER
	@GetMapping("/cart")
	@PreAuthorize("hasAnyRole('USER')")
	public List<Product> findProductsOfUser(HttpServletRequest request) {
		String authToken = request.getHeader("Authorization");
		String email = jwtUtil.getUsernameFromToken(authToken);
		User user = userDao.findByEmail(email);
		return productService.findProductsOfUser(user.getId());
	}

	// RETURNS ALL THE PRODUCTS
	@GetMapping("/products")
	public List<Product> findProducts() {
		return productDao.findAll();
	}

	// RETURNS ONE PRODUCT
	@GetMapping("/products/{id}")
	public Product findProduct(@PathVariable long id) {
		return productDao.findOne(id);
	}

}

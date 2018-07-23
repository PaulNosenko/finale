package diploma;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import diploma.entity.Product;
import diploma.entity.User;
import diploma.entity.UserProduct;
import diploma.enums.Role;
import diploma.repository.ProductRepository;
import diploma.repository.UserProductRepository;
import diploma.repository.UserRepository;

@SpringBootApplication
public class DiplomaApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(DiplomaApplication.class, args);
	}

	@Autowired
	UserRepository userDao;

	@Autowired
	ProductRepository productDao;

	@Autowired
	UserProductRepository userProductDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	/*
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User user = new User();
		user.setEmail("pavel@gmail.com");
		user.setPassword(passwordEncoder.encode("1234567"));
		user.setRole(Role.ROLE_ADMIN);

		User find = userRepository.findByEmail("pavel@gmail.com");
		if (find == null) {
			userRepository.save(user);
		}


		
	}*/

	@Override
	public void run(String... strings) throws Exception {
		
		userDao.save(new User(null, "email@gmail.com", "name", passwordEncoder.encode("pas123"), Role.ROLE_USER));
		
		productDao.save(new Product(null, "iPhone 7", "desc", 500, "image.png"));	
		productDao.save(new Product(null, "iPhone X", "desc", 1000, "X.png"));	
		
		userProductDao.save(new UserProduct(null, userDao.findOne((long) 1), productDao.findOne((long) 1)));
		userProductDao.save(new UserProduct(null, userDao.findOne((long) 1), productDao.findOne((long) 2)));
		
	}

}

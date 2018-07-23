package diploma.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import diploma.entity.Product;
import diploma.entity.UserProduct;
import diploma.repository.UserProductRepository;

@Service
public class ProductService {

	@Autowired
	private UserProductRepository userProductDao;
	
	public List<Product> findProductsOfUser(long id){
		ArrayList<Product> products = new ArrayList<>();
		for(UserProduct ur : userProductDao.findAll()) {
			if(ur.getUser().getId() == id) {
				products.add(ur.getProduct());
			}
		}
		return products;
	}
}

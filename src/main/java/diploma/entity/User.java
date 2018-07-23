package diploma.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import diploma.enums.Role;

@Entity
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message="Email is required")
	@Email(message="Email invalid")
	@Column(unique=true)
	private String email;
	
	@Column(name = "name")
	private String name;
	
	@NotBlank(message="Password is required")
	@Size(min=6)
	private String password;
	
	private Role role;

	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<UserProduct> userProducts;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public List<UserProduct> getUserProducts() {
		return userProducts;
	}


	public void setUserProducts(List<UserProduct> userProducts) {
		this.userProducts = userProducts;
	}

	
	public User(Long id, String email, String name, String password, Role role) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
	}


	public User() {}
	
}

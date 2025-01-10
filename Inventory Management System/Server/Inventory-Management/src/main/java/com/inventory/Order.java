package com.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Autowired
    @ManyToOne // Assuming there is a many-to-one relationship with Product
    @JoinColumn(name = "product_id")
     private Product product;

    private int quantity;
    private String status;  // 'pending' or 'completed'
    private LocalDate orderdate;
    
    @Autowired
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

    public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public LocalDate getOrderdate() {
		return orderdate;
	}



	public void setOrderdate(LocalDate orderdate) {
		this.orderdate = orderdate;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	// Default constructor
    public Order() {
        super();
    }


    
	@Override
	public String toString() {
		return "Order [id=" + id + ", product=" + product + ", quantity=" + quantity + ", status=" + status
				+ ", orderdate=" + orderdate + ", user=" + user + "]";
	}


	/*public Order(Long id, Product product, int quantity, String status, LocalDate orderdate, User user) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.status = status;
		this.orderdate = orderdate;
		this.user = user;
	}*/
    
}

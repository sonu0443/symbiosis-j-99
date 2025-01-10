package com.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.inventory.Order;
import com.inventory.Product;
import com.inventory.User;
import com.inventory.repository.OrderRepository;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.UserRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private UserRepository userRepository;

    // Get all orders
    @GetMapping("/getorder")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get an order by ID
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Order orderDetails) {
    	orderDetails.setId((long) 2);
    	System.out.println("Product Id " +orderDetails.getId());
        // Step 1: Fetch the product by ID
        //Optional<Product> optionalProduct = productRepository.findById(orderDetails.getProduct().getId());
    	Optional<Product> optionalProduct = productRepository.findById(orderDetails.getId());
    	
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Step 2: Check if sufficient stock is available
            if (product.getStockQuantity() >= orderDetails.getQuantity()) {
                // Reduce the stock quantity after creating the order
                product.setStockQuantity(product.getStockQuantity() - orderDetails.getQuantity());
                productRepository.save(product); // Save the updated product stock
                
                // Step 3: Set the order details and save the order
                orderDetails.setProduct(product);  // Link the product to the order
                orderDetails.setOrderdate(LocalDate.now());  // Set order date to current date
                
                // Step 4: Save the order in the database
                Order savedOrder = orderRepository.save(orderDetails);

                // Return the created order as the response
                return ResponseEntity.ok(savedOrder);
            } else {
                return ResponseEntity.badRequest().body(null);  // Insufficient stock
            }
        } else {
            return ResponseEntity.badRequest().body(null);  // Product not found
        }
    }




    // Update an existing order
    @PutMapping("/update/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setProduct(orderDetails.getProduct());
            order.setQuantity(orderDetails.getQuantity());
            order.setStatus(orderDetails.getStatus());
            order.setOrderdate(orderDetails.getOrderdate());
            return ResponseEntity.ok(orderRepository.save(order));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an order
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/count/{userId}")
    public Map<String, Long> getOrderCountsByUserId(@PathVariable Long userId) {
        long pendingOrders = orderRepository.countPendingOrdersByUserId(userId);
        long completedOrders = orderRepository.countCompletedOrdersByUserId(userId);

        // Prepare the response as a map
        Map<String, Long> response = new HashMap<>();
        response.put("pendingOrders", pendingOrders);
        response.put("completedOrders", completedOrders);

        return response;
    }
}

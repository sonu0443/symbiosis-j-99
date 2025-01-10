import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { ProductService } from '../../service/product.service';
import { OrderService } from '../../service/order.service';  // Import OrderService
import { User } from '../../model/user';
import { Product } from '../../model/product';
import { Order } from '../../model/order';  // Import Order model
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  user: User = { id: 0, name: '', email: '', password: '' };
  totalProducts: number = 0;
  pendingOrders: number = 0;
  completedOrders: number = 0;
  recentActivities: any[] = [];

  constructor(
    private userService: UserService,
    private productService: ProductService,
    private orderService: OrderService  // Inject OrderService
  ) { }

  ngOnInit(): void {
    this.loadUserData();  // First load the user data
    this.loadTotalProducts();
    this.loadRecentActivities();
  }

  // Load user data and fetch orders only after the user data is loaded
  loadUserData() {
    // Dynamically fetch the user ID (you can replace 1 with session or authentication-based logic)
    const userId = 1;  // This should come from your authentication/session context
    this.userService.getUserById(userId).subscribe(
      (userData: User) => {
        this.user = userData;
        // Once user data is loaded, load order data
        if (this.user.id) {
          this.loadOrderData(this.user.id);  // Pass the dynamic user ID
        } else {
          console.error('User ID is not available');
        }
      },
      error => {
        console.error('Error fetching user data', error);
      }
    );
  }

  loadTotalProducts() {
    this.productService.getProducts().subscribe(
      (products: Product[]) => {
        this.totalProducts = products.length;
      },
      error => {
        console.error('Error fetching product data', error);
      }
    );
  }

  // Fetch pending and completed orders based on the dynamic user ID
  loadOrderData(userId: number) {
    if (userId) {
      this.orderService.getOrderCountsByUserId(userId).subscribe(
        (orderCounts: { pendingOrders: number, completedOrders: number }) => {
          this.pendingOrders = orderCounts.pendingOrders;
          this.completedOrders = orderCounts.completedOrders;
        },
        (error) => {
          console.error('Error fetching orders count', error);
          if (error.status === 404) {
            console.error(`No orders found for user with ID ${userId}`);
          }
        }
      );
    } else {
      console.error('User ID is not available');
    }
  }

  loadRecentActivities() {
    const currentDate = new Date().toISOString().split('T')[0];
    this.recentActivities = [
      { message: 'Added new product', date: currentDate },
      { message: 'Updated stock quantity', date: currentDate },
      { message: 'Completed an order', date: currentDate }
    ];
  }
}

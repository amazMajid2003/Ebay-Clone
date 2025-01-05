

# eBay Clone App

### Overview

The eBay Clone App is a feature-rich mobile application developed using **Android Studio** and **Kotlin**. It replicates key functionalities of eBay, including user authentication, product search, saving items, adding to the cart, placing orders, and tracking recently viewed products. The app integrates **Firebase Authentication** for secure login and sign-up and leverages the **Firebase Realtime Database** to store and retrieve product, category, order, and user-related data.

---

## Features

1. **User Authentication**
   - Users can securely sign up or log in using Firebase Authentication.
   - Password-based authentication ensures user data protection.

2. **Product Catalog**
   - Products are organized into categories (e.g., Electronics, Antiques, Art, etc.).
   - Each product displays details like name, brand, price, discount, and an image.

3. **Search Functionality**
   - Users can search for products using a keyword-based search.

4. **Wishlist & Cart**
   - Save products for later (wishlist functionality).
   - Add products to the cart and manage quantities before placing an order.

5. **Order Management**
   - Place orders seamlessly and view order history.
   - Track total order amounts.

6. **Recently Viewed Products**
   - Displays a list of recently viewed products for easy access.

---

## Technology Stack

- **Frontend**: XML layouts for designing UI.
- **Backend**: Kotlin in Android Studio.
- **Database**: Firebase Realtime Database.
- **Authentication**: Firebase Authentication.

---

## Firebase Realtime Database Structure

### Categories
Stores product categories along with their names and images.

```json
"Category": {
  "c1": {
    "Name": "Antique",
    "turl": "image_url_here"
  },
  ...
}
```

### Products
Stores all product details, including name, brand, category, price, discount, and image URL.

```json
"Products": {
  "pid1": {
    "Brand": "Apple",
    "CategoryName": "Cell Phones & Accessories",
    "Name": "Iphone13",
    ...
  },
  ...
}
```

### Orders
Contains user order details such as order items, total amount, and user information.

```json
"Orders": {
  "order_id": {
    "orderDate": "timestamp",
    "orderItems": [
      {
        "productName": "Iphone13",
        "discountedPrice": 800,
        ...
      }
    ],
    "totalAmount": 845,
    "userId": "user_id_here"
  }
}
```

---

## Setup Instructions

### Prerequisites
1. **Android Studio**: Latest version installed on your system.
2. **Firebase Console**: A Firebase project set up with Realtime Database and Authentication enabled.

### Steps to Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/ebay-clone-app.git
   ```
2. Open the project in Android Studio.
3. Configure Firebase:
   - Add your app to Firebase Console.
   - Download the `google-services.json` file from Firebase and place it in the `app/` directory.
   - Enable **Realtime Database** and **Authentication** in Firebase Console.

4. Sync the project with Gradle to install dependencies.
5. Run the app on an emulator or a physical device.

---

## Key Functionalities and Screens

### 1. **Login and Sign Up**
   - Uses Firebase Authentication for secure user login and registration.

### 2. **Home Screen**
   - Displays categories with thumbnail images.
   - Allows users to browse products by category.

### 3. **Product Details**
   - Displays detailed information about the product.
   - Options to add to the cart or save for later.

### 4. **Search**
   - Search products by name or keyword.

### 5. **Cart**
   - View products added to the cart.
   - Modify quantities or remove products.

### 6. **Order History**
   - Displays previously placed orders.

### 7. **Recently Viewed Products**
   - Tracks and shows the last viewed products for the user.

---

## Future Enhancements
- **Responsive UI**: Adapting to different screen sizes and orientations.
- **Payment Gateway Integration**: Adding support for online payments.
- **Push Notifications**: Notify users of discounts, offers, and order status.

---

## Contribution
1. Fork the repository.
2. Create a feature branch: `git checkout -b feature-name`.
3. Commit your changes: `git commit -m "Added a new feature"`.
4. Push to the branch: `git push origin feature-name`.
5. Create a pull request.

---

## License
This project is licensed under the MIT License.

---

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class ShoppingCart {
    private List<CartItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        items.add(new CartItem(product, quantity));
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getTotalPrice();
        }
        return total;
    }
}

class User {
    private String username;
    private String password;
    private ShoppingCart cart;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ShoppingCart();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ShoppingCart getCart() {
        return cart;
    }
}

public class ECommerceSystem {
    private List<Product> products;
    private List<User> users;
    private User currentUser;
    private Scanner scanner;

    public ECommerceSystem() {
        this.products = new ArrayList<>();
        this.users = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void registerUser(String username, String password) {
        users.add(new User(username, password));
    }

    public void login() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful.\n");
                listProducts();
                return;
            }
        }
        System.out.println("Invalid username or password.");
    }

    private void listProducts() {
        System.out.println("\nAvailable Products:");
        for (Product product : products) {
            System.out.println(product.getId() + ". " + product.getName() + " - Rs." + product.getPrice());
        }
        System.out.println("\nOptions:");
        System.out.println("1. Add item to cart");
        System.out.println("2. View cart");
        System.out.println("3. Generate bill");
        System.out.println("4. Exit");
        System.out.println("Enter option number:");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline character

        switch (option) {
            case 1:
                addToCartOption();
                break;
            case 2:
                viewCartOption();
                break;
            case 3:
                generateBillOption();
                break;
            case 4:
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void addToCartOption() {
        System.out.println("Enter product ID:");
        int productId = scanner.nextInt();
        System.out.println("Enter quantity:");
        int quantity = scanner.nextInt();
        addToCart(productId, quantity);
    }

    private void viewCartOption() {
        viewCart();
        listProducts(); // List products again after viewing cart
    }

    private void generateBillOption() {
        viewCart();
        System.out.println("Total Price: Rs." + currentUser.getCart().getTotalPrice());
        System.out.println("Thank you for shopping!");
        System.exit(0);
    }

    public void addToCart(int productId, int quantity) {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        Product product = getProductById(productId);
        if (product == null) {
            System.out.println("Product not found.");
            return;
        }
        currentUser.getCart().addItem(product, quantity);
        System.out.println("Product added to cart.");
        listProducts();
    }

    public void viewCart() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        ShoppingCart cart = currentUser.getCart();
        List<CartItem> items = cart.getItems();
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Your cart:");
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                System.out.println((i + 1) + ". " + item.getProduct().getName() + " - Quantity: " + item.getQuantity() + " - Total Price: Rs." + item.getTotalPrice());
            }
            System.out.println("Total Price: Rs." + cart.getTotalPrice());
        }
    }

    private Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ECommerceSystem system = new ECommerceSystem();

        // Sample products
        system.addProduct(new Product(1, "Nike Shoes", 1000.0));
        system.addProduct(new Product(2, "Titan Watch", 2000.0));
        system.addProduct(new Product(3, "Samsung Smartphone", 15000.0));
        system.addProduct(new Product(4, "Sony Headphones", 1500.0));
        system.addProduct(new Product(5, "Apple AirPods", 3000.0));
        system.addProduct(new Product(6, "Adidas T-Shirt", 2500.0));
        system.addProduct(new Product(7, "Levi's Jeans", 3000.0));
        system.addProduct(new Product(8, "Ray-Ban Sunglasses", 950.0));
        system.addProduct(new Product(9, "Dell Laptop", 70000.0));
        system.addProduct(new Product(10, "Logitech Mouse", 500.0));

        // Sample user registration
        system.registerUser("user1", "password1");
        system.registerUser("user2", "password2");

        // Sample login
        system.login();
    }
}

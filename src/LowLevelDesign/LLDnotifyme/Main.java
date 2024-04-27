import java.util.ArrayList;
import java.util.List;

// Subject interface
interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyObservers();
}

// Concrete subject class
class Product implements Subject {
    private String name;
    private boolean availability;
    private List
            <Observer> observers;
    public Product(String name) {
        this.name = name;
        this.availability = false;
        this.observers = new ArrayList<>();
    }

    public void setAvailability(boolean availability) {
        if (this.availability != availability) {
            this.availability = availability;
            notifyObservers();
        }
    }

    @Override
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public String getName() {
        return name;
    }

    public boolean isAvailable() {
        return availability;
    }
}

// Observer interface
interface Observer {
    void update(Product product);
}

// Concrete observer class
class User implements Observer {
    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(Product product) {
        System.out.println(name + ": Notification - Product '" + product.getName() + "' is now available!");
    }
}

public class Main {
    public static void main(String[] args) {
        // Create a product
        Product product = new Product("Smartphone");

        // Create users
        User user1 = new User("Alice");
        User user2 = new User("Bob");

        // Subscribe users to product notifications
        product.attach(user1);
        product.attach(user2);

        // Simulate availability change
        product.setAvailability(true);
    }
}

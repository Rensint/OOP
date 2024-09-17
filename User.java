package Assignment;

public class User {
    
        private String username;
        private String password;
    
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
    
        // Login
        public boolean login(String username, String password) {
            return this.username.equals(username) && this.password.equals(password);
        }
    
        // Logout
        public void logout() {
            System.out.println("User " + username + " logged out.");
        }
    
        // Getters and Setters
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public String toString() {
            return "Username: " + username;
        }
    }
    
public class CurrentUser {
    private static User user;
    private static final CurrentUser instance = new CurrentUser();

    private CurrentUser() {}

    public static CurrentUser getInstance() {
        return instance;
    }

    public void setUser(User newUser) {
        System.out.println("Setting current user: " + (newUser != null ? newUser.getUsername() : "null"));
        user = newUser;
    }

    public User getUser() {
        if (user == null) {
            System.out.println("Current user is null.");
            // Handle this case appropriately, e.g., return a new User(), or handle null outside
        }
        return user;
    }
}
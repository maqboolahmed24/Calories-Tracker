public class User {
    private String username;
    private String password;
    private int dailyCalorieGoal;
    private boolean loggedOnFirstTime;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedOnFirstTime = true; // Set default to true for new users
        this.dailyCalorieGoal = 0;     // Set default goal to 0
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

    public int getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(int dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public boolean isLoggedOnFirstTime() {
        return loggedOnFirstTime;
    }

    public void setLoggedOnFirstTime(boolean loggedOnFirstTime) {
        this.loggedOnFirstTime = loggedOnFirstTime;
    }

    // toString method for easy printing of user information
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dailyCalorieGoal=" + dailyCalorieGoal +
                '}';
    }
}
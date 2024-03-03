public class User {
    private String username;
    private int password;
    private boolean isFirstPurchase;

    public User(String username, int password, boolean isFirstPurchase) {
        this.username = username;
        this.password = password;
        this.isFirstPurchase = isFirstPurchase;
    }
    public static User getUserInput(){
        System.out.println("-----Welcome to Westminster Shopping Centre-----");

        String username= Validation.StringValidator("Username: ");
        int password= Validation.intValidator("Password: ");
        return new User(username,password,true);

    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }
    public int getPassword(){
        return password;
    }
    public void setPassword(int password){
        this.password=password;
    }

    public boolean isFirstPurchase() {
        return isFirstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase) {
        isFirstPurchase = firstPurchase;
    }
}

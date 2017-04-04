package info.androidhive.firebase;

/**
 * Created by ehab- on 3/31/2017.
 */

public class User {
    public String name;
    public String email;
    public String mobile;
    public String gender;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String name,String mobile,String gender) {
        this.name = name;
      //  this.email = email;
        this.gender= gender;
        this.mobile= mobile;
    }
}

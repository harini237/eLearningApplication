package Service;

import Entity.User;
import Repository.UserRepository;
import Util.PasswordUtil;

public class UserService {

    private final UserRepository userRepository;

    // Constructor
    public UserService() {
        this.userRepository = new UserRepository();
    }

    /**
     * Creates a new user in the database.
     * @param user The User object containing all necessary information.
     */
    public void createUser(User user) {
        // Hash the password before creating the user
        String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.createUser(user);
    }

    /**
     * Retrieves a user by ID.
     * @param userId The ID of the user to retrieve.
     * @return The User object if found, otherwise null.
     */
    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    /**
     * Validates user credentials for login.
     * @param email The user's email.
     * @param password The user's password.
     * @return True if credentials are valid, false otherwise.
     */
    public boolean validateLogin(String email, String password) {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            return false;
        }
        // Hash the input password and compare with stored hash
        String hashedInputPassword = PasswordUtil.hashPassword(password);
        return hashedInputPassword.equals(user.getPassword());
    }

    /**
     * Checks if the user has a specified role.
     * @param userId The ID of the user to check.
     * @param roleId The role ID to verify.
     * @return True if the user has the specified role, false otherwise.
     */
    public boolean hasUserRole(String userId, int roleId) {
        User user = userRepository.getUserById(userId);
        return user != null && user.getRoleId() == roleId;
    }
}
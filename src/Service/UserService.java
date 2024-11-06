package Service;

import Entity.Role;
import Entity.User;
import Repository.RoleRepository;
import Repository.UserRepository;
import Util.PasswordUtil;

public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Constructor
    public UserService() {
        this.roleRepository = new RoleRepository();
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
     * Creates a new User in the database.
     * @param firstName The firstname of the User
     * @param lastName The lastname of the User
     * @param email The email of the User
     * @param password The password of the User
     * @param roleId The Type of User must be either 3 or 4
     */
    public void createFirstTimeUser(String firstName, String lastName, String email, String password, Integer roleId) {
        if(!(roleId == 3 || roleId == 4)){
            throw new IllegalArgumentException("Role Id must be either 3 or 4");
        }
        User newUser = new User();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(password);  // The password should be hashed in UserService
        newUser.setRoleId(roleId);  // Assuming 2 is the role ID for Faculty
        newUser.setIsPwdResetReq(Boolean.TRUE);
        try {
            this.createUser(newUser);
            System.out.println("Faculty account created successfully.");
        }catch (Exception ignored){
            System.out.println("Error While creating user account Error: " + ignored.getMessage());
        }
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
     * Retrieves a user by Email.
     * @param userEmail The Email ID of the user to retrieve.
     * @return The User object if found, otherwise null.
     */
    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmail(userEmail);
    }

    /**
     * Retrieves a user by Email.
     * @param userEmail The Email ID of the user to retrieve.
     * @return The User object if found, otherwise null.
     */
    public User getUserWithRoleUsingEmail(String userEmail) {
        return userRepository.getUserWithRoleUsingEmail(userEmail);
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

    /**
     * Handles the password reset process.
     *
     * @param userId The ID of the user whose password is being updated.
     * @param currentPassword The new  password to store.
     */
    public void resetPassword(String userId, String currentPassword, String newPassword) {

        // Retrieve the user details
        User user = userRepository.getUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        // Validate current password
        if (!PasswordUtil.hashPassword(currentPassword).equals(user.getPassword())) {
            System.out.println("Incorrect current password. Please try again.");
            return;
        }

        // Update password in the repository
        String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
        user.setIsPwdResetReq(false);
        if (userRepository.updatePassword(userId, hashedNewPassword)) {
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Error updating password. Please try again.");
        }
    }
}
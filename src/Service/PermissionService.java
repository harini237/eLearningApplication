package Service;

import Entity.ContentBlock;
import Entity.User;
import Repository.UserRepository;


public class PermissionService {

    UserRepository userRepository = new UserRepository();

    /**
     * Checks if the user is an admin.
     */
    public boolean isAdmin(User user) {
        return "Admin".equalsIgnoreCase(user.getRole());
    }

    /**
     * Checks if the user is a faculty member.
     */
    public boolean isFaculty(User user) {
        return "Faculty".equalsIgnoreCase(user.getRole());
    }

    /**
     * Checks if the user is a teaching assistant.
     */
    public boolean isTA(User user) {
        return "TA".equalsIgnoreCase(user.getRole());
    }

    /**
     * Checks if a user is allowed to delete specific content based on ownership and role.
     */
    public boolean canDeleteContent(User user, ContentBlock content) {
        if (isAdmin(user)) {
            return true; // Admin can delete any content
        }
        if (isFaculty(user) && content.getCreatedBy().equals(user.getId())) {
            return true; // Faculty can delete their own content
        }
        if (isTA(user) && userRepository.getUserById(content.getCreatedBy()).getRole().equals("TA") && content.getCreatedBy().equals(user.getId())) {
            return true; // TA can delete their own content or other TAs' content
        }
        return false;
    }

    /**
     * Checks if a user is allowed to add content based on role.
     */
    public boolean canAddContent(User user) {
        return isAdmin(user) || isFaculty(user) || isTA(user);
    }
}

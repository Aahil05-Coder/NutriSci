package service;

import dao.UserProfileDAO;
import model.UserProfile;
import java.util.List;

public class ProfileService {
    private final UserProfileDAO dao = new UserProfileDAO();

    public List<UserProfile> getAllUsers() {
        return dao.getAll();
    }

    public void saveOrUpdate(UserProfile user) {
        if (user.getId() > 0) {
            dao.update(user);
        } else {
            dao.create(user);
        }
    }
}

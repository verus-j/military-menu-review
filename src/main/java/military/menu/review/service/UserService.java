package military.menu.review.service;

import military.menu.review.model.menu.User;
import military.menu.review.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User findByName(String name) {
        return repository.findByName(name);
    }
}

package atesteme.service;

import atesteme.entity.UserEntity;
import atesteme.exception.AuthenticationException;
import atesteme.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AuthService {

    private final UserRepository userRepository;

    @Inject
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserEntity login(String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            throw new AuthenticationException("Usuário ou senha inválidos");
        }
        return user;
    }
}

package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Transactional
    public User add(UserDto userDto) {
        return userRepository.save(userDto._toUser());
    }

    @Transactional
    public void update(User loginUser, long id, UserDto updatedUser) {
        User original = findById(loginUser, id);
        original.update(loginUser, updatedUser._toUser());
    }

    public User findById(User loginUser, long id) {
        return userRepository.findById(id)
                .filter(user -> user.equals(loginUser))
                .orElseThrow(UnAuthorizedException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User login(String userId, String password) throws UnAuthenticationException {
        Optional<User> maybeUser = userRepository.findByUserId(userId);
        if (!maybeUser.isPresent()) {
            throw new UnAuthenticationException("아이디 또는 비밀번호가 다릅니다");
        }

        User user = maybeUser.get();
        if (!user.matchPassword(password)) {
            throw new UnAuthenticationException("아이디 또는 비밀번호가 다릅니다");
        }

        return user;
    }
}

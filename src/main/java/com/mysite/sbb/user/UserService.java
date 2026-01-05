package com.mysite.sbb.user;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public SiteUser create(String username, String email, String password){
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(siteUser);
        return siteUser;
    }

    @Transactional
    public void createBatch(List<SiteUser> siteUsers){
        final int batchSize = 50;

        for(int i = 0 ; i < siteUsers.size() ; i++){
            userRepository.save(siteUsers.get(i));

            if(i % batchSize == 0 && i > 0){
                userRepository.flush();
                entityManager.clear();
            }
        }

        userRepository.flush();
        entityManager.clear();
    }

    public SiteUser getUser(String username){
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if(_siteUser.isPresent()){
            return _siteUser.get();
        }
        else{
            throw new DataNotFoundException("siteuser not found");
        }
    }
}

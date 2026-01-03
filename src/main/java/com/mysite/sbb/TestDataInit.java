package com.mysite.sbb;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserRepository;
import com.mysite.sbb.user.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TestDataInit {
    @Autowired
    @Lazy
    private TestDataInit self;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserRepository userRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            self.truncateTable("SITE_USER");
            self.truncateTable("QUESTION");
            self.truncateTable("ANSWER");
            self.work1();
        };
    }

    // just test
    @Transactional
    public void truncateTable(String tableName){
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        String sql = "TRUNCATE TABLE %s".formatted(tableName);
        entityManager.createNativeQuery(sql).executeUpdate();

        sql = "ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1".formatted(tableName);
        entityManager.createNativeQuery(sql).executeUpdate();

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    @Transactional
    public void work1(){
        if(userRepository.count() > 0) return;
        LocalDateTime localDateTimeNow = LocalDateTime.now();

        List<SiteUser> siteUsers = new ArrayList<>();
//        userService.create("123", "q@q", "123");
        // 사용자 생성
        for(int i = 0 ; i < 200 ; i++){
//            "username%d".formatted(i), "email%d@gmail.com".formatted(i), "password%d".formatted(i)
            SiteUser siteUser = new SiteUser();
            siteUser.setUsername("username%d".formatted(i));
            siteUser.setEmail("email%d@gmail.com".formatted(i));
            siteUser.setPassword("password%d".formatted(i));
            siteUsers.add(siteUser);
        }
        userService.createBatch(siteUsers);

        // 질문 생성
        List<Question> questions = new ArrayList<>();
        for(int i = 0 ; i < 200 ; i++){
//            "subject%d".formatted(i), "content%d".formatted(i), siteUsers.get(i)
            Question question = new Question();
            question.setSubject("subject%d".formatted(i));
            question.setContent("content%d".formatted(i));
            question.setAuthor(siteUsers.get(i));
            question.setCreateDate(localDateTimeNow);
            questions.add(question);
        }
        questionService.createBatch(questions);

        // 답변 생성
        for(Question question : questions){
            List<Answer> answers = new ArrayList<>();
            int index = 0;
            for(SiteUser siteUser : siteUsers){
                if(index >= 200) break;
//                question, "content%d".formatted(index++), siteUser
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setContent("content%d".formatted(index++));
                answer.setAuthor(siteUser);
                answer.setCreateDate(localDateTimeNow);
                answers.add(answer);
            }
            answerService.createBatch(answers);
        }
    }
}

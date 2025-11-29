package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model,
                               @PathVariable Integer id,
                               @RequestParam(value="content") String content){
        Question question = this.questionService.getQuestion(id);
//        TODO: 답변 저장
        return String.format("redirect:/question/detail/%s", id);
    }
}
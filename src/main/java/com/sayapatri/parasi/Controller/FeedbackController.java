package com.sayapatri.parasi.Controller;

import com.sayapatri.parasi.Model.ReCaptchaResponse;
import com.sayapatri.parasi.Model.feedback;
import com.sayapatri.parasi.Repository.FeedbackRepositry;
import com.sayapatri.parasi.Service.NoticeService;
import com.sayapatri.parasi.Service.feedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.RestTemplate;

//import net.tanesha.recaptcha.ReCaptchaResponse;

@Controller
public class FeedbackController {

    private String message1;

    @Autowired
    private feedbackService fbs;

    @Autowired
    private FeedbackRepositry feedbackRepositry;
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/")
    public String showFeedback(Model model)
    {
        message1=null;
        model.addAttribute("notices",noticeService.findAll());
        return "Home";
    }

    @PostMapping("/addFeedback")
    public String addFeedback(feedback fb, Model model,@RequestParam(name="g-recaptcha-response") String captchaResponse) {

        model.addAttribute("feedback", fb);
        String url = "https://www.google.com/recaptcha/api/siteverify";
        String params = "?secret=6Lc4C64ZAAAAAJTZOPbVbXmyXcs9dpl9tcsxW-wI&response=" + captchaResponse;

        ReCaptchaResponse reCaptchaResponse = restTemplate.exchange(url + params, HttpMethod.POST, null, ReCaptchaResponse.class).getBody();
//        model.addAttribute("feedback", fb);

        if (reCaptchaResponse.isSuccess()) {
            feedbackRepositry.save(fb);
            return "redirect:/";
        } else {
            message1 = "Please verify captcha";
            return "redirect:/";
        }
    }

        @GetMapping("/feedBackList")
    public String showadminNoticeList(Model model)
    {
        model.addAttribute("feedBack",fbs.findAll());
        return "feedback";
    }
}



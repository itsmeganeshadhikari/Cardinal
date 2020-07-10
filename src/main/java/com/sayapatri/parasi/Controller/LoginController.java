package com.sayapatri.parasi.Controller;

import com.sayapatri.parasi.Model.Notice;
import com.sayapatri.parasi.Model.User;
import com.sayapatri.parasi.Service.NoticeService;
import com.sayapatri.parasi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {


    @Autowired
    UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

   @Autowired
  private NoticeService noticeService;

    // Function to display login page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("security/login");
        return modelAndView;
    }


    // Function to handle the login process
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView processLoginForm(ModelAndView modelAndView, User user, HttpServletRequest request, Model model) {
        User existingUser = userService.findByEmail(user.getEmail());
        if(existingUser!=null) {
            //Use encoder.matches to compare raw password with encrypted password
            if(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
             modelAndView.addObject("message", "Successfully logged in!");
             model.addAttribute("notices",noticeService.findAll());
                modelAndView.setViewName("message");
            } else {
                modelAndView.addObject("notmessage", "Incorrect password. Try again.");
                modelAndView.setViewName("security/login");
            }
        }
        else {
            modelAndView.addObject("notmessages", "The email provided does not exist!");
            modelAndView.setViewName("security/login");

        }
        return modelAndView;
    }
//function to handle logout
@GetMapping("/logout")
public String Logout(Notice notice)
{
    return "redirect:/login?logout";
}




}


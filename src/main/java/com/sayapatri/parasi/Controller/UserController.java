package com.sayapatri.parasi.Controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.sayapatri.parasi.Model.User;
import com.sayapatri.parasi.Service.EmailService;
import com.sayapatri.parasi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    //Return registration form template

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("security/register");
        return modelAndView;

    }

    //Process input data
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult, HttpServletRequest request) {
        //lookup user in database
        User userExits = userService.findByEmail(user.getEmail());

        System.out.println(userExits);

        if (userExits != null) {
            modelAndView.addObject("alreadyRegisteredMessage", "Opps! There is already a user registered with this email provided");
            modelAndView.setViewName("security/register");
            bindingResult.reject("email");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("security/register");

        } else {
            //new user so, we create user and send confirmation e-mail
            //Disable user until they click on confirmation link of email
            user.setEnabled(false);

            //Generate random 36-character string token for confirmation link
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.save(user);

            String appurl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();

            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Registration confirmation");

            registrationEmail.setText("To Confirm your e-mail,please click on link below:\n" + appurl + ":8080/confirm?token=" + user.getConfirmationToken());

            registrationEmail.setFrom("suraj520876@gmail.com");
            emailService.sendEmail(registrationEmail);

            modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to" + user.getEmail());
            modelAndView.setViewName("security/register");
        }
        return modelAndView;
    }

    //Process confirmation link
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public ModelAndView ConfirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if (user == null) {
            //No token found in DB
            modelAndView.addObject("invalidToken", "Oops!,this is an invalid confirmation link.");

        } else {
            //Token found
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());

        }
        modelAndView.setViewName("security/confirm");
        return modelAndView;
    }

    //Process confirmatiom link
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ModelAndView ConfirmRegistration(ModelAndView modelAndView, BindingResult bindingResult, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
        modelAndView.setViewName("security/confirm");
        Zxcvbn passwordCheck = new Zxcvbn();

        Strength strength = passwordCheck.measure(requestParams.get("password"));
        if (strength.getScore() < 4) {
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Your password is too weak.Choose stronger one");
            modelAndView.setViewName("redirect:confirm?/token=" + requestParams.get("token"));
            return modelAndView;

        }
        //find User associated with reset token
        User user = userService.findByConfirmationToken(requestParams.get("token"));
        //set new password
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));

        //set user to enabled
        user.setEnabled(true);

        //save user
        userService.saveuser(user);
        modelAndView.addObject("successMessage", "Your Password has been set!");
        return modelAndView;
    }


    }


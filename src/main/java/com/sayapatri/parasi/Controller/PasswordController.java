package com.sayapatri.parasi.Controller;

import com.sayapatri.parasi.Model.ResetToken;
import com.sayapatri.parasi.Model.User;
import com.sayapatri.parasi.Repository.ResetRepositry;
import com.sayapatri.parasi.Repository.UserRepository;
import com.sayapatri.parasi.Service.EmailService;
import com.sayapatri.parasi.Service.ResetService;
import com.sayapatri.parasi.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetService resetService;

    @Autowired
    private ResetRepositry resetRepositry;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Display forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView displayForgotPasswordPage(ModelAndView modelAndView, User user) {

        modelAndView.addObject("user", user);
        modelAndView.setViewName("security/forgotPassword");
        return modelAndView;

    }

    //Process form submission from forgotPassword page
    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, User user, HttpServletRequest request) {
        //Lookup user in database by e-mail
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null) {
            //create token
            ResetToken resetToken = new ResetToken(existingUser);

            //save it
            resetService.save(resetToken);

            //create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("suraj520876@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://localhost:8080/confirm-password?token=" + resetToken.getResetToken());
            emailService.sendEmail(mailMessage);
            modelAndView.addObject("succesmessage", "Request to reset password received. Check your inbox for the reset link.");
            modelAndView.setViewName("security/forgotPassword");


        } else {
            modelAndView.addObject("errormessage", "This email does not exist!");
            modelAndView.setViewName("security/forgotPassword");
        }
        return modelAndView;

    }


    @RequestMapping(value = "/confirm-password", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token") String ResetToken) {
        ResetToken token = resetRepositry.findByResetToken(ResetToken);


        if (token != null) {
            User user = userRepository.findByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("email", user.getEmail());
            modelAndView.setViewName("security/resetPassword");
        }
        else
            {
            modelAndView.addObject("invalidToken", "The link is invalid or broken!");
            modelAndView.setViewName("security/resetPassword");
            }

        return modelAndView;
    }

    /**
     * Receive the token from the link sent via email and display form to reset password
     */
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        // ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (user.getEmail() != null) {
            // use email to find user
            User tokenUser = userRepository.findByEmail(user.getEmail());
            tokenUser.setEnabled(true);
            tokenUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            // System.out.println(tokenUser.getPassword());
            userRepository.save(tokenUser);
            modelAndView.addObject("successMessage", "Password successfully reset. You can now log in with the new credentials.");
            modelAndView.setViewName("security/resetPassword");
        } else {
            modelAndView.addObject("errorMessage", "The link is invalid or broken!");
            modelAndView.setViewName("security/resetPassword");
        }

        return modelAndView;
    }
}

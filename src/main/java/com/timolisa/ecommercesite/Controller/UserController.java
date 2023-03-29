package com.timolisa.ecommercesite.Controller;

import com.timolisa.ecommercesite.DTO.UserDTO;
import com.timolisa.ecommercesite.Entity.User;
import com.timolisa.ecommercesite.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("userDTO") UserDTO userDTO,
                                          RedirectAttributes redirectAttributes) {
        if (!userDTO.getPassword()
                .equals(userDTO.getRepeatPassword())) {
            redirectAttributes.addFlashAttribute("passwordError", "Incorrect password");
            return "redirect:/register";
        }
        userService.saveUser(userDTO);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("user", new UserDTO());
        return mav;
    }
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") UserDTO userDTO,
                            HttpServletRequest request,
                            RedirectAttributes redirectAttributes) {
        User user = userService.loginUser(userDTO);
        if (user == null) {
            redirectAttributes.addFlashAttribute("loginError", "Invalid email or password...");
            return "redirect:/login";
        } else {
            HttpSession session = request.getSession();
            Long id = user.getUserID();
            session.setAttribute("userID", id);
            return "redirect:/home";
        }
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}

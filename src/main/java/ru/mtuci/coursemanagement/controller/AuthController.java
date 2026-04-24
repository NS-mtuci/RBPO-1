package ru.mtuci.coursemanagement.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.coursemanagement.model.User;
import ru.mtuci.coursemanagement.service.UserService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService users;

    private static final BCryptPasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpServletRequest req,
                          Model model) {

        Optional<User> opt = users.findByUsername(username);

        if (opt.isPresent()) {
            User u = opt.get();

            if (PASSWORD_ENCODER.matches(password, u.getPassword())) {
                log.info("User {} logged in", username);

                HttpSession session = req.getSession(true);
                session.setAttribute("username", username);
                session.setAttribute("role", u.getRole());

                return "redirect:/";
            }
        }

        model.addAttribute("error", "Неверные учетные данные");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam(required = false) String role) {

        String encodedPassword = PASSWORD_ENCODER.encode(password);

        // Роль не берём из формы, чтобы пользователь не мог сам сделать себя TEACHER.
        users.save(new User(null, username, encodedPassword, "STUDENT"));

        return "redirect:/login";
    }
}
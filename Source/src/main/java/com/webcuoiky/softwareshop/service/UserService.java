package com.webcuoiky.softwareshop.service;

import com.webcuoiky.softwareshop.model.User;
import com.webcuoiky.softwareshop.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Trả về UserDetails để Spring Security sử dụng
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getAuthorities()
                );
//                .withUsername(user.getEmail())
//                .password(user.getPassword())
//                .authorities() // Gán quyền của người dùng
//                .build();
    }

    public boolean register(User user) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(user.getEmail())) {
            return false; // Email đã tồn tại
        }

        System.out.println("Mật khẩu gốc (trước khi mã hóa): " + user.getPassword());
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        System.out.println("Mật khẩu mã hóa (lưu vào DB): " + encodedPassword);
        user.setPassword(encodedPassword);

        // Cài đặt quyền admin nếu là admin
        if(List.of("duong@admin.com",
                        "phuc@admin.com",
                        "quang@admin.com",
                        "phat@admin.com")
                .contains(user.getEmail())) {
            user.setRole("ROLE_ADMIN");
        }
        else {
            user.setRole("ROLE_USER");
        }

        userRepository.save(user);
        return true;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email); // Tìm người dùng theo email
        if (user == null) {
            throw new IllegalArgumentException("Tài khoản không tồn tại");
        }

        System.out.println("Mật khẩu trong DB (mã hóa): " + user.getPassword());
        System.out.println("Mật khẩu nhập vào (plain text): " + password);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Mật khẩu không chính xác");
        }
        return user;
    }

//    public boolean getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }
}

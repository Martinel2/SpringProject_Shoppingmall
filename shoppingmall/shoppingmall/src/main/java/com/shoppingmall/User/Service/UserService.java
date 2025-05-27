package com.shoppingmall.User.Service;

import com.shoppingmall.Global.Exception.EmailDuplicationException;
import com.shoppingmall.User.Domain.Users;
import com.shoppingmall.Global.Auth.Dto.SignupDto;
import com.shoppingmall.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //중복 id 검사 코드
    public Users findById(String id) {
        return userRepository.findById(id);
    }

    //로그인 절차 확인 코드

    /*public Users Login(String id, String password){
        Users user = userRepository.findById(id);
        if(user==null) return null;
        else if(user.getPassword().equals(user.getPassword())) return user;
        return null;
    }
     */

    public Users findByEmail(String email) {return userRepository.findByEmail(email);}

    @Override
    public UserDetails loadUserByUsername(String insertedUserId) throws UsernameNotFoundException {
        Optional<Users> findOne = Optional.ofNullable(userRepository.findById(insertedUserId));
        Users users = findOne.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다 ㅠ"));

        return User.builder()
                .username(users.getId())
                .password(users.getPassword())
                .roles(users.getRole())
                .build();
    }

    public Users join(Users users){
        userRepository.add(users);
        return users;
    }

    public boolean delete(Users users) { return userRepository.deleteUser(users); }

    public boolean dupCheck(String id) {
        if(userRepository.findById(id) == null) return true;
        else throw new EmailDuplicationException();
    }

    public Users signUp(SignupDto signupDto) {
        if(dupCheck(signupDto.getId())) {
            String hashPwd = passwordEncoder.encode(signupDto.getPassword());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate birthdate = LocalDate.parse(signupDto.getBirth(), formatter);
            Users savedUser = new Users(signupDto.getId(), hashPwd, signupDto.getName(), signupDto.getPlace(),
                    signupDto.getPhone(), birthdate, signupDto.getRole(), signupDto.getEnabled(), signupDto.getEmail(), signupDto.getSex());

            if (findById(signupDto.getId()) == null) return join(savedUser);
            else throw new UsernameNotFoundException("회원가입 오류");
        }
        else throw new EmailDuplicationException();
    }

}

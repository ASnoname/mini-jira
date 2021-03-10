package ru.nstu.upp.minijira.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.nstu.upp.minijira.entity.User;

import java.util.ArrayList;
import java.util.List;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getLogin(),
                user.getPasswordHash(),
                mapToGrantedAuthorities(user.getIsAdmin())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(boolean isAdmin) {
        List<GrantedAuthority> result = new ArrayList<>();
        if (isAdmin) {
            result.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            result.add(new SimpleGrantedAuthority("USER"));
        }
        return result;
    }
}

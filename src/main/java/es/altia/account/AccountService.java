package es.altia.account;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	protected void initialize() {
		this.save(new Account("Usuario", "user", "user", "ROLE_USER"));
		this.save(new Account("Administrador", "admin", "admin", "ROLE_ADMIN"));
	}

	@Transactional
	public Account save(final Account account) {
		account.setPassword(this.passwordEncoder.encode(account.getPassword()));
		this.accountRepository.save(account);
		return account;
	}

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		final Account account = this.accountRepository.findOneByEmail(username);
		if(account == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return this.createUser(account);
	}

	public void signin(final Account account) {
		SecurityContextHolder.getContext().setAuthentication(this.authenticate(account));
	}

	private Authentication authenticate(final Account account) {
		return new UsernamePasswordAuthenticationToken(this.createUser(account), null, Collections.singleton(this.createAuthority(account)));
	}

	private User createUser(final Account account) {
		return new User(account.getName(), account.getPassword(), Collections.singleton(this.createAuthority(account)));
	}

	private GrantedAuthority createAuthority(final Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}

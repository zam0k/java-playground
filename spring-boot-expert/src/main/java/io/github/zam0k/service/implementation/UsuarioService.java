package io.github.zam0k.service.implementation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.zam0k.domain.entity.Usuario;
import io.github.zam0k.domain.repository.UsuarioRepository;
import io.github.zam0k.exception.SenhaInvalidaException;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public UserDetails autenticar(Usuario usuario)
			throws SenhaInvalidaException {
		UserDetails user = loadUserByUsername(usuario.getUsername());
		boolean passwordMatches = encoder.matches(usuario.getPassword(),
				user.getPassword());

		if (passwordMatches) {
			return user;
		}

		throw new SenhaInvalidaException();
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(
						"Usuário não encontrado na base de dados."));

		String[] roles = usuario.isAdmin() ? new String[] { "USER", "ADMIN" }
				: new String[] { "USER" };

		return User.builder().username(usuario.getUsername())
				.password(usuario.getPassword()).roles(roles).build();
	}

}

package br.com.locadora.api.domain.usuario;
import br.com.locadora.api.domain.pessoa.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String senha;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    public Usuario(String email, String senha, Role role) {
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == null) {
            // Definindo "ROLE_USER" quando o papel não é especificado
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        } else if (this.role == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
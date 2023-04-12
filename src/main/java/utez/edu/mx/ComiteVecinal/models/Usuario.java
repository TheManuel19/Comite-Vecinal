package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long id;

    /*@NotNull
    @NotBlank
    @Size(min = 3, max = 50)
    @Pattern(regexp = "[A-Za-zÀ-ÿ '-.]*")*/
    @Column(nullable = true, length = 45, unique = true)
    private String userName;

    /*@NotNull
    @NotBlank
    @Size(min = 4, max = 150)*/
    @Column(nullable = false, length = 150)
    private String password;

    @Column(nullable = true, length = 50)
    private String linkRestablecer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private Set<Log> logs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<Rol>();

    public Usuario(Long id, String userName, String password, String linkRestablecer, Set<Log> logs, Set<Rol> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.linkRestablecer = linkRestablecer;
        this.logs = logs;
        this.roles = roles;
    }

    public Usuario(String userName, String password, Set<Rol> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public Usuario() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLinkRestablecer() {
        return linkRestablecer;
    }

    public void setLinkRestablecer(String linkRestablecer) {
        this.linkRestablecer = linkRestablecer;
    }

    public Set<Log> getLogs() {
        return logs;
    }

    public void setLogs(Set<Log> logs) {
        this.logs = logs;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }
}

package utez.edu.mx.ComiteVecinal.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_log")
    private Long id;

    @Column(name="old_data")
    private String datoViejo;

    @Column(name="new_data")
    private String datoNuevo;

    @CreationTimestamp
    @Column(name = "creado_en", nullable = false)
    private LocalDateTime creadoEn;

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false)
    private String tabla;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario user;

    public Log() {
    }

    public Log(Long id, String datoViejo, String datoNuevo, LocalDateTime creadoEn, String accion, String tabla, Usuario user) {
        this.id = id;
        this.datoViejo = datoViejo;
        this.datoNuevo = datoNuevo;
        this.creadoEn = creadoEn;
        this.accion = accion;
        this.tabla = tabla;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatoViejo() {
        return datoViejo;
    }

    public void setDatoViejo(String datoViejo) {
        this.datoViejo = datoViejo;
    }

    public String getDatoNuevo() {
        return datoNuevo;
    }

    public void setDatoNuevo(String datoNuevo) {
        this.datoNuevo = datoNuevo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
}

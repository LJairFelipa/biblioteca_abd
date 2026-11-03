package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "RECURSOS")
public class Recurso {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECURSO")
    private Long idRecurso;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "TIPO")
    private String tipo;

    @Column(name = "AUTOR_CATEGORIA")
    private String autorCategoria;

    @Column(name = "ESTADO")
    private String estado;

    // Getters y Setters
    public Long getIdRecurso() { return idRecurso; }
    public void setIdRecurso(Long idRecurso) { this.idRecurso = idRecurso; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getAutorCategoria() { return autorCategoria; }
    public void setAutorCategoria(String autorCategoria) { this.autorCategoria = autorCategoria; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}

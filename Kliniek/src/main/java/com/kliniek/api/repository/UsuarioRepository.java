package com.kliniek.api.repository;

import com.kliniek.api.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UsuarioRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int create(Usuario usuario) {
        String sql = "insert into usuario (username, senha, estado) values (?, ?, ?)";
        return jdbcTemplate.update(sql, usuario.getUsername(), usuario.getSenha(), usuario.getEstado());
    }

    public List<Usuario> findAll() {
        return jdbcTemplate.query(
                "select * from usuario",
                (rs, rowNum) ->
                        new Usuario(
                                rs.getLong("usuarioid"),
                                rs.getString("username"),
                                rs.getString("senha"),
                                rs.getString("estado"),
                                rs.getDate("dataCriacao")
                        )
        );
    }

}

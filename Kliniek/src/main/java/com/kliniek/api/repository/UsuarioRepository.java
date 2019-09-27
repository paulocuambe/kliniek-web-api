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

    public List<Usuario> findAll() {
        try {
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
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario findUsuarioById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from usuario where usuarioid = ?", new Object[]{id},
                    (rs, rowNum) ->
                            new Usuario(
                                    rs.getLong("usuarioid"),
                                    rs.getString("username"),
                                    rs.getString("senha"),
                                    rs.getString("estado"),
                                    rs.getTimestamp("dataCriacao")
                            )
            );
        } catch (Exception e) {
            return null;
        }
    }

    public Usuario findUsuarioByUsername(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from usuario where username = ?", new Object[]{username},
                    (rs, rowNum) ->
                            new Usuario(
                                    rs.getLong("usuarioid"),
                                    rs.getString("username"),
                                    rs.getString("senha"),
                                    rs.getString("estado"),
                                    rs.getDate("dataCriacao")
                            )
            );
        } catch (Exception e) {
            return null;
        }
    }

    public int create(Usuario usuario) {
        String sql = "insert into usuario (username, senha, estado) values (?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, usuario.getUsername(), usuario.getSenha(), usuario.getEstado());
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateSenha(long id, String senha) {
        String sql = "update usuario set senha = ? where usuarioid = " + id;
        try {
            return jdbcTemplate.update(sql, senha);
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateEstado(long id, String estado) {
        String sql = "update usuario set estado = ? where usuarioid = " + id;
        try {
            return jdbcTemplate.update(sql, estado);
        } catch (Exception e) {
            return 0;
        }
    }

    public int updateUsername(long id, String username) {
        String sql = "update usuario set username = ? where usuarioid = " + id;
        try {
            return jdbcTemplate.update(sql, username);
        } catch (Exception e) {
            return 0;
        }
    }


}

package com.kliniek.api.repository;

import com.kliniek.api.model.TipoExame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoExameRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TipoExame> findAllTiposExames() {
        try {
            return jdbcTemplate.query(
                    "select * from tipoExame",
                    (rs, rowNum) ->
                            new TipoExame(
                                    rs.getLong("tipoexameid"),
                                    rs.getString("designacao"),
                                    rs.getString("descricao"),
                                    rs.getDouble("preco"),
                                    rs.getBoolean("disponivel")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TipoExame findTipoExameById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from tipoExame where tipoexameid = ?", new Object[]{id},
                    (rs, rowNum) ->
                            new TipoExame(
                                    rs.getLong("tipoexameid"),
                                    rs.getString("designacao"),
                                    rs.getString("descricao"),
                                    rs.getDouble("preco"),
                                    rs.getBoolean("disponivel")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int create(TipoExame tipoExame) {
        String sql = "insert into tipoExame (designacao, descricao, preco, disponivel) values (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, tipoExame.getDesignacao(), tipoExame.getDescricao(), tipoExame.getPreco(), tipoExame.isDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(long id, TipoExame tipoExame) {
        String sql = "UPDATE tipoExame set designacao = ?, descricao = ?, preco = ?, disponivel = ? where tipoexameid = " + id;
        try {
            return jdbcTemplate.update(sql, tipoExame.getDesignacao(), tipoExame.getDescricao(), tipoExame.getPreco(), tipoExame.isDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int indisponibilizarExame(long id) {
        String sql = "update tipoExame set disponivel = false where tipoexameid = " + id;
        try {
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

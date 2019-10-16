package com.kliniek.api.repository;

import com.kliniek.api.model.TipoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoConsultaRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TipoConsulta> findAllTiposConsultas() {
        try {
            return jdbcTemplate.query(
                    "select * from tipoConsulta",
                    (rs, rowNum) ->
                            new TipoConsulta(
                                    rs.getLong("tipoconsultaid"),
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

    public TipoConsulta findTipoConsultaById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from tipoConsulta where tipoconsultaid = ?", new Object[]{id},
                    (rs, rowNum) ->
                            new TipoConsulta(
                                    rs.getLong("tipoconsultaid"),
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

    public int create(TipoConsulta tipoConsulta) {
        String sql = "insert into tipoConsulta (designacao, descricao, preco, disponivel) values (?, ?, ?, ?)";
        try {
            return jdbcTemplate.update(sql, tipoConsulta.getDesignacao(), tipoConsulta.getDescricao(), tipoConsulta.getPreco(), tipoConsulta.isDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(long id, TipoConsulta tipoConsulta) {
        String sql = "UPDATE tipoConsulta set designacao = ?, descricao = ?, preco = ?, disponivel = ? where tipoconsultaid = " + id;
        try {
            return jdbcTemplate.update(sql, tipoConsulta.getDesignacao(), tipoConsulta.getDescricao(), tipoConsulta.getPreco(), tipoConsulta.isDisponivel());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int indisponibilizarConsulta(long id) {
        String sql = "update tipoConsulta set disponivel = false where tipoconsultaid = " + id;
        try {
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

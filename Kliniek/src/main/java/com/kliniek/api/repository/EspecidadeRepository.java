package com.kliniek.api.repository;

import com.kliniek.api.model.Especialidade;
import com.kliniek.api.model.Exame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin(origins = "*")
@Repository
public class EspecidadeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Especialidade> findAll() {
        String sql = "select * from especialidade";
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Especialidade(
                                    rs.getLong("especialidadeid"),
                                    rs.getString("nome"),
                                    rs.getString("descricao")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Especialidade findEspecialidadeById(long id) {
        String sql = "select * from especialidade where especialidadeid = ?";
        try {
            return jdbcTemplate.queryForObject(
                    sql, new Object[]{id},
                    (rs, rowNum) ->
                            new Especialidade(
                                    rs.getLong("especialidadeid"),
                                    rs.getString("nome"),
                                    rs.getString("descricao")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

package com.kliniek.api.repository;

import com.kliniek.api.model.DiaSemana;
import com.kliniek.api.model.Disponibilidade;
import com.kliniek.api.model.Periodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DisponibilidadeRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int create(Disponibilidade disponibilidade) {
        try {
            return jdbcTemplate.update("insert into disponibilidade values (?, ?, ?)",
                    disponibilidade.getMedicoid(),
                    disponibilidade.getDiaSemana().getDiaid(),
                    disponibilidade.getPeriodo().getPeriodoid()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(Disponibilidade disponibilidade) {
        try {
            String sql = "update disponibilidade set periodoid = ? where diaid = " + disponibilidade.getDiaSemana().getDiaid() +
                    " and medicoid = " + disponibilidade.getMedicoid();
            return jdbcTemplate.update(sql,
                    disponibilidade.getPeriodo().getPeriodoid()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Disponibilidade> getHorarioTodosMedicos() {
        String sql = "select * from disponibilidade inner join diaSemana on disponibilidade.diaid = diaSemana.diaid" +
                " inner join periodo on periodo.periodoid = disponibilidade.periodoid";
        return executeMultipleObjectQuery(sql);
    }

    public List<Disponibilidade> getHorarioMedico(long medicoid) {
        String sql = "select * from disponibilidade inner join diaSemana on disponibilidade.diaid = diaSemana.diaid" +
                " inner join periodo on periodo.periodoid = disponibilidade.periodoid where medicoid = " + medicoid;
        return executeMultipleObjectQuery(sql);
    }

    public int deleteHorario(long medicoid, long diaid) {
        return jdbcTemplate.update("delete from disponibilidade where medicoid = " + medicoid + " and diaid = diaid");
    }

    private List<Disponibilidade> executeMultipleObjectQuery(String sql) {
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Disponibilidade(
                                    new DiaSemana(
                                            rs.getLong("diaid"),
                                            rs.getString("designacao")
                                    ),
                                    new Periodo(
                                            rs.getLong("periodoid"),
                                            rs.getString("p_designacao"),
                                            rs.getString("horainicio"),
                                            rs.getString("horafim")
                                    ),
                                    rs.getLong("medicoid")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

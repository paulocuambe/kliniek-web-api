package com.kliniek.api.repository;

import com.kliniek.api.model.Consulta;
import com.kliniek.api.model.Especialidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConsultaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Consulta> findAll() {
        String sql = "Select * from consulta";
        return executeMultipleObjectQuery(sql);
    }

    public Consulta findConsultaById(long id) {
        try {
            String sql = "select * from consulta where consultaid = " + id;
            return jdbcTemplate.queryForObject(
                    sql, new Object[]{id},
                    (rs, rowNum) ->
                            new Consulta(
                                    rs.getLong("consultaid"),
                                    rs.getLong("tipoconsultaid"),
                                    rs.getLong("medicoid"),
                                    rs.getLong("pacienteid"),
                                    rs.getLong("recepcionistaid"),
                                    rs.getDate("data"),
                                    rs.getString("hora"),
                                    rs.getString("descricao"),
                                    rs.getString("prescricao"),
                                    rs.getString("observacao"),
                                    rs.getBoolean("urgente")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Consulta> findAllConsultasDoPaciente(long id) {
        String sql = "Select * from consulta where pacienteid = " + id;
        return executeMultipleObjectQuery(sql);
    }

    public List<Consulta> findAllConsultasDoMedico(long id) {
        String sql = "Select * from consulta where medicoid = " + id;
        return executeMultipleObjectQuery(sql);
    }

    public int marcarConsulta(Consulta consulta) {
        try {
            String sql = "INSERT INTO consulta(tipoconsultaid, medicoid, pacienteid, " +
                    "dia, hora, descricao, urgente)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            return jdbcTemplate.update(sql, consulta.getTipoconsultaid(), consulta.getMedicoid(), consulta.getPacienteid(),
                    consulta.getData(), consulta.getHora(), consulta.getDescricao(), consulta.isUrgente());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int prescricaoMedica(long id, Consulta consulta) {
        try {
            String sql = "UPDATE consulta SET prescricao=?, observacao=?, realizada = true WHERE consultaid = " + id;
            return jdbcTemplate.update(sql, consulta.getPacienteid(), consulta.getObservacao());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int cancelarConsulta(long id) {
        try {
            String sql = "DELETE from consulta where consultaid= " + id;
            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private List<Consulta> executeMultipleObjectQuery(String sql) {
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Consulta(
                                    rs.getLong("consultaid"),
                                    rs.getLong("tipoconsultaid"),
                                    rs.getLong("medicoid"),
                                    rs.getLong("pacienteid"),
                                    rs.getLong("recepcionistaid"),
                                    rs.getDate("data"),
                                    rs.getString("hora"),
                                    rs.getString("descricao"),
                                    rs.getString("prescricao"),
                                    rs.getString("observacao"),
                                    rs.getBoolean("urgente")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


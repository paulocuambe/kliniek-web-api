package com.kliniek.api.repository;

import com.kliniek.api.model.Exame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalTime;
import java.util.List;

@CrossOrigin(origins = "*")
@Repository
public class ExameRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TipoExameRepository tipoExameRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    public List<Exame> findAll() {
        String sql = "Select * from exame";
        return executeMultipleObjectQuery(sql);
    }

    public Exame findExameById(long id) {
        String sql = "Select * from exame where exameid = " + id;
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) ->
                        new Exame(
                                rs.getLong("exameid"),
                                tipoExameRepository.findTipoExameById(rs.getLong("tipoexameid")).getDesignacao(),
                                rs.getLong("tipoexameid"),
                                pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getPrimeiroNome() + " " + pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getApelido(),
                                rs.getLong("pacienteid"),
                                rs.getLong("recepcionistaid"),
                                rs.getDate("data"),
                                rs.getString("hora"),
                                rs.getString("observacao"),
                                rs.getBoolean("positivo"),
                                rs.getBoolean("urgente"),
                                rs.getBoolean("realizado"),
                                rs.getBoolean("pago")
                        )
        );
    }

    public List<Exame> findExamesByPacienteId(long id) {
        String sql = "Select * from exame where pacienteid = " + id;
        return executeMultipleObjectQuery(sql);
    }

    public List<Exame> findExamesByMedicoId(long id) {
        String sql = "Select * from exame where medicoid = " + id;
        return executeMultipleObjectQuery(sql);
    }

    public int marcarExame(Exame exame) {
        try {
            String sql = "INSERT INTO exame(tipoexameid, pacienteid, recepcionistaid, data, hora, urgente) " +
                    " VALUES (?, ?, ?, ?, ?, ?)";

            return jdbcTemplate.update(sql, exame.getTipoexameid(), exame.getPacienteid(), 12,
                    exame.getData(), LocalTime.parse(exame.getHora()), exame.isUrgente());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int resultadoExame(long id, Exame exame) {
        try {
            String sql = "UPDATE exame SET  observacao=?, positivo=?, realizado=true " +
                    "WHERE exameid = " + id;

            return jdbcTemplate.update(sql, exame.getObservacao(), exame.isPositivo());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int pagarExame(long id) {
        try {
            String sql = "UPDATE exame SET  pago =true " +
                    "WHERE exameid = " + id;

            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int cancelarExame(long id) {
        try {
            String sql = "delete from exame where exameid =" + id;

            return jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private List<Exame> executeMultipleObjectQuery(String sql) {
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Exame(
                                    rs.getLong("exameid"),
                                    tipoExameRepository.findTipoExameById(rs.getLong("tipoexameid")).getDesignacao(),
                                    rs.getLong("tipoexameid"),
                                    pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getPrimeiroNome() + " " + pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getApelido(),
                                    rs.getLong("pacienteid"),
                                    rs.getLong("recepcionistaid"),
                                    rs.getDate("data"),
                                    rs.getString("hora"),
                                    rs.getString("observacao"),
                                    rs.getBoolean("positivo"),
                                    rs.getBoolean("urgente"),
                                    rs.getBoolean("realizado"),
                                    rs.getBoolean("pago")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

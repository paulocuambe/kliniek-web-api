package com.kliniek.api.repository;

import com.kliniek.api.model.Consulta;
import com.kliniek.api.model.Especialidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public class ConsultaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PacienteRepository pacienteRepository;

    @Autowired
    MedicoRepository medicoRepository;

    public List<Consulta> findAll() {
        String sql = "Select * from consulta";
        return executeMultipleObjectQuery(sql);
    }

    public Consulta findConsultaById(long id) {
        try {
            String sql = "select * from consulta where consultaid = "+id;
            return jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) ->
                            new Consulta(
                                    rs.getLong("consultaid"),
                                    rs.getLong("tipoconsultaid"),
                                    medicoRepository.findMedicoById(rs.getLong("medicoid")).getPrimeiroNome() + " " +medicoRepository.findMedicoById(rs.getLong("medicoid")).getApelido(),
                                    rs.getLong("medicoid"),
                                    pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getPrimeiroNome() + " "+ pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getApelido(),
                                    rs.getLong("pacienteid"),
                                    rs.getLong("recepcionistaid"),
                                    rs.getDate("dia"),
                                    rs.getString("hora"),
                                    rs.getString("descricao"),
                                    rs.getString("prescricao"),
                                    rs.getString("observacao"),
                                    rs.getBoolean("urgente"),
                                    rs.getBoolean("realizada")
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
                    consulta.getData(), LocalTime.parse(consulta.getHora()), consulta.getDescricao(), consulta.isUrgente());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int prescricaoMedica(long id, Consulta consulta) {
        try {
            String sql = "UPDATE consulta SET prescricao=?, observacao=?, realizada = true WHERE consultaid = " + id;
            return jdbcTemplate.update(sql, consulta.getPrescricao(), consulta.getObservacao());
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

    public int pagarConsulta(long id) {
        try {
            String sql = "update consulta set pago = true where consultaid= " + id;
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
                                    medicoRepository.findMedicoById(rs.getLong("medicoid")).getPrimeiroNome() + " " +medicoRepository.findMedicoById(rs.getLong("medicoid")).getApelido(),
                                    rs.getLong("medicoid"),
                                    pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getPrimeiroNome() + " "+ pacienteRepository.findPacienteById(rs.getLong("pacienteid")).getApelido(),
                                    rs.getLong("pacienteid"),
                                    rs.getLong("recepcionistaid"),
                                    rs.getDate("dia"),
                                    rs.getString("hora"),
                                    rs.getString("descricao"),
                                    rs.getString("prescricao"),
                                    rs.getString("observacao"),
                                    rs.getBoolean("urgente"),
                                    rs.getBoolean("realizada")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}


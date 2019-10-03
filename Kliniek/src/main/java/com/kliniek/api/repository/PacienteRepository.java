package com.kliniek.api.repository;

import com.kliniek.api.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PacienteRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PessoaRepository pessoaRepository;

    public int create(Paciente paciente) {
        try {
            if (pessoaRepository.create(paciente) > 0) {
                String sql = "INSERT INTO PACIENTE VALUES (?, ?, ?)";
                return jdbcTemplate.update(sql,
                        pessoaRepository.findPessoaByBI(paciente.getBi()).getPessoaoid(),
                        paciente.getProfissao(),
                        paciente.getEstadoActual()
                );
            } else
                return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(long id, Paciente paciente) {
        try {
            if (pessoaRepository.update(id, paciente) > 0) {
                String sql = "UPDATE Paciente set profissao = ? where pacienteid = " + id;
                return jdbcTemplate.update(sql, paciente.getProfissao());
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateEstado(long id, String estado) {
        try {
            String sql = "UPDATE Paciente set estadoactual = ? where pacienteid = " + id;
            return jdbcTemplate.update(sql, estado);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Paciente> findAll() {
        String sql = "select * from paciente inner join pessoa on pacienteid = pessoaid";
        return executeMultipleObjectQuery(sql);
    }

    public List<Paciente> findPacienteByEstado(String estado) {
        String sql = "select * from paciente inner join pessoa on pacienteid = pessoaid where estadoactual = '" + estado + "'";
        return executeMultipleObjectQuery(sql);
    }

    public List<Paciente> findPaciente(String nome) {
        nome = "'%" + nome + "%'";
        String sql = "select * from paciente inner join pessoa on pacienteid = pessoaid where primeironome Like " + nome + " or apelido Like " + nome;
        return executeMultipleObjectQuery(sql);
    }

    public Paciente findPacienteById(long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "select * from paciente inner join pessoa on pacienteid = pessoaid where pacienteid = ?", new Object[]{id},
                    (rs, rowNum) ->
                            new Paciente(
                                    rs.getLong("pessoaid"),
                                    rs.getLong("usuarioid"),
                                    rs.getString("bi"),
                                    rs.getString("nuit"),
                                    rs.getString("primeironome"),
                                    rs.getString("apelido"),
                                    rs.getString("email"),
                                    rs.getDate("datanascimento"),
                                    rs.getString("sexo"),
                                    rs.getString("endereco"),
                                    rs.getDate("dataregisto"),
                                    pessoaRepository.findAllTelefones(rs.getLong("pessoaid")),
                                    rs.getString("profissao"),
                                    rs.getString("estadoactual")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Paciente> executeMultipleObjectQuery(String sql) {
        try {
            return jdbcTemplate.query(
                    sql,
                    (rs, rowNum) ->
                            new Paciente(
                                    rs.getLong("pessoaid"),
                                    rs.getLong("usuarioid"),
                                    rs.getString("bi"),
                                    rs.getString("nuit"),
                                    rs.getString("primeironome"),
                                    rs.getString("apelido"),
                                    rs.getString("email"),
                                    rs.getDate("datanascimento"),
                                    rs.getString("sexo"),
                                    rs.getString("endereco"),
                                    rs.getDate("dataregisto"),
                                    pessoaRepository.findAllTelefones(rs.getLong("pessoaid")),
                                    rs.getString("profissao"),
                                    rs.getString("estadoactual")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}

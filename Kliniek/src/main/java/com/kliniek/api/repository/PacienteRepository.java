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
        String sql = "INSERT INTO PACIENTE VALUES (?, ?, ?)";

        if (pessoaRepository.create(paciente) > 0) {
            long id = pessoaRepository.findPessoaByBI(paciente.getBi()).getPessoaoid();
            return jdbcTemplate.update(sql,
                    id,
                    paciente.getProfissao(),
                    paciente.getEstadoActual()
            );
        } else
            return 0;

    }

    public int update(long id, Paciente paciente) {
        String sql = "UPDATE Paciente set profissao = ? where pacienteid = " + id;
        if (pessoaRepository.update(id, paciente) > 0)
            return jdbcTemplate.update(sql,
                    paciente.getProfissao()
            );
        else return 0;
    }

    public int updateEstado(long id, String estado) {
        String sql = "UPDATE Paciente set estadoactual = ? where pacienteid = " + id;
        return jdbcTemplate.update(sql, estado);
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
                                rs.getString("contactoprimario"),
                                rs.getDate("dataregisto"),
                                rs.getString("profissao"),
                                rs.getString("estadoactual")
                        )
        );
    }

    private List<Paciente> executeMultipleObjectQuery(String sql) {
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
                                rs.getString("contactoprimario"),
                                rs.getDate("dataregisto"),
                                rs.getString("profissao"),
                                rs.getString("estadoactual")
                        )
        );
    }


}

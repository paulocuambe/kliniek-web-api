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

    public int create(Paciente paciente){
        String sql = "INSERT INTO PACIENTE VALUES (?, ?, ?)";

        if(pessoaRepository.create(paciente) > 0){
            long id = pessoaRepository.findPessoaByBI(paciente.getBi()).getPessoaoid();
            return jdbcTemplate.update(sql,
                    id,
                    paciente.getProfissao(),
                    paciente.getEstadoActual()
            );
        } else
            return 0;

    }

    public int update(long id, Paciente paciente){
        String sql = "UPDATE Paciente set profissao = ?, estadoactual = ? where pessaoid = " + id;

        return jdbcTemplate.update(sql,
                paciente.getProfissao(),
                paciente.getEstadoActual()
        );
    }

    public List<Paciente> findAll(){
        return jdbcTemplate.query(
                "select * from paciente as p inner join pessoa as pes on pacienteid = pessoaid",
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

    public Paciente findPacienteById(long id){
        return jdbcTemplate.queryForObject(
                "select * from usuario where usuarioid = ?", new Object[]{id},
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

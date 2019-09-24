package com.kliniek.api.repository;

import com.kliniek.api.model.Paciente;
import com.kliniek.api.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PessoaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int create(Pessoa pessoa){
        String sql = "INSERT INTO Pessoa " +
                "(bi, nuit, primeironome, apelido, email, datanascimento, sexo, endereco, contactoprimario)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                pessoa.getBi(),
                pessoa.getNuit(),
                pessoa.getPrimeiroNome(),
                pessoa.getApelido(),
                pessoa.getEmail(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getEndereco(),
                pessoa.getContactoPrimario()
        );
    }

    public int update(long id, Pessoa pessoa){
        String sql = "UPDATE Pessoa SET bi = ?, nuit = ?, primeironome = ?, apelido = ?, email = ?, datanascimento = ?," +
                " sexo = ?, endereco = ?, contactoprimario = ? where pacienteid = " + id;
        return jdbcTemplate.update(sql,
                pessoa.getBi(),
                pessoa.getNuit(),
                pessoa.getPrimeiroNome(),
                pessoa.getApelido(),
                pessoa.getEmail(),
                pessoa.getDataNascimento(),
                pessoa.getSexo(),
                pessoa.getEndereco(),
                pessoa.getContactoPrimario()
        );
    }

    public Pessoa findPessoaByBI(String bi){
        return jdbcTemplate.queryForObject(
                "select * from pessoa where bi = ?", new Object[]{bi},
                (rs, rowNum) ->
                        new Pessoa(
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
                                rs.getDate("dataregisto")
                        )
        );
    }
}

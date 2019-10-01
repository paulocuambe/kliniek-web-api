package com.kliniek.api.repository;

import com.kliniek.api.model.Pessoa;
import com.kliniek.api.model.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public int create(Pessoa pessoa) {
        try {
            String sql = "INSERT INTO Pessoa " +
                    "(bi, nuit, primeironome, apelido, email, datanascimento, sexo, endereco, contactoprimario)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return executeUpdateQuery(sql, pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(long id, Pessoa pessoa) {
        try {
            String sql = "UPDATE Pessoa SET bi = ?, nuit = ?, primeironome = ?, apelido = ?, email = ?, datanascimento = ?," +
                    " sexo = ?, endereco = ?, contactoprimario = ? where pessoaid = " + id;
            return executeUpdateQuery(sql, pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Pessoa findPessoaByBI(String bi) {
        String sql = "select * from pessoa where bi = ?";
        return executeSingleObjectQuery(sql, bi);
    }

    public Pessoa findPessoaByEmail(String email) {
        String sql = "select * from pessoa where email = ?";
        return executeSingleObjectQuery(sql, email);
    }

    public Pessoa findPessoaByNuit(String nuit) {
        String sql = "select * from pessoa where nuit = ?";
        return executeSingleObjectQuery(sql, nuit);
    }

    private int executeUpdateQuery(String sql, Pessoa pessoa) {
        try {
            return jdbcTemplate.update(sql,
                    pessoa.getBi(),
                    pessoa.getNuit(),
                    pessoa.getPrimeiroNome(),
                    pessoa.getApelido(),
                    pessoa.getEmail(),
                    pessoa.getDataNascimento(),
                    pessoa.getSexo(),
                    pessoa.getEndereco()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Pessoa executeSingleObjectQuery(String sql, Object o) {
        try {
            return jdbcTemplate.queryForObject(
                    sql, new Object[]{o},
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
                                    rs.getDate("dataregisto")
                            )
            );
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }

    }

    public int createTelefone(Telefone telefone) {
        try {
            String sql = "insert into telefone values (?, ?)";
            return jdbcTemplate.update(sql,
                    telefone.getPessoaid(),
                    telefone.getNumero()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Telefone> findAllTelefones(long pessoaid) {
        try {
            return jdbcTemplate.query(
                    "select * from telefone where pessoaid = " + pessoaid,
                    (rs, rowNum) ->
                            new Telefone(
                                    rs.getLong("pessoaid"),
                                    rs.getString("numerotelefone")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

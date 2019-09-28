package com.kliniek.api.repository;

import com.kliniek.api.model.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "select count(*) from pessoa where email = '" + email + "'";
        return executeSingleObjectQuery(sql, email);
    }

    public Pessoa findPessoaByNuit(String nuit) {
        String sql = "select count(*) from pessoa where nuit = '" + nuit + "'";
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
                    pessoa.getEndereco(),
                    pessoa.getContactoPrimario()
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
                                    rs.getString("contactoprimario"),
                                    rs.getDate("dataregisto")
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}

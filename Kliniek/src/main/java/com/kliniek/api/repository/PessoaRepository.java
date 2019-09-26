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
        String sql = "INSERT INTO Pessoa " +
                "(bi, nuit, primeironome, apelido, email, datanascimento, sexo, endereco, contactoprimario)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        if (atributosUnicosNaoRepetidos(pessoa))
            return executeUpdateQuery(sql, pessoa);
        else
            return 0;
    }

    public int update(long id, Pessoa pessoa) {
        String sql = "UPDATE Pessoa SET bi = ?, nuit = ?, primeironome = ?, apelido = ?, email = ?, datanascimento = ?," +
                " sexo = ?, endereco = ?, contactoprimario = ? where pessoaid = " + id;
        return executeUpdateQuery(sql, pessoa);
    }

    public Pessoa findPessoaByBI(String bi) {
        String sql = "select * from pessoa where bi = ?";
        return executeSingleObjectQuery(sql, bi);
    }

    public int countEmail(String email) {
        return jdbcTemplate.queryForObject("select count(*) from pessoa where email = '" + email + "'", Integer.class);
    }

    public int countBi(String bi) {
        return jdbcTemplate.queryForObject("select count(*) from pessoa where bi = '" + bi + "'", Integer.class);
    }

    public int countNuit(String nuit) {
        return jdbcTemplate.queryForObject("select count(*) from pessoa where nuit = '" + nuit + "'", Integer.class);
    }

    private int executeUpdateQuery(String sql, Pessoa pessoa) {
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
            return null;
        }

    }

    private boolean atributosUnicosNaoRepetidos(Pessoa pessoa) {
        if (countEmail(pessoa.getEmail()) <= 0 && countBi(pessoa.getBi()) <= 0 && countNuit(pessoa.getNuit()) <= 0)
            return true;
        else
            return false;
    }

}

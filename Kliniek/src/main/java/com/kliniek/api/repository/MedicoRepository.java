package com.kliniek.api.repository;

import com.kliniek.api.model.Medico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicoRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PessoaRepository pessoaRepository;

    public int create(Medico medico) {
        String sql = "INSERT INTO Medico VALUES (?, ?)";

        if (pessoaRepository.create(medico) > 0) {
            long id = pessoaRepository.findPessoaByBI(medico.getBi()).getPessoaoid();
            return jdbcTemplate.update(sql, id, medico.getCarteiraProfissional());
        } else {
            return 0;
        }
    }

    public int update(long id, Medico medico) {
        return pessoaRepository.update(id, medico);
    }

    public int updateCarteiraProfissional(long id, String carteiraProfissional) {
        String sql = "UPDATE Medico set carteiraprofissional = ? where medicoid = " + id;
        return jdbcTemplate.update(sql, carteiraProfissional);
    }

    public List<Medico> findAll() {
        String sql = "select * from medico inner join pessoa on medicoid = pessoaid";
        return executeMultipleObjectQuery(sql);
    }

    public List<Medico> findMedico(String nome) {
        nome = "'%" + nome + "%'";
        String sql = "select * from medico inner join pessoa on medicoid = pessoaid where primeironome Like " + nome + " or apelido Like " + nome;
        return executeMultipleObjectQuery(sql);
    }

    public Medico findMedicoById(long id) {
        String sql = "select * from Medico inner join pessoa on pessoaid = medicoid where medicoid = ?";
        return executeSingleObjectQuery(sql, id);
    }

    public Medico findMedicoByCarteiraProfissional(String carteiraProfissional) {
        String sql = "select * from Medico inner join pessoa on pessoaid = medicoid where carteiraProfissional = ?";
        return executeSingleObjectQuery(sql, carteiraProfissional);
    }

    private Medico executeSingleObjectQuery(String sql, Object o) {
        try {
            return jdbcTemplate.queryForObject(
                    sql, new Object[]{o},
                    (rs, rowNum) ->
                            new Medico(
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
                                    rs.getString("carteiraprofissional")
                            )
            );
        } catch (Exception e) {
            return null;
        }
    }

    private List<Medico> executeMultipleObjectQuery(String sql) {
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new Medico(
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
                                rs.getString("carteiraprofissional")
                        )
        );
    }

}

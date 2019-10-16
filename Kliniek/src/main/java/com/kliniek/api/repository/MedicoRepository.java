package com.kliniek.api.repository;

import com.kliniek.api.model.Especialidade;
import com.kliniek.api.model.Medico;
import com.kliniek.api.model.Telefone;
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
        try {
            if (pessoaRepository.create(medico) > 0) {
                String sql = "INSERT INTO Medico VALUES (?, ?, ?)";
                long medicoid = pessoaRepository.findPessoaByBI(medico.getBi()).getPessoaoid();

                if (medico.getContactos() != null)
                    for (Telefone telefone : medico.getContactos()) {
                        telefone.setPessoaid(medicoid);
                        pessoaRepository.createTelefone(telefone);
                    }

                return jdbcTemplate.update(sql, medicoid, medico.getCarteiraProfissional(), medico.getEspecialidade().getEspecialidadeid());
            } else return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int update(long id, Medico medico) {
        return pessoaRepository.update(id, medico);
    }

    public int updateCarteiraProfissional(long id, String carteiraProfissional) {
        String sql = "UPDATE Medico set carteiraprofissional = ? where medicoid = " + id;
        try {
            return jdbcTemplate.update(sql, carteiraProfissional);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Medico> findAll() {
        String sql = "select * from medico inner join pessoa on medico.medicoid = pessoa.pessoaid " +
                "inner join especialidade on medico.especialidadeid = especialidade.especialidadeid where eliminado = false";
        return executeMultipleObjectQuery(sql);
    }

    public List<Medico> findMedico(String nome) {
        nome = "'%" + nome + "%'";
        String sql = "select * from medico inner join pessoa on medico.medicoid=pessoa.pessoaid " +
                "inner join especialidade on medico.especialidadeid=especialidade.especialidadeid" +
                " where eliminado = false and (primeironome Like " + nome + " or apelido Like " + nome + ")";
        return executeMultipleObjectQuery(sql);
    }

    public Medico findMedicoById(long id) {
        String sql = "select * from medico inner join pessoa on medico.medicoid=pessoa.pessoaid " +
                "inner join especialidade on medico.especialidadeid=especialidade.especialidadeid" +
                " where medicoid = ?";
        return executeSingleObjectQuery(sql, id);
    }

    public Medico findMedicoByCarteiraProfissional(String carteiraProfissional) {
        String sql = "select * from medico inner join pessoa on medico.medicoid = pessoa.pessoaid " +
                "inner join especialidade on medico.especialidadeid = especialidade.especialidadeid " +
                "where carteiraProfissional = ? and eliminado = false";
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
                                    rs.getDate("dataregisto"),
                                    pessoaRepository.findAllTelefones(rs.getLong("pessoaid")),
                                    rs.getString("carteiraprofissional"),
                                    new Especialidade(
                                            rs.getLong("especialidadeid"),
                                            rs.getString("nome"),
                                            rs.getString("descricao"))
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Medico> executeMultipleObjectQuery(String sql) {
        try {
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
                                    rs.getDate("dataregisto"),
                                    pessoaRepository.findAllTelefones(rs.getLong("pessoaid")),
                                    rs.getString("carteiraprofissional"),
                                    new Especialidade(
                                            rs.getLong("especialidadeid"),
                                            rs.getString("nome"),
                                            rs.getString("descricao"))
                            )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteMedico(long id) {
        try {
            String sql = "UPDATE Medico set eliminado = ? where medicoid = " + id;
            return jdbcTemplate.update(sql, true);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

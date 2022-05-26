import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mysql.cj.x.protobuf.MysqlxPrepare.Prepare;

public class Pessoa {
    private int codigo;
    private String nome;
    private String fone;
    private String email;

    //mapeamento objeto relacional
    //ORM: Hibernate, EclipseLink
    public void inserir() throws Exception{
    
        String sql = "INSERT INTO tb_pessoa (nome, fone, email) VALUES (?, ?, ?)";
        
        ConnectionFactory factory = new ConnectionFactory();
        Connection conexao = factory.getConnection();
    
        PreparedStatement ps = conexao.prepareStatement(sql);
        ps.setString(1, nome);
        ps.setString(2, fone);
        ps.setString(3, email);
        
        ps.execute();
        
        ps.close();
        conexao.close();
    }

    public void atualizar() throws Exception{
        String sql = "UPDATE tb_pessoa SET nome = ?, fone = ?, email = ? WHERE cod_pessoa = ?";
        ConnectionFactory factory = new ConnectionFactory();
        try(Connection conexao = factory.getConnection();

                PreparedStatement ps = conexao.prepareStatement(sql)){
            ps.setString(1, nome);
            ps.setString(2, fone);
            ps.setString(3, email);
            ps.setInt(4, codigo);
            ps.execute();
        }
    }

    public void apagar() throws Exception{
        String sql = "DELETE FROM tb_pessoa WHERE cod_pessoa = ?";
        try(
            Connection conexao = new ConnectionFactory().getConnection();
        
            PreparedStatement ps = conexao.prepareStatement(sql);
        ){
            
            ps.setInt(1, codigo);
            ps.execute();
        }
    }

    public static void listar() throws Exception{
        String sql = "SELECT * FROM tb_pessoa";
        try(
            Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement ps = conexao.prepareStatement(sql);
            java.sql.ResultSet rs = ps.executeQuery();
            ){            
            
            while(rs.next()){
                int codigo = rs.getInt("cod_pessoa");
                String nome = rs.getString("nome");
                String fone = rs.getString("fone");
                String email = rs.getString("email");
                System.out.printf(
                    "código: %d, nome: %s, fone: %s, e-mail: %s\n",
                    codigo, nome, fone, email
                );
            }           

        }
    }


    public Pessoa (String nome, String fone, String email){
        this(0, nome, fone, email);    
    }

    public Pessoa(int codigo, String nome, String fone, String email){
        setCodigo(codigo);
        setNome(nome);
        setFone(fone);
        setEmail(email);
    }

    public Pessoa(int codigo){
        this(codigo, null, null, null);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }
    public String getFone() {
        return fone;
    }

    public String getNome() {
        return nome;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
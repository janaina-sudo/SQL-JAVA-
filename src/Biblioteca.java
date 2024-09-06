import java.sql.*;
import java.util.List;

public class Biblioteca {

    // Conectar ao banco de dados
    private Connection connect() {
        String url = "jdbc:sqlite:/home/janaina-santos/Documentos/SQLJAVA/livraria/src/bancodedados.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Adicionar Estudante 
    public void adicionarEstudante(String nome, String email) {
        String sql = "INSERT INTO Estudante(nome, email) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Adicionar Livro
    public void adicionarLivro(String titulo, String autor) {
        String sql = "INSERT INTO Livro(titulo, autor) VALUES(?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, titulo);
            pstmt.setString(2, autor);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Adicionar Emprestimo
    public void adicionarEmprestimo(int estudanteId, int livroId, String dataEmprestimo, String dataDevolucao) {
        String sql = "INSERT INTO Emprestimo(id_estudante, id_livro, data_aluguel, data_devolucao) VALUES(?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, estudanteId);
            pstmt.setInt(2, livroId);
            pstmt.setString(3, dataEmprestimo);
            pstmt.setString(4, dataDevolucao);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Remover estudante
    public void removerEstudante(int id) {
        String sql = "DELETE FROM Estudante WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //  Remover Livro
    public void removerLivro(int id) {
        String sql = "DELETE FROM Livro WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Atualizar dados do Estudante
    public void atualizarEstudante(int id, String novoNome, String novoEmail) {
        String sql = "UPDATE Estudante SET nome = ?, matricula = ? WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, novoNome);
            pstmt.setString(2, novoEmail);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Pesquisar dados no banco
    public void pesquisarEstudante(int id) {
        String sql = "SELECT * FROM Estudante WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Buscar por substring usando LIKE
    public void buscarEstudantesPorNome(String nomeSubstring) {
        String sql = "SELECT * FROM Estudante WHERE nome LIKE ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nomeSubstring + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Adicionar uma lista de estudantes
    public void adicionarEstudantes(List<Estudante> estudantes) {
        String sql = "INSERT INTO Estudante(nome, matricula) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Estudante estudante : estudantes) {
                pstmt.setString(1, estudante.getNome());
                pstmt.setString(2, estudante.getEmail());
                pstmt.addBatch();  
            }
            pstmt.executeBatch();  
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    // Adicionar uma lista de livros
    public void adicionarLivros(List<Livro> livros) {
        String sql = "INSERT INTO Livro(titulo, autor) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Livro livro : livros) {
                pstmt.setString(1, livro.getTitulo());
                pstmt.setString(2, livro.getAutor());
                pstmt.addBatch();  // Adiciona ao lote de inserções
            }
            pstmt.executeBatch();  // Executa todas as inserções de uma vez
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //visualizar livros
    public void visualizarLivros() {

        String sql = "SELECT * FROM Livro";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Título: " + rs.getString("titulo"));
                System.out.println("Autor: " + rs.getString("autor"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //viaualizar estudantes
    public void visualizarEstudantes() {

        String sql = "SELECT * FROM Estudante";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Email: " + rs.getString("email"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        Biblioteca db = new Biblioteca();

        /* Gerando a lista de livros
        List<Livro> livros = List.of(
            new Livro(1, "Diario de Anne Frank", "Anne Frank"),
            new Livro(2, "Harry Potter", "J. K. Rowling"),
            new Livro(3, "O Pequeno Príncipe", "Antoine de Saint-Exupéry"),
            new Livro(4, "Dom Quixote", "Miguel de Cervantes"),
            new Livro(5, "1984", "George Orwell")
        );

        //adicionando uma lista de livros no banco de dados 
        db.adicionarLivros(livros);

        //visualizando os livros adicionados 
        db.visualizarLivros();
        
        */

        /* Adicionando  estudantes com segundo nome similar
        db.adicionarEstudante("Ana Kelly", "anakelly@gmail.com");
        db.adicionarEstudante("Amanda Kelly", "amandakelly@gmail.com");
        */
        
        db.buscarEstudantesPorNome("Kelly");
        
    }
}

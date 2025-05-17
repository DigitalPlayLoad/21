package pkg21.model;

public class Pessoa {
    
  //atributos
  private String nome;
  private String sobrenome; 
  private String email;
  private String senha;

  //Construtor
 public Pessoa(String nome, String sobrenome, String email, String senha){
    this.nome = nome;
    this.sobrenome = sobrenome;
    this.email = email;
    this.senha = senha;  
 }   

  //Getters
  public String getNome() {
    return nome;
  }

  public String getSobrenome() {
    return sobrenome;
  }
  
  public String getEmail() {
      return email;
  }

  public String getSenha() {
      return senha;
  }

  //setters
  public void setNome(String nome) {
    this.nome = nome;
  }

  public void setSobrenome(String sobrenome) {
    this.sobrenome = sobrenome;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }
    
}

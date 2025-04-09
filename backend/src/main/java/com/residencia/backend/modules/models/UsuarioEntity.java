package com.residencia.backend.modules.models;




import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Table(name = "usuario")  // Especifique explicitamente o nome da tabela
public class UsuarioEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank(message = "O nome é obrigatório")
  @Column(nullable = false)  // Garante a restrição no banco
  private String nome;  // Mantenha em português para consistência

  @CPF(message = "CPF inválido")
  @Column(unique = true, nullable = false)
  private String cpf;

  @Email(message = "Email inválido")
  @Column(unique = true, nullable = false)
  private String email;

  @Pattern(regexp = "^\\d{2}\\s?\\d{4,5}\\s?\\d{4}$",
      message = "Telefone deve seguir o padrão: DD XXXXXXXX")
  private String telefone;

  @Length(min = 6, max = 100, message = "Senha deve ter 6 a 100 caracteres")
  private String senha;

  @CreationTimestamp
  @Column(name = "dt_criacao", updatable = false)
  private LocalDateTime dataCriacao;
}

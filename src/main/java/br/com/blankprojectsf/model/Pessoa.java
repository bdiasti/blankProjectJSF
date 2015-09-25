package br.com.blankprojectsf.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "TB_PESSOA" database table.
 * 
 */
@Entity
@Table(name="\"TB_PESSOA\"")
@NamedQuery(name="TbPessoa.findAll", query="SELECT t FROM Pessoa t")
public class Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"CPF\"")
	private Long cpf;

	private String email;

	private Long idade;

	private String nome;

	private String sexo;
	
	 @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="\"RL_PESSOA_DEPARTAMENTO\"", 
               joinColumns=  @JoinColumn( name = "\"CPF\""), 
               inverseJoinColumns= @JoinColumn(name = "\"ID\"") )
	private List<Departamento> tbDepartamentos;

	public Pessoa() {
	}

	public Long getCpf() {
		return this.cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdade() {
		return this.idade;
	}

	public void setIdade(Long idade) {
		this.idade = idade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public List<Departamento> getTbDepartamentos() {
		return this.tbDepartamentos;
	}

	public void setTbDepartamentos(List<Departamento> tbDepartamentos) {
		this.tbDepartamentos = tbDepartamentos;
	}

}
package br.com.blankprojectsf.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the "TB_DEPARTAMENTO" database table.
 * 
 */
@Entity
@Table(name="\"TB_DEPARTAMENTO\"")
@NamedQuery(name="TbDepartamento.findAll", query="SELECT t FROM Departamento t")
public class Departamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"ID\"")
	private Long id;

	private String nome;

	private String sigla;

	//bi-directional many-to-many association to TbPessoa
	@ManyToMany(mappedBy="tbDepartamentos")
	private List<Pessoa> tbPessoas;

	public Departamento() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public List<Pessoa> getTbPessoas() {
		return this.tbPessoas;
	}

	public void setTbPessoas(List<Pessoa> tbPessoas) {
		this.tbPessoas = tbPessoas;
	}

}
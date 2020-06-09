/**
 * 
 */
package br.com.consultemed.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.consultemed.model.Contato;
import br.com.consultemed.utils.JPAUtils;

/**
 * @author carlosbarbosagomesfilho Classe responável pelo acesso aos dados no
 *         banco.
 */
public class ContatoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	EntityManagerFactory emf = JPAUtils.getEntityManagerFactory();
	EntityManager factory = emf.createEntityManager();

	public void salvar(Contato contato) {
		
		this.factory.getTransaction().begin();
		this.factory.persist(contato);
		this.factory.getTransaction().commit();
		this.factory.close();
	}
	
	public void remover(Long idContato) {
		
		this.factory.getTransaction().begin();
		this.factory.remove(buscaPorId(idContato));
		this.factory.getTransaction().commit();
		this.factory.close();
	}
	
	public Contato buscaPorId(Long idContato) {
		this.factory.getTransaction().begin();
		Contato contatoRemover = this.factory.find(Contato.class, idContato);
		return contatoRemover;
	}
	
	public Contato buscaPorNome(String nome) {
		this.factory.getTransaction().begin();
		Query query = 
				this.factory.createQuery("SELECT c FROM Contato c WHERE c.nome = :nome");
		query.setParameter("nome", nome);
		Contato contato = (Contato) query.getSingleResult();
		return contato;
	}
	
	
	public Contato buscaPorEmail(String email) {
		this.factory.getTransaction().begin();
		Query query = 
				this.factory.createQuery("SELECT c FROM Contato c WHERE c.email = :email" );
		query.setParameter("email", email);
		Contato contato = (Contato) query.getSingleResult();
		return contato;
	}
	
	public void editar(Contato contato) {
		this.factory.getTransaction().begin();
		this.factory.merge(contato);
		this.factory.getTransaction().commit();
		this.factory.close();
	}
	
	public List<Contato> contatos(){
		List<Contato> contatos = new ArrayList<>();
		try {
			this.factory.getTransaction().begin();
			TypedQuery<Contato> query = this.factory.createNamedQuery("Contato.findAll", Contato.class);
			contatos = query.getResultList(); 
			this.factory.getTransaction().commit();
		} catch (Exception e) {
			e.getMessage();
			this.factory.getTransaction().rollback();
		} finally {
			this.factory.close();
		}
		return contatos;
	}
}









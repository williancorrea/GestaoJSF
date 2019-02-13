package br.com.wcorrea.util.jpa;

import org.hibernate.Session;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class EntityManagerProducer {

    @Inject
    private PersistenceProperties properties;

    private EntityManagerFactory factory;

    @PostConstruct
    public void postConstruct() {
        this.factory = Persistence.createEntityManagerFactory("GestaoPU", properties.get());
    }

    @Produces
    @RequestScoped
    public Session createEntityManager() {
        return (Session) this.factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes Session manager) {
        manager.close();
    }
}

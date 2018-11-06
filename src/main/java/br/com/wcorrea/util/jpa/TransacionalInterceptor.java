package br.com.wcorrea.util.jpa;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;

@Interceptor
@Transacional
@Priority(Interceptor.Priority.APPLICATION)
public class TransacionalInterceptor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager entityManager;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        EntityTransaction trx = entityManager.getTransaction();
        boolean criador = false;

        try {
            if (!trx.isActive()) {
                /*
                 * Truque para fazer rollback no que já passou
                 * (senão, um futuro commit confirmaria até mesmo operações sem transação)
                 */
                trx.begin();
                trx.rollback();

                /*
                 * Agora sim inicia a transação
                 */
                trx.begin();

                criador = true;
            }

            return context.proceed();
        } catch (Exception e) {
            if (trx != null && criador) {
                trx.rollback();
            }
            throw e;
        } finally {
            if (trx != null && trx.isActive() && criador) {
                trx.commit();
            }
        }
    }
}
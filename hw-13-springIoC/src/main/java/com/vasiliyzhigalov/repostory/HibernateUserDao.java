package com.vasiliyzhigalov.repostory;

import com.vasiliyzhigalov.domain.User;
import com.vasiliyzhigalov.repostory.sessionmanager.DatabaseSessionHibernate;
import com.vasiliyzhigalov.repostory.sessionmanager.SessionManager;
import com.vasiliyzhigalov.repostory.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HibernateUserDao implements UserDao {

    private final SessionManagerHibernate sessionManager;

    public HibernateUserDao(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            return Optional.ofNullable(hibernateSession.find(User.class, id));
        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Optional<User> findRandomUser() {
        return findByLogin("admin");
    }

    @Override
    public Optional<User> findByLogin(String login) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            Query query = hibernateSession.createQuery("FROM User WHERE login =: setLogin");
            query.setParameter("setLogin", login);
            List<User> users = query.list();
            return users.stream().findFirst();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }


    @Override
    public long saveUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
            }
            return user.getId();
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }

    @Override
    public List<User> findAll() {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();

            Query query = hibernateSession.createQuery("FROM User");
            List<User> users = query.list();
            return users;
        } catch (Exception e) {
            throw new DaoException(e);
        }
    }
}

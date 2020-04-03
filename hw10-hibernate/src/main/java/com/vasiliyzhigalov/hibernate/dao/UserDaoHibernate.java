package com.vasiliyzhigalov.hibernate.dao;


import com.vasiliyzhigalov.core.dao.DaoException;
import com.vasiliyzhigalov.core.dao.UserDao;
import com.vasiliyzhigalov.core.model.User;
import com.vasiliyzhigalov.core.sessionmanager.SessionManager;
import com.vasiliyzhigalov.hibernate.sessionmanager.DatabaseSessionHibernate;
import com.vasiliyzhigalov.hibernate.sessionmanager.SessionManagerHibernate;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoHibernate implements UserDao {
  private static Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

  private final SessionManagerHibernate sessionManager;

  public UserDaoHibernate(SessionManagerHibernate sessionManager) {
    this.sessionManager = sessionManager;
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
      logger.error(e.getMessage(), e);
      throw new DaoException(e);
    }
  }

  @Override
  public Optional<User> findById(long id){
    DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
    try {
      return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return Optional.empty();
  }

  @Override
  public SessionManager getSessionManager() {
    return sessionManager;
  }
}

package controllers;

import java.sql.SQLException;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

  @Inject
  private SqlSessionFactory sessionFactory;

  public Result index() throws SQLException {
    SqlSession session = sessionFactory.openSession();
    try {
      return ok(index.render("Your db catalog: " + session.getConnection().getCatalog()));
    } finally {
      session.close();
    }
  }

}
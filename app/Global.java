import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;

import play.Application;
import play.GlobalSettings;
import play.db.DB;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.name.Names;

public class Global extends GlobalSettings {

  private Injector injector;

  @Override
  public void onStart(Application app) {
    injector = Guice.createInjector(new MyBatisModule() {
      @Override
      protected void initialize() {
        bindDataSourceProvider(new Provider<DataSource>() {
          @Override
          public DataSource get() {
            // use db as configured in conf/application.conf
            return DB.getDataSource();
          }
        });
        bindTransactionFactoryType(JdbcTransactionFactory.class);

        final Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "default");
        Names.bindProperties(binder(), myBatisProperties);
      }
    });
  }

  @Override
  public <A> A getControllerInstance(Class<A> clazz) throws Exception {
    return injector.getInstance(clazz);
  }

}

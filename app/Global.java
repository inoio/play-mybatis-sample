import java.util.Properties;

import org.mybatis.guice.XMLMyBatisModule;

import play.Application;
import play.Configuration;
import play.GlobalSettings;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Global extends GlobalSettings {

  private Injector injector;

  @Override
  public void onStart(Application app) {
    final Properties props = new Properties();
    final Configuration config = app.configuration();
    props.setProperty("driver", config.getString("db.default.driver"));
    props.setProperty("url", config.getString("db.default.url"));
    props.setProperty("username", config.getString("db.default.user"));
    props.setProperty("password", config.getString("db.default.password"));
    injector = Guice.createInjector(new XMLMyBatisModule() {
      @Override
      protected void initialize() {
        setEnvironmentId("default");
        addProperties(props);
      }
    });
  }

  @Override
  public <A> A getControllerInstance(Class<A> clazz) throws Exception {
    return injector.getInstance(clazz);
  }

}

    package controllers;
    
    import javax.inject.Inject;
    
    import org.apache.ibatis.mapping.Environment;
    
    import play.mvc.Controller;
    import play.mvc.Result;
    import views.html.index;
    
    public class Application extends Controller {
      
      @Inject
      private Environment myBatisEnv;
    
      public Result index() {
        return ok(index.render("Your app is running with ds " + myBatisEnv.getDataSource()));
      }
    
    }
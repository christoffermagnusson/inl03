import java.beans.*;
import javax.swing.*;

public abstract class View extends JPanel implements PropertyChangeListener{
  protected Model model;

  public Model getModel(){
    return model;
  }
  public void setModel(Model m){
    if(model!=null){
      model.removePropertyChangeListener(this);
      model = m;
      if(model!=null){
        model.addPropertyChangeListener(this);
      }
    }

  }
  public void propertyChange(PropertyChangeEvent e){
    
  }
}

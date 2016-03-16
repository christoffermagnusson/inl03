import java.beans.*;
import java.io.*;

public interface Model extends Serializable{
void addPropertyChangeListener(PropertyChangeListener l);
void addPropertyChangeListener(String prop,PropertyChangeListener l);
void removePropertyChangeListener(PropertyChangeListener l);
void removePropertyChangeListener(String prop,PropertyChangeListener l);
}

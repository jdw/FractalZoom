
import java.applet.*;
import java.awt.Dimension;
import java.awt.GridLayout;

public class FractalZoom extends Applet {
	public void init() {
		setLayout(new GridLayout(1,2,2,2));
		add(new FractalPanel(new Dimension(getWidth(), getHeight())));
	}
	
	private static final long serialVersionUID = 187078011954356562L;
}
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;

public class FractalPanel extends Canvas implements Runnable, MouseListener, MouseMotionListener {
	FractalPanel(Dimension size) {
		this.normalSize = size;
		this.calculatedSize = new Dimension(2 * (int)size.getWidth(), 2 * (int)size.getHeight());
		fm = new FractalMaker(this.normalSize, this.calculatedSize);
		fm.reset();
		
		fz = new FractalZoomer(this.normalSize, this.calculatedSize);
		this.targetFPSDelta = 1000 / 30; // Thousand millisec/30 FPS
		this.pushed = false;
		
		new Thread(this).start();
		addMouseListener(this);
	    addMouseMotionListener(this);
	}
	
	public void init() {
	}
	
	public void run() {
		long prev = new Date().getTime();
		long now = 0L;
		
		while (true) {
			now = new Date().getTime();
			
			if (now - prev < this.targetFPSDelta) {
				try { Thread.sleep(now - prev); }
				catch(InterruptedException e) { e.printStackTrace(); }
			}
			else {
				update(this.getGraphics());
				paint(this.getGraphics());
				prev = now;
			}
		}
	}
	
	public void update(Graphics g) {
		if (fm.getDone() && !fm.getFetched()) {
			fz.addImg(fm.getImg());
			fz.setDone(false); // The zoomer have a img to work on
			fm.setFetched(true);
		}
		
		if (fz.getAllowedToZoom() && fm.getDone() && fz.getAmountOfCalculatedImg() < 3) // If we are running out on new img's to zoom in on
			fm.reset();
	}
	
	public void paint(Graphics g) {
		if (fm.getNbrOfCalcImgs() != 0) {
			g.drawImage(fz.getTargetImg(), 0, 0, null);
			g.setColor(Color.WHITE);
			g.fillRect(8, 8, 100, 30);
			g.setColor(Color.BLACK);
			g.drawString("Fractal: " + Integer.toString(fz.getImgCounter()) + "/" + Integer.toString(fm.getNbrOfCalcImgs()), 10, 20);
			g.drawString("sec: " + Double.toString(((double)fz.getDelta()) / 1000), 10, 33);
		}
	}

	public void mouseClicked(MouseEvent e) {
		
		this.pushed = true;
		fm.getSquare().setTarget(tmpX, tmpY);
		fz.setAllowedToZoom(true);
		e.consume();
		
	}

	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mouseDragged(MouseEvent e) { }
	public void mouseMoved(MouseEvent e) { }
	
	// Local variables
	private static final long serialVersionUID = -6991235636124112122L;
	private FractalMaker fm;
	private FractalZoomer fz;
	private Dimension normalSize;
	private Dimension calculatedSize;
	private int targetFPSDelta;
	private boolean pushed;
}

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Vector;

public class FractalZoomer implements Runnable {
	public FractalZoomer(Dimension normalSize, Dimension calculatedSize) {
		t = new Thread(this);
		t.start();
		this.normalSize = normalSize;
		this.calculatedSize = calculatedSize;
		this.alive = true;
		this.done = true;
		this.imgCounter = 0;
		this.targetImg = new BufferedImage((int)this.normalSize.getWidth(), (int)this.normalSize.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.img = new Vector<BufferedImage>();
		this.start = 0;
		this.imgCounter = 1;
		this.allowedToZoom = false;
	}
	
	public void run() {
		while(this.alive) {
			while(!this.done && !img.isEmpty()) {
				if (start == 0 || !this.allowedToZoom) {
					targetImg.getGraphics().drawImage(img.firstElement(),
							0, 0, targetImg.getWidth(), targetImg.getHeight(),
							0, 0, img.firstElement().getWidth(), img.firstElement().getHeight(),
							null);
					start = new Date().getTime();
				}
				else {
					//System.out.println("delta:" + (new Date().getTime()-delta));
					double delta = (new Date().getTime() - start);
					
					if (delta <= 10000) {
						int offsetX = (int)((this.calculatedSize.getWidth() / 4) * (delta / 10000));
						int offsetY = (int)((this.calculatedSize.getHeight() / 4) * (delta / 10000));
						
						targetImg.getGraphics().drawImage(img.firstElement(),
								0, 0, targetImg.getWidth(), targetImg.getHeight(),
								offsetX, offsetY, img.firstElement().getWidth() - offsetX, img.firstElement().getHeight() - offsetY,
								//0, 0, img.firstElement().getWidth(), img.firstElement().getHeight(),
								null);
					}
					else { // Reset and start with a new img
						this.img.remove(this.img.firstElement());
						this.start = 0;
						this.imgCounter++;
						
						
					}
				}
			}
			
			try { Thread.sleep(25); }
			catch(InterruptedException e) { e.printStackTrace(); }
		}				
	}
	
	// Getters and setters
	public long getDelta() { return (start == 0)? 0: (new Date().getTime() - start); }
	public boolean getAlive() { return alive; }
	public boolean getDone() { return done; }
	public void setAlive(boolean alive) { this.alive = alive; }
	public void setDone(boolean done) { this.done = done; }
	public void addImg(BufferedImage img) { this.img.add(img); }
	public BufferedImage getCurrentImg() { return img.firstElement(); }
	public BufferedImage getTargetImg() { return targetImg; }
	public void setNormalSize(Dimension size) { this.normalSize = size; }
	public Dimension getNormalSize() { return this.normalSize; }
	public void setCalculatedSize(Dimension size) { this.calculatedSize = size; }
	public Dimension getCalculatedSize() { return this.calculatedSize; }
	public int getAmountOfCalculatedImg() { return this.img.size(); }
	public int getImgCounter() { return imgCounter; }
	public void setAllowedToZoom(boolean allowed) { this.allowedToZoom = allowed; }
	public boolean getAllowedToZoom() { return allowedToZoom; }
	
	// Local variables
	private Thread t;
	private boolean alive;
	private boolean done;
	private Dimension normalSize;
	private Dimension calculatedSize;
	private Vector<BufferedImage> img; // Calculated fractals
	private BufferedImage targetImg; // Zoomed and ready to be displayed
	private int imgCounter;
	private long start;
	private boolean allowedToZoom;
}

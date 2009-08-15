import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class FractalMaker implements Runnable {
	public FractalMaker(Dimension normalSize, Dimension calculatedSize) {
		this.t = new Thread(this);
		this.t.start();
		this.alive = true;
		this.maxIter = 30;
		this.normalSize = normalSize;
		this.calculatedSize = calculatedSize;
		this.img = null;
		this.done = true;
		this.square = new FractalSquare(0.0, 0.0, 4.0, 4.0);
		this.nbrOfCalculatedImgs = 0;
	}
	
	public void run() {
		while (this.alive) {
			while (!this.done) {
				Complex tmp = new Complex(0.0, 0.0);
				
				for (int y = 0; y < this.calculatedSize.getHeight(); y++) {
					for (int x = 0; x < this.calculatedSize.getWidth(); x++) {
						
						tmp = this.getRelativePos(x, y, this.calculatedSize.getWidth(), this.calculatedSize.getHeight());
						int i = iteration(tmp);

						if (4 <= i && i <= this.maxIter - 1) {
							img.setRGB(x, y, new Color(100 + 5 * i, 100 + 5 * i, 0).getRGB());
						}
					}
				}
				
				this.done = true;
				this.square.half(); // Prepare for next rendering
				this.nbrOfCalculatedImgs++;
			}
			
			try { Thread.sleep(25); }
			catch(InterruptedException e) { e.printStackTrace(); }
		}
	}
	
	public int iteration(Complex C) {
		Complex Z = new Complex(0.0, 0.0);
		int ret = 0;
		
		for (; ret < this.maxIter; ret++) {
			Z = Z.multiply(Z);
			Z = Z.add(C);
	
			if (4.0 < Z.getReal() * Z.getReal() + Z.getImaginary() * Z.getImaginary())
				break;
		}
		
		return ret;
	}
		
	public Complex getRelativePos(double x, double y, double w, double h) {
		Complex ret = new Complex(0.0, 0.0);
		ret.setReal(this.square.getMinRe() + x * (this.square.getMaxRe() - this.square.getMinRe()) / (w));
		ret.setImaginary(this.square.getMaxIm() - y * (this.square.getMaxIm() - this.square.getMinIm()) / (h));
		
		return ret;
	}
	
	public void reset() {
		this.img = new BufferedImage((int)this.calculatedSize.getWidth(), (int)this.calculatedSize.getHeight(), BufferedImage.TYPE_INT_RGB);
		this.fetched = false;
		this.done = false;
	}
	
	// Getters and setters
	public boolean getAlive() { return this.alive; }
	public boolean getDone() { return this.done; }
	public boolean getFetched() { return this.fetched; }
	public void setFetched(boolean fetched) { this.fetched = fetched; }
	public void setAlive(boolean alive) { this.alive = alive; }
	public void setDone(boolean done) { this.done = done; }
	public BufferedImage getImg() { return img; }
	public void setNormalSize(Dimension size) { this.normalSize = size; }
	public Dimension getNormalSize() { return this.normalSize; }
	public void setCalculatedSize(Dimension size) { this.calculatedSize = size; }
	public Dimension getCalculatedSize() { return this.calculatedSize; }
	public int getNbrOfCalcImgs() { return nbrOfCalculatedImgs; }
	public FractalSquare getSquare() { return square; }
	
	// Local variables
	private Thread t;
	private boolean alive;
	private boolean done;
	private BufferedImage img; // The target GFX for the current calculated fractal
	private Dimension normalSize;
	private Dimension calculatedSize;
	private int maxIter;
	private boolean fetched;
	private FractalSquare square;
	private int nbrOfCalculatedImgs;
	
}

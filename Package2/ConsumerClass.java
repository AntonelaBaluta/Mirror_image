package Package2;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;

public class ConsumerClass extends ThreadClass {
	private BufferClass buffer;
	private PipedInputStream pis;

	public ConsumerClass(BufferClass buffer, PipedInputStream pis) { //am trimis numarul sectiunii nu sectunea
		super("Consumer"); //apeleaza constructor de la clasa mostenita
		this.buffer = buffer;
		this.pis = pis;
	}

	@Override
	public void executeTask() throws InterruptedException {
		for (int i = 0; i < 4; i++) {
			// sectionam imaginea in 4 sferturi
			BufferedImage imageSection = new BufferedImage(this.buffer.getWidth(), this.buffer.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			try {
				System.out.println("Citim in Consumer din Pipe sectiunea: " + this.pis.read());
				System.out.println("Scriem in WriterResult din Consumer sectiunea: " + i);
				imageSection = this.buffer.writeImage(i);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String fileName = "sectiune_";
			if (i == 0) {
				fileName += "sus_stanga.bmp";
			} else if (i == 1) {
				fileName += "sus_dreapta.bmp";
			} else if (i == 2) {
				fileName += "jos_stanga.bmp";
			} else if (i == 3) {
				fileName += "jos_dreapta.bmp";
			}

			File file = new File(fileName);
			try {
				ImageIO.write(imageSection, "bmp", file); // se scrie fiecare secventa
			} catch (IOException e) {
				e.printStackTrace();
			}
			Thread.sleep(1000);// ca in laborator
		}

		try {
			this.pis.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

package Package2;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class BufferClass {
	private boolean lock = false;
	String imagePath; // calea de acces la imagine
	private int height = 1, width = 1; // format imagine: inaltime, latime
	public BufferedImage sourceImage = null; // pentru citerea fiecarui sfert (1/4) de informatie

	public BufferClass(String path) throws IOException { // constructor
		this.imagePath = path;
	}

	/**
	 * Metoda pentru citirea informatiilor din imaginea sursa de catre thread-ul
	 * Producer
	 *
	 */
	public synchronized void readImage() {

		File file = new File(this.imagePath);
		try { // pregatesc imaginea pentru a citi un sfert din ea
			this.sourceImage = ImageIO.read(file);

//		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		    ImageIO.write(this.sourceImage, "bmp", bos );
//		    byte [] data = bos.toByteArray();
//		    System.out.println(data);

			this.height = this.sourceImage.getHeight();
			this.width = this.sourceImage.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// cum am facut la laborator
		while (!lock) {
			try {
				wait(); // Asteapta Producer sa puna o valoare
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock = false;
		notifyAll();
	}

	/**
	 * Metoda pentru prelucrarea informatiilor din imaginea sursa primite de la
	 * thread-ul Producer catre thread-ul Consumer
	 *
	 * @param section numarul sectiunii
	 */
	public synchronized BufferedImage writeImage(int section) throws IOException {
		// initializez un vector in care sa retin secventele (sferturile)
		BufferedImage images[] = new BufferedImage[4];

		// cum am facut la laborator
		while (lock) { // fiecare sectiune este scrisa (sunt 4 sectiuni)
			try {
				wait(); // asteapta Consumer sa preia valoarea
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock = true;
		notifyAll();

		BufferedImage sourceImage = this.sourceImage;

		// Creez 4 imagini separate pentru fiecare sfert
		BufferedImage topLeft = sourceImage.getSubimage(0, 0, width / 2, height / 2);
		BufferedImage topRight = sourceImage.getSubimage(width / 2, 0, width / 2, height / 2);
		BufferedImage bottomLeft = sourceImage.getSubimage(0, height / 2, width / 2, height / 2);
		BufferedImage bottomRight = sourceImage.getSubimage(width / 2, height / 2, width / 2, height / 2);

		images[0] = topLeft;
		images[1] = topRight;
		images[2] = bottomLeft;
		images[3] = bottomRight;

		return images[section];
	}

	/**
	 * Getter pentru inaltime
	 * 
	 * @return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Getter pentru latime
	 * 
	 * @return the width
	 */
	public int getWidth() {
		return this.width;
	}

}

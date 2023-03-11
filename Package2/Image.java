package Package2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class Image extends MirrorImage {
	private static BufferedImage originalImage = null, flippedImage = null;

	/**
	 * Metoda pentru citirea unei imagini
	 *
	 * @param imagePath calea imaginii
	 */
	public static void readImage(String imagePath) {
		// convertim calea (String-ul efectiv la abstract pathname, pentru a putea fi
		// citit de ImageIO.read)
		File file = new File(imagePath);
		try {
			originalImage = ImageIO.read(file);
			flippedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
					originalImage.getType());
			System.out.println("Reading complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda pentru scrierea unei imagini
	 *
	 * @param imagePath calea imaginii
	 * @param image     imaginea care va fi scrisa (salvata pe disk)
	 * @throws IOException
	 */
	public static void writeImage(String imagePath, BufferedImage image) throws IOException {
		File file = new File(imagePath);
		try {
			ImageIO.write(image, "bmp", file);
			System.out.println("Writing complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda pentru a lua numele imaginii fara extensie
	 *
	 * @param imagePath calea imaginii
	 */
	public static String getFileNameWithoutExt(String imagePath) {
		File file = new File(imagePath);
		return file.getName().replaceFirst("[.][^.]+$", "");
	}

	/**
	 * Getter pentru imaginea originala
	 * 
	 * @return the originalImage
	 */
	public static BufferedImage getOriginalImage() {
		return originalImage;
	}

	/**
	 * Getter pentru imaginea oglindita
	 * 
	 * @return the flippedImage
	 */
	public static BufferedImage getFlippedImage() {
		return flippedImage;
	}

	@Override
	public void flipPrintMessage(String message) {
		System.out.println(
				"Mostenire multipla: clasa Image mosteneste clasa Mirror Image care mosteneste interfata Flippable (2)");

	}
}

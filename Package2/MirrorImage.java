package Package2;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MirrorImage implements Flippable {

	/**
	 * Metoda pentru a oglindi o imagine orizontal
	 * 
	 * @param originalImage
	 * @param flippedImage
	 * @throws IOException
	 */
	public static void flipHorizontally(BufferedImage originalImage, BufferedImage flippedImage) throws IOException {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width / 2; x++) {
				int pixel = originalImage.getRGB(x, y);
				flippedImage.setRGB(x, y, originalImage.getRGB(width - x - 1, y));
				flippedImage.setRGB(width - x - 1, y, pixel);
			}
		}
	}

	/**
	 * Metoda pentru a oglindi o imagine vertical
	 * 
	 * @param originalImage
	 * @param flippedImage
	 * @throws IOException
	 */
	public static void flipVertically(BufferedImage originalImage, BufferedImage flippedImage) throws IOException {
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();

		for (int y = 0; y < height / 2; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = originalImage.getRGB(x, y);
				flippedImage.setRGB(x, y, originalImage.getRGB(x, height - y - 1));
				flippedImage.setRGB(x, height - y - 1, pixel);
			}
		}
	}

	@Override
	public void flipPrintMessage(String message) {
		System.out.println("Mostenire multipla: clasa MirrorImage mosteneste interfata Flippable (1)");

	}

}

package Package1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Package2.BufferClass;
import Package2.ConsumerClass;
import Package2.Image;
import Package2.ProducerClass;

public class Application {

	public static void main(String[] args) throws IOException {

		// Citim de la tastatura informatii despre calea imaginii si cum vrem sa facem
		// oglindirea imaginii
		long startTime = System.currentTimeMillis(); // START timp citire informatii imagine
		String imagePath = null;
		int direction = 0;

		// Daca nu avem argumente atunci citim din consola, altfel citim din linia de
		// comanda
		if (args.length == 0) {
			// Citim de la consola
			Scanner sc = new Scanner(System.in);
			imagePath = enterImagePath(sc); // calea imaginii pe care va fi aplicata procesarea
			direction = enterDirection(sc); // Directia oglindirii (orizontala, verticala sau ambele)
		} else {
			// Citim din linia de comanda cu argumente
			imagePath = args[0]; // primul argument este calea imaginii pe care aplicam oglindirea
			direction = Integer.parseInt(args[1]); // al doilea argument este directia oglindirii
		}

		System.out.println("Ai introdus aceasta cale pentru imaginea originala: " + imagePath);
		System.out.println("Ai introdus aceasta directie pentru oglindire: " + direction);

		long endTime = System.currentTimeMillis(); // STOP timp citire informatii imagine
		// afisam cat a durat executia
		System.out.println("Detaliile despre imagine au durat " + (endTime - startTime) + " milliseconds");

		// Memorez numele imaginii fara extensie, pentru a fi folosit la denumirea
		// imaginii oglindite
		String fileName = Image.getFileNameWithoutExt(imagePath);

		// Citesc imaginea
		startTime = System.currentTimeMillis(); // START timp citire imagine
		Image.readImage(imagePath);
		endTime = System.currentTimeMillis(); // STOP timp citire imagine
		System.out.println("Citirea imaginii a durat " + (endTime - startTime) + " milliseconds");

		// Multithreading
		startTime = System.currentTimeMillis(); // START timp Multithreading

		BufferClass buffer = new BufferClass(imagePath);
		buffer.sourceImage = new BufferedImage(Image.getOriginalImage().getWidth(),
				Image.getOriginalImage().getHeight(), BufferedImage.TYPE_INT_RGB);

		PipedInputStream pis = new PipedInputStream();
		PipedOutputStream pos = new PipedOutputStream();
		pos.connect(pis); // conectam PipedOutputStream la PipedInputStream

		// cum am facut la laborator
		// trimitem la Producer imaginea si PipedOutputStream
		ProducerClass producer = new ProducerClass(buffer, pos);
		producer.start();
		// trimitem la Consumer imaginea si PipedInputStream
		ConsumerClass consumer = new ConsumerClass(buffer, pis);
		consumer.start();

		// se asteapta ca threadurile sa se sfarseasca
		try { // sincronizare
			consumer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endTime = System.currentTimeMillis();
		System.out.println("Multithreading a durat " + (endTime - startTime) + " milliseconds");

		// se executa algoritmul pe intreaga imagine
		startTime = System.currentTimeMillis();

		if (direction == 0) {
			// oglindim imaginea orizontal
			Image.flipHorizontally(Image.getOriginalImage(), Image.getFlippedImage());
			// salvam imaginea
			Image.writeImage(fileName + "_horizontally.bmp", Image.getFlippedImage());
		} else if (direction == 1) {
			// oglindim imaginea vertical
			Image.flipVertically(Image.getOriginalImage(), Image.getFlippedImage());
			// salvam imaginea
			Image.writeImage(fileName + "_vertically.bmp", Image.getFlippedImage());
		} else if (direction == 2) {
			// oglindim imaginea orizontal
			Image.flipHorizontally(Image.getOriginalImage(), Image.getFlippedImage());
			// oglindim imaginea vertical (pe cea deja oglindita orizontal)
			Image.flipVertically(Image.getFlippedImage(), Image.getFlippedImage());
			// salvam imaginea
			Image.writeImage(fileName + "_horizontally_vertically.bmp", Image.getFlippedImage());
		}

		endTime = System.currentTimeMillis();
		System.out.println("Final + oglindirea imaginii " + (endTime - startTime) + " milliseconds");
	}

	/**
	 * Metoda pentru citirea caii unei imagine
	 *
	 * @param data Scanner pentru a prelua input-ul unui utilizator
	 */
	public static String enterImagePath(Scanner data) {
		System.out.println("Intoduceti calea imaginii originale:");
		return data.nextLine();
	}

	/**
	 * Metoda pentru citirea directiei de oglindire
	 *
	 * @param data Scanner pentru a prelua input-ul unui utilizator
	 */
	public static int enterDirection(Scanner data) {
		System.out.println(
				"Introduceti directia pentru oglindire (0 - pentru orizontal, 1 - pentru vertical, 2 - pentru ambele):");
		return data.nextInt();
	}
}

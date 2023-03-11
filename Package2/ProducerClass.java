package Package2;

import java.io.IOException;
import java.io.PipedOutputStream;

public class ProducerClass extends ThreadClass {
	private BufferClass buffer;
	private PipedOutputStream pos;

	// constructor
	public ProducerClass(BufferClass b, PipedOutputStream pos) throws IOException {
		super("Producer");
		this.buffer = b;
		this.pos = pos;
	}

	/**
	 * @return the buffer
	 */
	public BufferClass getBuffer() {
		return this.buffer;

	}

	@Override
	public void executeTask() {
		// citesc datele despre imagine si afisez cand am citit cate un sfert
		for (int i = 0; i < 4; i++) {
			System.out.println("Citim din Producer sectiunea: " + i);
			try {
				this.pos.write((byte) i);
				this.pos.flush();
				System.out.println("Scriem din Producer in Pipe sectiunea: " + i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.buffer.readImage();
		}
		try {
			this.pos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

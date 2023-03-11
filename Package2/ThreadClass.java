package Package2;

public abstract class ThreadClass extends Thread {
	private String name;

	public abstract void executeTask() throws InterruptedException; // pentru a declara excepții

	/**
	 * Constructor
	 * 
	 * @param threadName numele thread-ului
	 */
	public ThreadClass(String threadName) {
		super(threadName);
		this.name = threadName;
		System.out.println("Constructor thread = " + threadName);
	}
	
	/**
	 * Afisez cand thread-ul a inceput si cand a terminat de executat, asa cum am
	 * facut la laborator
	 *
	 */
	@Override
	public void run() {
		super.run();
		System.out.println(this.name + " thread a inceput.");
		try {
			this.executeTask();
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.name + " thread s-a terminat.");
	}
}

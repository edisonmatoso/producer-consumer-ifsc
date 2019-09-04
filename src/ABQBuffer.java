import java.util.concurrent.ArrayBlockingQueue;

public class ABQBuffer implements Buffer {
	private ArrayBlockingQueue<Integer> queue;
	
	public ABQBuffer(int tam) {
		queue = new ArrayBlockingQueue<>(tam);
	}
	

	// place value into buffer
	public void set(int value) throws InterruptedException {
		System.out.printf("Producer writes\t%2d", value);
		queue.put(value);
	} // end method set

	// return value from buffer
	public int get() throws InterruptedException {
		int valorRetirado = -1;
		valorRetirado = queue.take();
		System.out.printf("Consumer reads\t%2d", valorRetirado);
		return valorRetirado;
	} // end method get
}

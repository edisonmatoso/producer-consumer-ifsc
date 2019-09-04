
public class Semaforo {
	private int cont;
	
	public Semaforo(int count) {
		this.cont = count;
	}
	
	public synchronized void acquire() throws InterruptedException {
		while(this.cont <= 0) {
			wait();
		}
		cont--;
	}
	
	public synchronized void release() {
		this.cont++;
		notifyAll();
	}
}

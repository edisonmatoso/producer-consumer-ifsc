import java.util.ArrayDeque;

public class DoubleBuffer implements Buffer {
	
	private volatile ArrayDeque<Integer> readBuffer, writeBuffer;
	private int tamanho;
	
	public DoubleBuffer(int tam) {
		this.tamanho = tam;
		readBuffer = new ArrayDeque<>(tamanho);
		writeBuffer = new ArrayDeque<>(tamanho);
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("Producer writes\t%2d", value);
		this.writeBuffer.add(value);
		
		if (this.writeBuffer.size() == this.tamanho) {			
			while(!this.readBuffer.isEmpty());
			toggleBuffer();
		}
	}

	@Override
	public int get() throws InterruptedException {
		int valorRetirado = -1;
		
		while(this.readBuffer.isEmpty());
		
		valorRetirado = this.readBuffer.remove();
		System.out.printf("Consumer reads\t%2d", valorRetirado);
		
		return valorRetirado;
	}
	
	private void toggleBuffer() {
		ArrayDeque<Integer> aux = this.readBuffer;
		this.readBuffer = this.writeBuffer;
		this.writeBuffer = aux;
	}

}

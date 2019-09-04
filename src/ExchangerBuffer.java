import java.util.ArrayDeque;
import java.util.concurrent.Exchanger;

public class ExchangerBuffer implements Buffer {

	private ArrayDeque<Integer> readBuffer, writeBuffer;
	private int tamanho;
	private Exchanger<ArrayDeque<Integer>> exchanger;
	
	public ExchangerBuffer(int tam) {
		this.tamanho = tam;
		this.readBuffer = new ArrayDeque<>(tamanho);
		this.writeBuffer = new ArrayDeque<>(tamanho);
		
		this.exchanger = new Exchanger<>();		
	}

	@Override
	public void set(int value) throws InterruptedException {
		System.out.printf("Producer writes\t%2d", value);
		this.writeBuffer.add(value);
		
		if (this.writeBuffer.size() == this.tamanho)
			this.writeBuffer = exchanger.exchange(this.writeBuffer);
	}

	@Override
	public int get() throws InterruptedException {
		int valorRetirado = -1;
		
		if(this.readBuffer.isEmpty())
			this.readBuffer = exchanger.exchange(this.readBuffer);
		
		valorRetirado = this.readBuffer.remove();	
		System.out.printf("Consumer reads\t%2d", valorRetirado);
		
		return valorRetirado;
	}
}

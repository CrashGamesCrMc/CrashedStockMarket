package io.github.crashgamescrmc.StockMarket.market;

public class QueueObject<T> {

	public QueueObject(T value, QueueObject<T> next) {
		setValue(value);
		setNext(next);
	}

	private T value;
	private QueueObject<T> next;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public QueueObject<T> getNext() {
		return next;
	}

	public void setNext(QueueObject<T> next) {
		this.next = next;
	}
}

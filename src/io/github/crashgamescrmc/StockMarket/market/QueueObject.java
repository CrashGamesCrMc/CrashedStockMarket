package io.github.crashgamescrmc.StockMarket.market;

public class QueueObject<T> {

	public QueueObject(T value, QueueObject<T> next, QueueObject<T> prev) {
		setValue(value);
		setNext(next);
		setPrev(prev);
	}

	private T value;
	private QueueObject<T> next;
	private QueueObject<T> prev;

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

	public QueueObject<T> getPrev() {
		return prev;
	}

	public void setPrev(QueueObject<T> prev) {
		this.prev = prev;
	}
}

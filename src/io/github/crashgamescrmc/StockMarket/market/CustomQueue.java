package io.github.crashgamescrmc.StockMarket.market;

import java.util.Iterator;

import io.github.crashgamescrmc.StockMarket.exceptions.IndexOutOfQueueException;
import io.github.crashgamescrmc.StockMarket.exceptions.QueueEmptyException;

public class CustomQueue<T> implements Iterable<T> {

	private QueueObject<T> first;
	private QueueObject<T> last;
	private int size;

	public void enqueue(T value) {
		if (last == null) {
			last = new QueueObject<T>(value, null);
			first = last;
		} else {
			last.setNext(new QueueObject<T>(value, null));
		}
		size++;
	}

	public void dequeue() {
		if (first == null) {
			throw new QueueEmptyException();
		} else {
			first = first.getNext();
			if (first == null) {
				last = null;
			}
		}
		size--;
	}

	public T get(int position) {
		try {
			QueueObject<T> obj = getFirst();

			for (int i = 0; i < position; i++) {
				obj = obj.getNext();
			}
			return obj.getValue();

		} catch (NullPointerException e) {
			throw new IndexOutOfQueueException();
		}
	}

	public QueueObject<T> getFirst() {
		return first;
	}

	public void setFirst(QueueObject<T> first) {
		this.first = first;
	}

	public QueueObject<T> getLast() {
		return last;
	}

	public void setLast(QueueObject<T> last) {
		this.last = last;
	}

	@Override
	public Iterator<T> iterator() {
		return new CustomQueueIterator<>(first);
	}

	public double size() {
		return size;
	}

}

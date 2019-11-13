package io.github.crashgamescrmc.StockMarket.market;

import java.util.Iterator;

import io.github.crashgamescrmc.StockMarket.exceptions.IndexOutOfQueueException;
import io.github.crashgamescrmc.StockMarket.exceptions.QueueEmptyException;

/**
 * New values are inserted as the first element(s) for ease of use while
 * scanning through the last x prices.
 * 
 * @author crash
 *
 * @param <T>
 */
public class CustomQueue<T> implements Iterable<T> {

	private QueueObject<T> first;
	private QueueObject<T> last;
	private int size;

	public void enqueue(T value) {
		QueueObject<T> obj = new QueueObject<T>(value, first, null);
		if (first != null) {
			first.setPrev(obj);
		}
		first = obj;
		if (last == null) {
			last = first;
		}
		size++;
	}

	public void dequeue() {
		if (last == null) {
			throw new QueueEmptyException();
		}
		last = last.getPrev();
		if (last == null) {
			first = null;
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
		return new CustomQueueIterator<T>(first);
	}

	public int size() {
		return size;
	}

}

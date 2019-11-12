package io.github.crashgamescrmc.StockMarket.market;

import java.util.Iterator;

public class CustomQueueIterator<T> implements Iterator<T> {

	QueueObject<T> current;

	public CustomQueueIterator(QueueObject<T> first) {
		current = first;
	}

	@Override
	public boolean hasNext() {
		return current == null;
	}

	@Override
	public T next() {
		T out = current.getValue();
		current = current.getNext();
		return out;
	}

}

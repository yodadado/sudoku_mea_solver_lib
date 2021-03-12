package sk.study.mea.core;

import java.util.Comparator;
import java.util.Queue;

/**
 * Reimplementation {@link RandomAccessPriorityQueue} wit ability of random access by index without need of coping of array.
 *
 * @since 1.5
 * @author Josh Bloch, Doug Lea
 * @param <E> the type of elements held in this collection
 */
public class RandomAccessPriorityQueue<E>
{
	/**
	 * Priority queue represented as a balanced binary heap: the two
	 * children of queue[n] are queue[2*n+1] and queue[2*(n+1)].  The
	 * priority queue is ordered by the elements'
	 * natural ordering: For each node n in the
	 * heap and each descendant d of n, n <= d.  The element with the
	 * lowest value is in queue[0], assuming the queue is nonempty.
	 */
	private transient Object[] queue; // non-private to simplify nested class access

	/**
	 * The number of elements in the priority queue.
	 */
	private int size = 0;

	/**
	 * The comparator, or null if priority queue uses elements'
	 * natural ordering.
	 */
	private final Comparator<? super E> comparator;

	/**
	 * Creates a {@code RandomAccessPriorityQueue} with the specified initial
	 * capacity that orders its elements according to their
	 * {@linkplain Comparable natural ordering}.
	 *
	 * @param initialCapacity the initial capacity for this priority queue
	 * @throws IllegalArgumentException if {@code initialCapacity} is less
	 *         than 1
	 */
	public RandomAccessPriorityQueue (int initialCapacity)
	{
		this(initialCapacity, null);
	}

	/**
	 * Creates a {@code RandomAccessPriorityQueue} with the specified initial capacity
	 * that orders its elements according to the specified comparator.
	 *
	 * @param  initialCapacity the initial capacity for this priority queue
	 * @param  comparator the comparator that will be used to order this
	 *         priority queue.  If {@code null}, the {@linkplain Comparable
	 *         natural ordering} of the elements will be used.
	 * @throws IllegalArgumentException if {@code initialCapacity} is
	 *         less than 1
	 */
	public RandomAccessPriorityQueue (int initialCapacity, Comparator<? super E> comparator)
	{
		if (initialCapacity < 1)
		{
			throw new IllegalArgumentException();
		}
		this.queue = new Object[initialCapacity];
		this.comparator = comparator;
	}

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @return {@code true} (as specified by {@link Queue#offer})
	 * @throws ClassCastException if the specified element cannot be
	 *         compared with elements currently in this priority queue
	 *         according to the priority queue's ordering
	 * @throws NullPointerException if the specified element is null
	 */
	public boolean offer (E e)
	{
		if (e == null)
		{
			throw new NullPointerException();
		}
		int i = size;
		if (i >= queue.length)
		{
			throw new IllegalStateException("Queue full");
		}
		size = i + 1;
		if (i == 0)
		{
			queue[0] = e;
		}
		else
		{
			siftUp(i, e);
		}
		return true;
	}

	@SuppressWarnings ("unchecked")
	public E peek ()
	{
		return (size == 0) ? null : (E) queue[0];
	}

	public int size ()
	{
		return size;
	}

	/**
	 * Removes all of the elements from this priority queue.
	 * The queue will be empty after this call returns.
	 */
	public void clear ()
	{
		for (int i = 0; i < size; i++)
		{
			queue[i] = null;
		}
		size = 0;
	}

	@SuppressWarnings ("unchecked")
	public E poll ()
	{
		if (size == 0)
		{
			return null;
		}
		int s = --size;
		E result = (E) queue[0];
		E x = (E) queue[s];
		queue[s] = null;
		if (s != 0)
		{
			siftDown(0, x);
		}
		return result;
	}

	/**
	 * Inserts item x at position k, maintaining heap invariant by
	 * promoting x up the tree until it is greater than or equal to
	 * its parent, or is the root.
	 *
	 * To simplify and speed up coercions and comparisons. the
	 * Comparable and Comparator versions are separated into different
	 * methods that are otherwise identical. (Similarly for siftDown.)
	 *
	 * @param k the position to fill
	 * @param x the item to insert
	 */
	private void siftUp (int k, E x)
	{
		if (comparator != null)
		{
			siftUpUsingComparator(k, x);
		}
		else
		{
			siftUpComparable(k, x);
		}
	}

	@SuppressWarnings ("unchecked")
	private void siftUpComparable (int k, E x)
	{
		Comparable<? super E> key = (Comparable<? super E>) x;
		while (k > 0)
		{
			int parent = (k - 1) >>> 1;
			Object e = queue[parent];
			if (key.compareTo((E) e) >= 0)
			{
				break;
			}
			queue[k] = e;
			k = parent;
		}
		queue[k] = key;
	}

	@SuppressWarnings ("unchecked")
	private void siftUpUsingComparator (int k, E x)
	{
		while (k > 0)
		{
			int parent = (k - 1) >>> 1;
			Object e = queue[parent];
			if (comparator.compare(x, (E) e) >= 0)
			{
				break;
			}
			queue[k] = e;
			k = parent;
		}
		queue[k] = x;
	}

	/**
	 * Inserts item x at position k, maintaining heap invariant by
	 * demoting x down the tree repeatedly until it is less than or
	 * equal to its children or is a leaf.
	 *
	 * @param k the position to fill
	 * @param x the item to insert
	 */
	private void siftDown (int k, E x)
	{
		if (comparator != null)
		{
			siftDownUsingComparator(k, x);
		}
		else
		{
			siftDownComparable(k, x);
		}
	}

	@SuppressWarnings ("unchecked")
	private void siftDownComparable (int k, E x)
	{
		Comparable<? super E> key = (Comparable<? super E>) x;
		int half = size >>> 1; // loop while a non-leaf
		while (k < half)
		{
			int child = (k << 1) + 1; // assume left child is least
			Object c = queue[child];
			int right = child + 1;
			if (right < size && ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0)
			{
				c = queue[child = right];
			}
			if (key.compareTo((E) c) <= 0)
			{
				break;
			}
			queue[k] = c;
			k = child;
		}
		queue[k] = key;
	}

	@SuppressWarnings ("unchecked")
	private void siftDownUsingComparator (int k, E x)
	{
		int half = size >>> 1;
		while (k < half)
		{
			int child = (k << 1) + 1;
			Object c = queue[child];
			int right = child + 1;
			if (right < size && comparator.compare((E) c, (E) queue[right]) > 0)
			{
				c = queue[child = right];
			}
			if (comparator.compare(x, (E) c) <= 0)
			{
				break;
			}
			queue[k] = c;
			k = child;
		}
		queue[k] = x;
	}

	/**
	 * Returns the comparator used to order the elements in this
	 * queue, or {@code null} if this queue is sorted according to
	 * the {@linkplain Comparable natural ordering} of its elements.
	 *
	 * @return the comparator used to order this queue, or
	 *         {@code null} if this queue is sorted according to the
	 *         natural ordering of its elements
	 */
	public Comparator<? super E> comparator ()
	{
		return comparator;
	}

	public boolean isEmpty ()
	{
		return size() == 0;
	}

	@SuppressWarnings ("unchecked")
	public E get (int index)
	{
		if (index >= size)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}

		return (E) queue[index];
	}
}

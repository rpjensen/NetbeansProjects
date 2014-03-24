package List_Lab_08;

public interface ListLab08<T>
{
	public int size();
	public void clear();
	public boolean contains(T element);
	public void add(T element);
	public void insert(T element, int index);
	public void remove(int index);
	public T get(int index);
	public void set(int index, T element);
}
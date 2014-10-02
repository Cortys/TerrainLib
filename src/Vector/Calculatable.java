package Vector;

public interface Calculatable<T>
{
	public T add(T o);

	public T add(T o, boolean create);

	public T subtract(T o);

	public T subtract(T o, boolean create);

	public T multiply(T o);

	public T multiply(T o, boolean create);

	public T divide(T o);

	public T divide(T o, boolean create);

	public T invert();

	public T invert(boolean create);
}

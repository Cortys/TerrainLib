package Vector;

public abstract class Vector<E extends Number> implements Calculatable<Vector<E>>
{

	private E x, y;

	public Vector()
	{
		this.x = null;
		this.y = null;
	}

	public Vector(Vector<E> v)
	{
		this.x = v.getX();
		this.y = v.getY();
	}

	public Vector(E x, E y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector<E> setX(E x)
	{
		this.x = x;
		return this;
	}

	public Vector<E> setY(E y)
	{
		this.y = y;
		return this;
	}

	public Vector<E> set(E x, E y)
	{
		this.x = x;
		this.y = y;
		return this;
	}

	public E getX()
	{
		return x;
	}

	public E getY()
	{
		return y;
	}

	protected abstract E calculate(E v1, E v2, String operation);

	private Vector<E> operate(Vector<E> v, boolean create, String operation) throws InstantiationException, IllegalAccessException
	{

		@SuppressWarnings("unchecked")
		Vector<E> target = create ? this.getClass().newInstance() : this;

		if (v != null)
			target.set(calculate(this.getX(), v.getX(), operation), calculate(this.getY(), v.getY(), operation));
		else
			target.set(calculate(this.getX(), null, operation), calculate(this.getY(), null, operation));
		return target;
	}

	public Vector<E> add(Vector<E> v)
	{
		return add(v, false);
	}

	public Vector<E> subtract(Vector<E> v)
	{
		return subtract(v, false);
	}

	public Vector<E> multiply(Vector<E> v)
	{
		return multiply(v, false);
	}

	public Vector<E> divide(Vector<E> v)
	{
		return divide(v, false);
	}

	public Vector<E> invert()
	{
		return invert(false);
	}

	public Vector<E> add(Vector<E> v, boolean create)
	{
		try
		{
			return operate(v, create, "+");
		} catch (Exception e)
		{
			return null;
		}
	}

	public Vector<E> subtract(Vector<E> v, boolean create)
	{
		try
		{
			return operate(v, create, "-");
		} catch (Exception e)
		{
			return null;
		}
	}

	public Vector<E> multiply(Vector<E> v, boolean create)
	{
		try
		{
			return operate(v, create, "*");
		} catch (Exception e)
		{
			return null;
		}
	}

	public Vector<E> divide(Vector<E> v, boolean create)
	{
		try
		{
			return operate(v, create, "/");
		} catch (Exception e)
		{
			return null;
		}
	}

	public Vector<E> invert(boolean create)
	{
		try
		{
			return operate(null, create, "!");
		} catch (Exception e)
		{
			return null;
		}
	}

	public String toString()
	{
		return "[" + this.x + "|" + this.y + "]";
	}
}

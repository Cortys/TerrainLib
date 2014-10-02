package JavaRenderer;

public class Matrix
{

	private double[][] data;
	private int rows, columns;

	public Matrix(int rows, int columns)
	{
		if (rows < 1 || columns < 1)
			throw new IllegalArgumentException("At least one row and one column is required. Got " + rows + " rows, " + columns + " columns.");
		this.rows = rows;
		this.columns = columns;
		data = new double[rows][columns];
	}

	public Matrix(double[][] data)
	{
		this.rows = data.length;
		if (this.rows > 0)
			this.columns = data[0].length;
		if (this.rows < 1 || this.columns < 1)
			throw new IllegalArgumentException("At least one row and one column is required. Got " + rows + " rows, " + columns + " columns.");
		this.data = data;
	}

	public Matrix(Vector3D v)
	{
		this.rows = 3;
		this.columns = 1;
		data = new double[][] { { v.X }, { v.Y }, { v.Z } };
	}

	public Matrix(Matrix m)
	{
		this.rows = m.rows;
		this.columns = m.columns;
		data = m.data;
	}

	public double[] getRow(int row)
	{
		return data[row];
	}

	public double[] getColumn(int column)
	{
		double[] result = new double[rows];
		for (int i = 0; i < rows; i++)
			result[i] = data[i][column];
		return result;
	}

	public double get(int row, int column)
	{
		return data[row][column];
	}

	public Matrix set(int row, int column, double value)
	{
		data[row][column] = value;
		return this;
	}

	public Matrix set(double[][] data)
	{
		if (data.length != rows || data[0].length != columns)
			throw new IllegalArgumentException("Dimensions do not match.");
		this.data = data;
		return this;
	}

	public int rows()
	{
		return rows;
	}

	public boolean isSquare()
	{
		return rows == columns;
	}

	/**
	 * Transpose the matrix.
	 * 
	 * @param inverse
	 *            : true = mirror from bottom left to top right, false = mirror
	 *            from top left to bottom right
	 * @return The matrix.
	 * @throws TypeException
	 */
	public Matrix transpose(boolean inverse) throws TypeException
	{
		if (!isSquare())
			throw new TypeException("This matrix can not be transposed. Number of rows and columns have to be equal.");

		// if not inverse: iterate over upper right half of the matrix
		// if inverse: iterate over bottom left half of the matrix
		for (int i = 0; i < rows; i++)
			for (int e = (inverse ? 0 : i); e < (inverse ? columns - i : columns); e++)
			{

				// i: current row
				// e: current column

				// j: row to swap with
				// k: column to swap with
				int j, k;

				if (inverse)
				{ // inverse: swap rows and columns and invert them
					j = columns - e - 1;
					k = rows - i - 1;
				} else
				{ // not inverse: swap rows and columns
					j = e;
					k = i;
				}
				// Swap i,e with j,k:
				double tmp = data[i][e];
				data[i][e] = data[j][k];
				data[j][k] = tmp;
			}
		return this;
	}

	public Matrix transpose() throws TypeException
	{
		return transpose(false);
	}

	public Matrix multiply(Matrix m)
	{

		if (this.columns != m.rows)
			throw new IllegalArgumentException("Multiply: Matrix columns are not equal to rows");
		Matrix result = new Matrix(this.rows, m.columns);

		for (int r = 0; r < result.rows; r++)
		{
			for (int c = 0; c < result.columns; c++)
			{
				double summe = 0;
				for (int k = 0; k < this.columns; k++)
					// summe berechnen
					summe += this.get(r, k) * m.get(k, c);
				result.set(r, c, summe);
			}
		}
		return result;
	}

	public boolean inUpperTriangle()
	{
		if (!isSquare() || rows < 2)
			return false;
		for (int i = 1; i < rows; i++)
			for (int e = 0; e < i; i++)
				if (data[i][e] != 0)
					return false;
		return true;
	}

	public String toString()
	{
		String result = "";
		for (int i = 0; i < rows; i++)
		{
			result += "[ ";
			for (int e = 0; e < columns; e++)
				result += data[i][e] + " ";
			result += "]\n";
		}
		return result;
	}
}
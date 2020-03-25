class GraphUtils {
	/**
	 * Start of helper functions.
	 */
	static int vectorMax(int[] columns) {
		int max = 0;
		for (int i = 0; i < columns.length; i++) {
			if (columns[i] > max)
				max = columns[i];
		}

		return max;
	}

	static void checkVectorValidity(int[] v) {
		for(int i = 0; i < v.length; i++) {
			if(v[i] < 0) {
				throw new IllegalArgumentException("Vector cannot have negative values.");
			}
		}
	}
	
	/**
	 * Copies line i backwards into a vector and returns it.
	 */
	static Color[] reverseLine(ColorImage graph, int i) {
		Color[] buffer = new Color[graph.getWidth()];
		for (int j = 0; j < graph.getWidth(); j++) {
			buffer[j] = graph.getColor(graph.getWidth() - 1 - j, i);
		}
		return buffer;
	}

	/**
	 * Performs 90 degree rotation:
	 *  1 - transpose the matrix;
	 *  2 - reverse each line of the image horizontally.
	 */
	static ColorImage rotateImage(ColorImage toBeRotated) {
		ColorImage rotatedGraph = new ColorImage(toBeRotated.getHeight(), toBeRotated.getWidth());
		for (int i = 0; i < toBeRotated.getWidth(); i++) {
			for (int j = 0; j < toBeRotated.getHeight(); j++) {
				rotatedGraph.setColor(j, i, toBeRotated.getColor(i, j));
			}
		}

		ColorImage reversedGraph = new ColorImage(rotatedGraph.getWidth(), rotatedGraph.getHeight());
		for (int i = 0; i < reversedGraph.getHeight(); i++) {
			Color[] invertedLine = reverseLine(rotatedGraph, i);
			for (int j = 0; j < reversedGraph.getWidth(); j++) {
				reversedGraph.setColor(j, i, invertedLine[j]);
			}
		}

		return reversedGraph;
	}

	static void drawColumn(ColorImage graph, int value, int columnIndex, int columnWidth, int spacing, Color columnColor) {
		int x = spacing * (columnIndex + 1) + columnWidth * columnIndex;
		int y = graph.getHeight() - 1;
		graph.drawRect(x, y, columnWidth, value, columnColor);
	}
	
	static void drawBlurredColumn(ColorImage graph, int value, int columnIndex, int columnWidth, int spacing, int gradientIndex, Color columnColor) {
		if (gradientIndex > columnWidth / 2) {
			throw new IllegalArgumentException(
					"Invalid gradient index: " + gradientIndex + " when columnWidth is " + columnWidth);
		}
		
		Color targetColor = new Color(0, 0, 0);
		double percentagePerStep = (double)(100 / gradientIndex)/100;

		int x = spacing * (columnIndex + 1) + columnWidth * columnIndex;
		int y = graph.getHeight() - 1;
		for (int i = 1; i <= gradientIndex; i++) {
			double r = columnColor.getR() + (gradientIndex - i) * percentagePerStep * (targetColor.getR() - columnColor.getR());
			double g = columnColor.getG() + (gradientIndex - i) * percentagePerStep * (targetColor.getG() - columnColor.getG());
			double b = columnColor.getB() + (gradientIndex - i) * percentagePerStep * (targetColor.getB() - columnColor.getB());
			graph.drawRect(x + i, y, columnWidth - i * 2, value - i, new Color((int)r, (int)g, (int)b));
		}
	}

	static void drawCircle(ColorImage graph, int value, int valueIndex, int columnWidth, int spacing, int radius, Color circleColor) {
		int x = spacing * (valueIndex + 1) + radius*2 * valueIndex + radius*2 / 2;
		int y = graph.getHeight() - 1 - value;
		graph.drawCircle(x, y, radius, circleColor);
	}

	/**
	 * End of helper functions.
	 */
	static ColorImage columnGraph(int[] values, int columnWidth, int spacing, Color columnColor) {
		checkVectorValidity(values);
		ColorImage graph = new ColorImage(columnWidth * values.length + spacing * (values.length + 1), vectorMax(values));

		for (int i = 0; i < values.length; i++) {
			drawColumn(graph, values[i], i, columnWidth, spacing, columnColor);
		}

		return graph;
	}

	static ColorImage blurredColumnGraph(int[] values, int columnWidth, int spacing, int gradientIndex, Color columnColor) {
		checkVectorValidity(values);
		ColorImage graph = new ColorImage(columnWidth * values.length + spacing * (values.length + 1), vectorMax(values));

		for (int i = 0; i < values.length; i++) {
			drawBlurredColumn(graph, values[i], i, columnWidth, spacing, gradientIndex, columnColor);
		}

		return graph;
	}
	
	static ColorImage dispersionGraph(int[] values, int radius, int spacing, Color circleColor) {
		checkVectorValidity(values);
		ColorImage graph = new ColorImage(radius*2 * values.length + spacing * (values.length + 1), vectorMax(values));

		for (int i = 0; i < values.length; i++) {
			drawCircle(graph, values[i], i, radius*2, spacing, radius, circleColor);
		}

		return graph;
	}
	
	static void test() {
		//When dispersionGraph and columnGraph are given the same vector, the radius passed to dispersionGraph should be half of column width passed to columnGraph, so they can match when merged.
		ColorImage graph = GraphUtils.columnGraph(new int[]{20, 30, 10, 10, 25}, 10, 14, new Color(255, 255, 255));
		ColorImage graph2 = GraphUtils.dispersionGraph(new int[]{20, 30, 10, 10, 25}, 5, 14, new Color(255, 0, 0));
		ColorImage graph3 = GraphUtils.blurredColumnGraph(new int[]{20, 30, 10, 10, 25}, 10, 14, 4, new Color(0, 241, 255));
		ColorImage graph4 = GraphUtils.rotateImage(graph3);
		return;
	}
}
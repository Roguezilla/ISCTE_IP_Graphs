/**
 * Represents color images.
 * Image data is represented as a matrix:
 * - the number of lines corresponds to the image height (data.length)
 * - the length of the lines corresponds to the image width (data[*].length)
 * - pixel color is encoded as integers (ARGB)
 */
class ColorImage {

	private int[][] data; // @colorimage

	ColorImage(String file) {
		this.data = ImageUtil.readColorImage(file);
	}

	ColorImage(int width, int height) {
		data = new int[height][width];
	}

	int getWidth() {
		return data[0].length;
	}

	int getHeight() {
		return data.length;
	}

	void setColor(int x, int y, Color c) {
		data[y][x] = ImageUtil.encodeRgb(c.getR(), c.getG(), c.getB());
	}

	Color getColor(int x, int y) {
		int[] rgb = ImageUtil.decodeRgb(data[y][x]);
		return new Color(rgb[0], rgb[1], rgb[2]);
	}
	
	/**
	 * Start of ColorImage helper functions.
	 */
	boolean validatePosition(int x, int y) {
		return x >= 0 && x < this.getWidth() && y >= 0 && y < this.getHeight();
	}
	
	void drawRect(int x, int y, int w, int h, Color color) {
		for(int i = 0; i < w; i++) {
			for(int j = 0; j < h; j++) {
				this.setColor(x + i, y - j, color);
			}
		}
	}
	
	/**
	 * Draws a circumference r times using the trigonometry circle coordinates:
	 * x = r*cos(angle)
	 * y = r*sin(angle)
	 **/
	void drawCircle(int x, int y, int radius, Color color) {
		for(int a = 0; a <= 360; a++) {
			for(int r = 0; r <= radius; r++) {
				int cx = (int)(x + r*Math.cos(a));
				int cy = (int)(y + r*Math.sin(a));
				if(this.validatePosition(cx, cy))
					this.setColor(cx, cy, color);
			}
		}
	}
	/**
	 * End of ColorImage helper functions.
	 */
}
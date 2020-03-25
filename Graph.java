class Graph {
	private ColorImage graph;
	private String title = "";
	private String xtitle = "";
	private String ytitle = "";
	
	Graph(ColorImage graph) {
		this.graph = graph;
	}
	
	Graph(ColorImage graph, String title, String xtitle, String ytitle) {
		this.graph = graph;
		this.title = title;
		this.xtitle = xtitle;
		this.ytitle = ytitle;
	}
	
	String getTitle() {
		return this.title;
	}
	
	void setTitle(String title) {
		this.title = title;
	}
	
	void setXTitle(String xtitle) {
		this.xtitle = xtitle;
	}
	
	void setYTitle(String ytitle) {
		this.ytitle = ytitle;
	}
	
	void setEvenColummOddLinePixelsTransparent() {
		for(int x = 0; x < this.graph.getWidth(); x++) {
			if(x % 2 == 0) {
				for(int y = 0; y < this.graph.getHeight(); y++) {
					if(y % 2 != 0) {
						this.graph.setColor(x, y, new Color(0, 0, 0));
					}
						
				}
			}
		}
	}
	
	void setOddColumnEvenLinePixelsTransparent() {
		for(int x = 0; x < this.graph.getWidth(); x++) {
			if(x % 2 != 0) {
				for(int y = 0; y < this.graph.getHeight(); y++) {
					if(y % 2 == 0) {
						this.graph.setColor(x, y, new Color(0, 0, 0));
					}
						
				}
			}
		}
	}
	
	String getInfo() {
		return "Title: ".concat(this.title).concat("\nX Axis Title: ").concat(this.xtitle).concat("\nY Axis Title: ").concat(this.ytitle);
	}
	
	ColorImage getImage() {
		return this.graph;
	}
	
	static void test() {
		Graph g = new Graph(GraphUtils.columnGraph(new int[]{20, 30, 10, 10, 25}, 10, 10, new Color(255, 255, 255)), "Test", "X", "Y");
		Graph g2 = new Graph(GraphUtils.blurredColumnGraph(new int[]{20, 30, 10, 10, 25}, 20, 10, 4, new Color(0, 241, 255)));
		
		return;
	}
}
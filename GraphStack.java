class GraphStack {
	Graph g7 = new Graph(GraphUtils.dispersionGraph(new int[]{20, 30}, 4, 10, new Color(255, 255, 0)));
	private final int MAX_GRAPHS = 10;
	private Graph[] graphStack = new Graph[MAX_GRAPHS];
	private int next = 0;
	
	GraphStack(Graph graph) {
		graphStack[next] = graph;
		next++;
	}
	
	GraphStack(Graph[] graphs) {
		if(graphs.length > graphStack.length) {
			throw new IllegalArgumentException("Given array is bigger than the graph stack.");
		}
		for(int i = 0; i < graphs.length; i++) {
			graphStack[next] = graphs[i];
			next++;
		}
	}
	
	/**
	 * Helper functions
	 */
	int getNumberOfGraphsInStack() {
		return next;
	}
	
	int getBiggestWidth() {
		int max = 0;
		
		if(this.getNumberOfGraphsInStack() > 0) {
			for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
				if(this.graphStack[i].getImage().getWidth() > max)
					max = this.graphStack[i].getImage().getWidth();
			}
		}
		
		return max;
	}
	
	int getBiggestHeight() {
		int max = 0;
		if(this.getNumberOfGraphsInStack() > 0)  {
			for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
				if(this.graphStack[i].getImage().getHeight() > max)
					max = this.graphStack[i].getImage().getHeight();
			}
		}
		
		return max;
	}
	
	/**
	 * End of helper functions
	 */
	
	
	/**
	 * Adds a graph to the top of the stack.
	 **/
	void addGraphToTop(Graph graph) {
		this.graphStack[this.next] = graph;
		this.next++;
	}
	
	/**
	 * Removes a graph from the top of the stack.
	 **/
	void removeGraphFromTop() {
		if(this.next > 0) {
			next--;
		}
		this.graphStack[this.next] = null;
	}
	
	/**
	 * Returns topmost graph without affecting the stack.
	 **/
	Graph getTopmostGraph() {
		return graphStack[next == 0 ? next : next - 1];
	}
	
	/**
	 * Inserts a graph into position idx.
	 **/
	void insertGraph(int idx, Graph graph) {
		if (idx >= this.getNumberOfGraphsInStack()-1) {
			throw new IllegalArgumentException("Can't insert into top position of the stack or above.");
		}
		if (this.getNumberOfGraphsInStack() + 1 > this.MAX_GRAPHS) {
			throw new IllegalArgumentException("Graph stack is full.");
		}
		//Copy the stack until the given index into a new stack.
		Graph[] bufferGraphStack = new Graph[MAX_GRAPHS];
		for(int i = 0; i < idx; i++) {
			bufferGraphStack[i] = this.graphStack[i];
		}
		
		//Add the given graph at the top of the stack which is located at the given index.
		bufferGraphStack[idx] = graph;
		
		//Copy the rest of the stack into the new stack.
		for(int i = idx; i < this.graphStack.length-1; i++) {
			bufferGraphStack[i+1] = this.graphStack[i];
		}
		
		//Set old graph stack to new graph stack.
		this.graphStack = bufferGraphStack;
		
		//As we add 1 graph to the stack, we have to increment used to control the stack top position.
		next++;
	}
	
	/**
	 * Swaps graphs i and j;
	 **/
	void swapGraphs(int i, int j) {
		if (i > this.getNumberOfGraphsInStack() || j > this.getNumberOfGraphsInStack()) {
			throw new IllegalArgumentException( "Swap indexes are bigger than the amount of graphs in the stack.");
		}
		Graph x = this.graphStack[i];
		Graph y = this.graphStack[j];
		
		this.graphStack[i] = y;
		this.graphStack[j] = x;
	}
	
	/**
	 * Returns a vector with graphs without a set name.
	 **/
	Graph[] getUnnamedGraphs() {
		
		
		int unnamedCount = 0;
		for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
			if(this.graphStack[i].getTitle() == "")
				unnamedCount++;
		}
		
		Graph[] graphs = new Graph[unnamedCount];
		
		int counter = 0;
		for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
			if(this.graphStack[i].getTitle() == "") {
				graphs[counter] = this.graphStack[i];
				counter++;
			}
		}
		
		return graphs;
	}
	
	/**
	 * This is a workaround function for not being able to use the toLowerCase and stripAccents.
	 **/
	static char[] toLowerAndStripAccents(String str) {
		//char[] charStack = new char[str.length()];
		//A string with all the possible accents in portuguese and its stripped counterpart, also contains Ã§;
		String charsWithAccents =  "áàâãéêíóôõúç";
		String strippedAccents = "aaaaeeiooouc";
		
		char[] charStack = str.toLowerCase().toCharArray();
		for(int i = 0; i < charStack.length; i++) {
			//charStack[i] = str.toLowerCase().charAt(i);
			
			for(int j = 0; j < charsWithAccents.length(); j++) {
				//If a character has an accent, its replaced with its stripped counterpart.
				if(charStack[i] == charsWithAccents.charAt(j)) {
					charStack[i] = strippedAccents.charAt(j);
				}
			}
		}
		
		return charStack;
	}
	
	/**
	 * Takes 2 strings and returns the one that is first alphabetically.
	 **/
	static String retStr(String first, String second) {
		String temp = first.length() > second.length() ? second : first;
		
		for(int i = 0; i < first.length() && i < second.length(); i++) {
			if(toLowerAndStripAccents(second)[i] > toLowerAndStripAccents(first)[i])
				return first;
			else if (toLowerAndStripAccents(second)[i] < toLowerAndStripAccents(first)[i]){
				return second;
			}
		}
		
		return temp;
	}
	
	/**
	 * Checks if our graph stack is sorted, used in the sortAlphabetically function.
	 **/
	boolean isSorted(Graph[] graphs) {
		for(int i = 0; i < this.getNumberOfGraphsInStack() - 1; i++) {
			String ret = retStr(graphs[i].getTitle(), graphs[i+1].getTitle());
			if(ret != graphs[i].getTitle()) {
				return false;
			}
		}
		return true;
	}
	
	void swapGraphs(Graph[] graphs, int i, int j) {
		Graph x = graphs[i];
		Graph y = graphs[j];
		
		graphs[i] = y;
		graphs[j] = x;
	}
	
	Graph[] sortAlphabetically() {
		Graph[] sortedGraphs = new Graph[this.getNumberOfGraphsInStack()];
		for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
			sortedGraphs[i] = this.graphStack[i];
		}
		
		while(!isSorted(sortedGraphs)) {
			for(int i = 0; i < this.getNumberOfGraphsInStack() - 1; i++) {
				String firstAlphabetically = retStr(sortedGraphs[i].getTitle(), sortedGraphs[i+1].getTitle());
				if(firstAlphabetically != sortedGraphs[i].getTitle()) {
					this.swapGraphs(sortedGraphs, i, i+1);
				}
			}
		}
		
		//After sorting out unnamed graphs are at the beginning of the stack, so we have to move them to the top.
		for(int x = 0; x < this.getUnnamedGraphs().length; x++) {
			for(int i = 0; i < this.getNumberOfGraphsInStack() - 1; i++) {
				if(sortedGraphs[i].getTitle() == "") {
					this.swapGraphs(sortedGraphs, i, i+1);
				}
			}
		}
		
		return sortedGraphs;
	}
	
	/**
	 * Merges the graph stack, the graph in i+1 overlays the graph at i.
	 **/
	ColorImage merge() {
		ColorImage mergedGraphs = new ColorImage(this.getBiggestWidth(), this.getBiggestHeight());
		
		for(int i = 0; i < this.getNumberOfGraphsInStack(); i++) {
			for(int x = 0; x < this.graphStack[i].getImage().getWidth(); x++) {
				for(int y = 0; y < this.graphStack[i].getImage().getHeight(); y++) {
					if(!this.graphStack[i].getImage().getColor(x, y).isEqual(new Color(0, 0, 0)))
						mergedGraphs.setColor(x, y + (this.getBiggestHeight() - this.graphStack[i].getImage().getHeight()), this.graphStack[i].getImage().getColor(x, y));
				}
			}
		}
		
		return mergedGraphs;
	}
	
	/**
	 * Same behavior as above, but rotates the merged image by 90 degrees.
	 **/
	ColorImage mergeAndRotate() {
		return GraphUtils.rotateImage(this.merge());
	}
	
	//Debug function
	Graph[] retStack() {
		return this.graphStack;
	}
	
	static void test() {
		//Tests for all functions besides merge and mergeAndRotate.
		Graph g = new Graph(GraphUtils.columnGraph(new int[]{20, 30, 10, 10, 25}, 10, 10, new Color(255, 255, 255)), "Gráfico de colunas", "X", "Y");
		Graph g2 = new Graph(GraphUtils.dispersionGraph(new int[]{20, 30, 10, 10, 25}, 5, 10, new Color(255, 0, 0)), "grafico de Dispersao", "X", "Y");
		Graph g3 = new Graph(GraphUtils.dispersionGraph(new int[]{20, 30, 25, 30}, 4, 10, new Color(255, 255, 0)), "gráfico de dispersão 2", "X", "Y");
		Graph g4 = new Graph(GraphUtils.columnGraph(new int[]{10, 25}, 10, 10, new Color(255, 255, 255)), "Column Graph 2", "X", "Y");
		Graph g5 = new Graph(GraphUtils.dispersionGraph(new int[]{20, 30}, 4, 10, new Color(255, 255, 0)));
		Graph g6 = new Graph(GraphUtils.dispersionGraph(new int[]{20, 25, 30}, 4, 10, new Color(255, 255, 0)));
		
		GraphStack stack = new GraphStack(new Graph[]{g, g6, g2, g5, g3, g4});
		
		
		//System.out.println("\nstack.getUnnamedGraphs()");
		Graph[] unnamed = stack.getUnnamedGraphs();
		for(int i = 0; i < unnamed.length; i++) {
			//System.out.println(unnamed[i].getInfo());
		}
		
		//System.out.println("\nstack.sortAlphabetically()");
		Graph[] sorted = stack.sortAlphabetically();
		for(int i = 0; i < sorted.length; i++) {
			//System.out.println(sorted[i].getTitle() == "" ? "Unnamed" : sorted[i].getTitle());
		}
		
		System.out.println("\nStack altering function test, no changes");
		for(int i = 0; i < stack.getNumberOfGraphsInStack(); i++) {
			System.out.println(stack.retStack()[i].getTitle() == "" ? i + " Unnamed" : i + " " + stack.retStack()[i].getTitle());
		}
		System.out.println("\ninsertGraph(4, g)");
		stack.insertGraph(4, g);
		for(int i = 0; i < stack.getNumberOfGraphsInStack(); i++) {
			System.out.println(stack.retStack()[i].getTitle() == "" ? i + " Unnamed" : i + " " + stack.retStack()[i].getTitle());
		}
		
		//Tests for merge and mergeAndRotate.
		//GraphStack stack2 = new GraphStack(new Graph[]{g, g2});
		//ColorImage merged = stack2.merge();
		//ColorImage merged90 = stack2.mergeAndRotate();
		
		return;
	}
}
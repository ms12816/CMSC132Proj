package implementation;

import java.util.*;

/**
 * Implements a graph. We use two maps: one map for adjacency properties 
 * (adjancencyMap) and one map (dataMap) to keep track of the data associated 
 * with a vertex. 
 * 
 * @author cmsc132
 * 
 * @param <E>
 */
public class Graph<E> {
	/* You must use the following maps in your implementation */
	private HashMap<String, HashMap<String, Integer>> adjacencyMap;
	private HashMap<String, E> dataMap;

	public Graph() {
		adjacencyMap = new HashMap<String, HashMap<String, Integer>>();
		dataMap = new HashMap<String, E>();

	}
	public void addVertex(String vertexName, E data) {
		this.adjacencyMap.put(vertexName, new HashMap<String, Integer>());
		this.dataMap.put(vertexName, data);
	}
	public void addDirectedEdge(String startVertexName, String endVertexName, int cost) {
		this.adjacencyMap.get(startVertexName).put(endVertexName, cost);
	}
	public String toString() {
		String ans = "";
		ArrayList<String> vertices = new ArrayList<>(adjacencyMap.keySet());

		for(int i = 0; i < vertices.size(); i++) {
			for(int j = i; j < vertices.size(); j++) {
				if(vertices.get(i).compareTo(vertices.get(j)) > 0){
					String temp = vertices.get(i);
					vertices.set(i, vertices.get(j));
					vertices.set(j, temp);

				}
			}
		}
		//Now sorted vertices array begin iterating
		ans += "Vertices: [";
		for(int i = 0; i < vertices.size() - 1; i++) {
			ans += vertices.get(i) + ", ";
		}
		ans += vertices.get(vertices.size() - 1) + "]";

		ans += "\n" + "Edges: " + "\n";

		for(String s : vertices) {
			ans += "Vertex(" + s + ")--->";

			//Each vertex's adjacent 
			Map<String, Integer> adjacents = this.adjacencyMap.get(s);
			ArrayList<String> keyList = new ArrayList<>(adjacents.keySet());
			//Sort each vertex list of adjacents by key
			for(int i = 0; i < keyList.size(); i++) {
				for(int j = i; j < keyList.size(); j++) {
					if(keyList.get(i).compareTo(keyList.get(j)) > 0) {
						String temp = keyList.get(i);
						keyList.set(i, keyList.get(j));
						keyList.set(j, temp);						
					}
				}
			}			
			ans += "{";
			for(int i = 0; i < keyList.size(); i++) {
				ans += keyList.get(i);
				ans += "=";
				ans += adjacents.get(keyList.get(i));
				if (i < keyList.size() - 1) ans += ", ";
			}
			ans += "}" + "\n";

		}
		return ans;

	}
	public Map<String, Integer> getAdjacentVertices(String vertexName){
		return this.adjacencyMap.get(vertexName);
	}
	public int getCost(String startVertex, String endVertex) {
		int cost = 0;
		cost += this.adjacencyMap.get(startVertex).get(endVertex);
		return cost;
	}
	public Set<String> getVertices(){
		Set<String> vertices = adjacencyMap.keySet();
		return vertices;
	}
	public E getData(String vertex) {
		if(!dataMap.containsKey(vertex)) {
			throw new IllegalArgumentException("Vertex Not Found");
		}
		return dataMap.get(vertex);
	}
	public void doDepthFirstSearch(String startVertexName, CallBack<E> callback) {
		if(!dataMap.containsKey(startVertexName) || !adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex Not Found");
		}

		Deque<String> stack = new ArrayDeque<String>();
		Set<String> visited = new HashSet<>();

		//Start the search 
		stack.push(startVertexName);
		visited.add(startVertexName);
		//Operate until stack is empty denoting that all nodes have been processed
		while(!stack.isEmpty()) {
			String curr = stack.pop();
			callback.processVertex(curr, this.dataMap.get(curr));

			for(String s : this.adjacencyMap.get(curr).keySet()) {
				if(!visited.contains(s)) {
					stack.push(s);
					visited.add(s);
				}
			}
		}
	}
	public void doBreadthFirstSearch(String startVertexName, CallBack<E> callback) {
		if(!dataMap.containsKey(startVertexName) || !adjacencyMap.containsKey(startVertexName)) {
			throw new IllegalArgumentException("Vertex Not Found");
		}

		Deque<String> queue = new ArrayDeque<String>();
		Set<String> visited = new HashSet<>();

		//Start the search 
		queue.push(startVertexName);
		visited.add(startVertexName);
		//Operate until stack is empty denoting that all nodes have been processed
		while(!queue.isEmpty()) {
			String curr = queue.remove();
			callback.processVertex(curr, this.dataMap.get(curr));

			for(String s : this.adjacencyMap.get(curr).keySet()) {
				if(!visited.contains(s)) {
					queue.add(s);
					visited.add(s);
				}
			}
		}
	}

	public int doDijkstras(String startVertexName, String endVertexName, ArrayList<String> shortestPath) {
		if(!adjacencyMap.containsKey(startVertexName) || !adjacencyMap.containsKey(endVertexName)) {
			shortestPath.add("None");
			return -1;
		}

		Map<String, Integer> distances = new HashMap<>();
		//Store path to reconstruct and place in array
		Map<String, String> previous = new HashMap<>();
		Set<String> visited = new HashSet<>();

		for(String vertex : adjacencyMap.keySet()) {
			distances.put(vertex, Integer.MAX_VALUE);
		}
		distances.put(startVertexName, 0);
		//Outer loop to manage combinations of moves
		while(visited.size() < adjacencyMap.size()) {
			String curr = null;
			int minDistance = Integer.MAX_VALUE;

			for(String vertex : adjacencyMap.keySet()) {
				if(!visited.contains(vertex) && distances.get(vertex) < minDistance) {
					minDistance = distances.get(vertex);
					curr = vertex;
				}
			}
			//No more reachable nodes
			if(curr == null) break;

			visited.add(curr);
			//Check distances to find most optimal path
			for(Map.Entry<String, Integer> adjEntry : adjacencyMap.get(curr).entrySet()) {
				String neighbor = adjEntry.getKey();
				int cost = adjEntry.getValue();
				if(!visited.contains(neighbor)) {
					int newDist = distances.get(curr) + cost;
					if(newDist < distances.get(neighbor)) {
						distances.put(neighbor, newDist);
						previous.put(neighbor, curr);
					}
				}
			}
		}
		//If none found
		if (distances.get(endVertexName) == Integer.MAX_VALUE) {
			shortestPath.clear();
			shortestPath.add("None");
			return -1;
		}
		
		//Retrace path 
		shortestPath.clear();
		String temp = endVertexName;
		while(temp != null) {
			shortestPath.add(0, temp);
			temp = previous.get(temp);
		}
		
		return distances.get(endVertexName);

	}
}
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Breadth-First Search (BFS)
 * 
 * You should fill the search() method of this class.
 */
public class BreadthFirstSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public BreadthFirstSearcher(Maze maze) {
		super(maze);
	}

	/**
	 * Main breadth first search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		// FILL THIS METHOD
		
		// explored list is a 2D Boolean array that indicates if a state associated with a given position in the maze has already been explored.
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		
		// ...

		// Queue implementing the Frontier list
		LinkedList<State> queue = new LinkedList<State>();
		
		//load queue with all level one states
		State player = new State(maze.getPlayerSquare(), null, 0, 0);
		explored[player.getX()][player.getY()] = true;
		
		queue.addAll(player.getSuccessors(explored, maze));
		maxSizeOfFrontier = queue.size();
		noOfNodesExpanded = queue.size();
		
		while (!queue.isEmpty()) {
			//
			State state = queue.pop();
			if(state.getDepth() > maxDepthSearched){
				maxDepthSearched = state.getDepth();
			}
			if(state.isGoal(maze)){
				cost = state.getDepth();
				state = state.getParent();
				while(state.getParent() != null) {
					getModifiedMaze().setOneSquare(state.getSquare(), '.');
					state = state.getParent();
				}
				return true;
			}
			explored[state.getX()][state.getY()] = true;
			
			ArrayList<State> next = new ArrayList<State>();
			next.addAll(state.getSuccessors(explored, maze));
			int n = next.size();
			//System.out.print("size: " + next.size());
			for(int i = 0; i < next.size(); i ++){
				if(explored[next.get(i).getX()][next.get(i).getY()]){
					next.remove(i); 
				}
				Iterator<State> iter = queue.iterator();
				while(iter.hasNext()){
					State curr = iter.next();
					if(curr.getX() == next.get(i).getX() && curr.getY() == next.get(i).getY()){
						//explored[next.get(i).getX()][next.get(i).getY()] = true;
						next.remove(i); break;
					}
				}
				explored[next.get(i).getX()][next.get(i).getY()] = true;
			}
			//System.out.println(n == next.size());
			noOfNodesExpanded += next.size();
			queue.addAll(next);
			
			if(queue.size() > maxSizeOfFrontier){
				maxSizeOfFrontier = queue.size();
			}
			
		}
		return false;
	}
}

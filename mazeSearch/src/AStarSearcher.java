import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * A* algorithm search
 * 
 * You should fill the search() method of this class.
 */
public class AStarSearcher extends Searcher {

	/**
	 * Calls the parent class constructor.
	 * 
	 * @see Searcher
	 * @param maze initial maze.
	 */
	public AStarSearcher(Maze maze) {
		super(maze);
	}
	public double calcHValue(Square sqr) {		
		return Math.sqrt(Math.pow(sqr.X - maze.getGoalSquare().X, 2) + Math.pow(sqr.Y - maze.getGoalSquare().Y, 2));
	}
	/**
	 * Main a-star search algorithm.
	 * 
	 * @return true if the search finds a solution, false otherwise.
	 */
	public boolean search() {
		
		// FILL THIS METHOD

		// explored list is a Boolean array that indicates if a state associated with a given position in the maze has already been explored. 
		boolean[][] explored = new boolean[maze.getNoOfRows()][maze.getNoOfCols()];
		// ...

		PriorityQueue<StateFValuePair> frontier = new PriorityQueue<StateFValuePair>();
		
		State player = new State(maze.getPlayerSquare(), null, 0, 0);
		//fill the queue with level 1. ten begin:
		ArrayList<State> init = new ArrayList<State>();
		init.addAll(player.getSuccessors(explored, maze));
		explored[player.getX()][player.getY()] = true;
		noOfNodesExpanded = 1;
		for(int i = 0; i < init.size(); i++) {
			frontier.add(new StateFValuePair(init.get(i), init.get(i).getGValue() + calcHValue(init.get(i).getSquare()) ));
		}
		maxSizeOfFrontier = frontier.size();
		maxDepthSearched = 0;
		while (!frontier.isEmpty()) {
			StateFValuePair state = frontier.poll();
			if(state.getState().getDepth() > maxDepthSearched){
				maxDepthSearched = state.getState().getDepth();
			}
			if(frontier.size() > maxSizeOfFrontier){
				maxSizeOfFrontier = frontier.size();
			}
			noOfNodesExpanded++;
			
			if(state.getState().isGoal(maze)){
				cost = state.getState().getGValue();
				maxSizeOfFrontier++;
				State bstate = state.getState().getParent();
				while(bstate.getParent() != null) {
					
					getModifiedMaze().setOneSquare(bstate.getSquare(), '.');
					bstate = bstate.getParent();
				}
				return true;
			}
			explored[state.getState().getX()][state.getState().getY()] = true;
			
			ArrayList<State> newStates = new ArrayList<State>();
			newStates.addAll(state.getState().getSuccessors(explored, maze));
			
			for(int i = 0; i < newStates.size(); i++){	
				
				Iterator<StateFValuePair> iter = frontier.iterator();
				boolean add = true;
				while(iter.hasNext()){
					StateFValuePair curr = iter.next();
					
					if(curr.getState().getX() == newStates.get(i).getX() &&
							curr.getState().getY() == newStates.get(i).getY()){
						add = false;
						//compare states GValues
						if(curr.getState().getGValue() > newStates.get(i).getGValue()){
							//add newStates, remove curr
							frontier.remove(curr);
							frontier.add(new StateFValuePair(newStates.get(i), newStates.get(i).getGValue() + calcHValue(newStates.get(i).getSquare())));
						}
						break;
					}
				}
				if(add){
					frontier.add(new StateFValuePair(newStates.get(i), newStates.get(i).getGValue() + calcHValue(newStates.get(i).getSquare())));
				}
				
			}

		}
		return false;
	}
}

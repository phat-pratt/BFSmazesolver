import java.util.ArrayList;

/**
 * A state in the search represented by the (x,y) coordinates of the square and
 * the parent. In other words a (square,parent) pair where square is a Square,
 * parent is a State.
 * 
 * You should fill the getSuccessors(...) method of this class.
 * 
 */
public class State {

	private Square square;
	private State parent;

	// Maintain the gValue (the distance from start)
	// You may not need it for the BFS but you will
	// definitely need it for AStar
	private int gValue;

	// States are nodes in the search tree, therefore each has a depth.
	private int depth;

	/**
	 * @param square
	 *            current square
	 * @param parent
	 *            parent state
	 * @param gValue
	 *            total distance from start
	 */
	public State(Square square, State parent, int gValue, int depth) {
		this.square = square;
		this.parent = parent;
		this.gValue = gValue;
		this.depth = depth;
	}

	//calculate the "Manhattan" distance
	public int calcGValue(int x1, int y1, int x2, int y2){
		return Math.abs(x1-x2) + Math.abs(y1-y2);
	}
	/**
	 * @param visited
	 *            explored[i][j] is true if (i,j) is already explored
	 * @param maze
	 *            initial maze to get find the neighbors
	 * @return all the successors of the current state
	 */
	public ArrayList<State> getSuccessors(boolean[][] explored, Maze maze) {
		// FILL THIS METHOD
		ArrayList<State> list = new ArrayList<State>();
		
		Square left = new Square(square.X, square.Y-1);
		Square right = new Square(square.X, square.Y+1);
		Square up = new Square(square.X-1, square.Y);
		Square down = new Square(square.X+1, square.Y);
		//check left
		if(left.Y > 0 && !explored[left.X][left.Y] && maze.getSquareValue(left.X, left.Y) != '%'){
			list.add(new State(left, this, getDepth()+1, getDepth() + 1));
		}
		
		//check down
		if(down.X < maze.getNoOfRows() && !explored[down.X][down.Y] && maze.getSquareValue(down.X, down.Y) != '%'){
			list.add(new State(down, this, getDepth()+1, getDepth() + 1));
		}
		
		//check right
		if(right.Y < maze.getNoOfCols() && !explored[right.X][right.Y] && maze.getSquareValue(right.X, right.Y) != '%'){
			list.add(new State(right, this, getDepth()+1, getDepth() + 1));
		}
		
		//check up
		if(up.X > 0 && !explored[up.X][up.Y] && maze.getSquareValue(up.X, up.Y) != '%'){
			list.add(new State(up, this, getDepth()+1, getDepth() + 1));
		}
		
		
		return list;
		// TODO check all four neighbors in left, down, right, up order
		// TODO remember that each successor's depth and gValue are
		// +1 of this object.
	}

	/**
	 * @return x coordinate of the current state
	 */
	public int getX() {
		return square.X;
	}

	/**
	 * @return y coordinate of the current state
	 */
	public int getY() {
		return square.Y;
	}

	/**
	 * @param maze initial maze
	 * @return true is the current state is a goal state
	 */
	public boolean isGoal(Maze maze) {
		if (square.X == maze.getGoalSquare().X
				&& square.Y == maze.getGoalSquare().Y)
			return true;

		return false;
	}

	/**
	 * @return the current state's square representation
	 */
	public Square getSquare() {
		return square;
	}

	/**
	 * @return parent of the current state
	 */
	public State getParent() {
		return parent;
	}

	/**
	 * You may not need g() value in the BFS but you will need it in A-star
	 * search.
	 * 
	 * @return g() value of the current state
	 */
	public int getGValue() {
		return gValue;
	}

	/**
	 * @return depth of the state (node)
	 */
	public int getDepth() {
		return depth;
	}
}

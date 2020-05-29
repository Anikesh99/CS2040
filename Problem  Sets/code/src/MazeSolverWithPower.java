/*
Collaborated with Ambrose Liew
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
public class MazeSolverWithPower implements IMazeSolverWithPower {
	private Maze maze;
	private boolean solved;
	private int endRow, endCol;
	private pair combo;
	private PathPower[][] powerDistance;
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private int[][] ddir = new int[][]{
			{-1, 0}, // North
			{1, 0},  // South
			{0, 1},  // East
			{0, -1}  // West
	};
	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}
	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		powerDistance = new PathPower[maze.getRows()][maze.getColumns()];
		solved = false;
	}
	public void pathGenerator() {
		int length = combo.placeSeen.size();
		for (int i = 0; i < length; i++) {
			Location curr = (Location)combo.placeSeen.get(i);
			maze.getRoom(curr.row, curr.column).onPath = true;
		}
	}
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.powerDistance[i][j] = new PathPower(-1, -1);
				maze.getRoom(i, j).onPath = false;
			}
		}
		combo = new pair(new ArrayList<>(), 0);
		this.endRow = endRow;
		this.endCol = endCol;
		solved = false;
		powerDistance[startRow][startCol] = new PathPower(0, 0); // there are no powers here
		ArrayList lst = new ArrayList<>();
		lst.add(new Location(startRow, startCol));
		amazo(new pair(lst, 0));
		if (solved) {
			pathGenerator();
			return combo.placeSeen.size() - 1;
		}
		return null;
	}
	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.powerDistance[i][j] = new PathPower(-1, -1);
				maze.getRoom(i, j).onPath = false;
			}
		}
		solved = false;
		combo = new pair(new ArrayList<>(), 0);
		this.endRow = endRow;
		this.endCol = endCol;
		powerDistance[startRow][startCol] = new PathPower(0, superpowers);
		ArrayList list = new ArrayList<>();
		list.add(new Location(startRow, startCol));
		amazo(new pair(list, superpowers));
		if (solved) {
			pathGenerator();
			return combo.placeSeen.size() - 1;
		}
		return null;
	}
	private void amazo(pair journey) {
		int dist = journey.placeSeen.size() - 1; // this is to compare with the current distance with each node
		Location current = (Location)journey.placeSeen.get(dist);
		if (current.row == endRow && current.column == endCol &&
				(combo.placeSeen.size() - 1 > dist || combo.placeSeen.isEmpty())) {
			// update the shortest path to the end if it exists
			combo = journey;
			solved = true;
		}
		if (powerDistance[current.row][current.column].distance == -1 // this is if no path exists
				|| dist < powerDistance[current.row][current.column].distance ||
				(dist == powerDistance[current.row][current.column].distance &&
						journey.powerLevel > powerDistance[current.row][current.column].power)) {
			powerDistance[current.row][current.column] = new PathPower(dist, journey.powerLevel);
			// updating the node with a better distance/powerlevel
			// 1st check is if there is even a path yet, 2nd if the current distance is shorter than the shortest
			// distance and last check is if there is a path with equal distance but lesser powerlevel
		}
		List future = canGo(current.row, current.column, false, journey);
		// add those coordinates which can be reached without using any power
		if (journey.powerLevel > 0) {
			// add those coordinates which can be reached bu using powers
			future.addAll(canGo(current.row, current.column, true, journey));
		}
		for (int i = 0; i < future.size(); i++) {
			Location next = (Location)future.get(i);
			ArrayList duplicate = new ArrayList<>();
			duplicate.addAll(journey.placeSeen);
			duplicate.add(next);
			//basically adding in the next location on the path
			int powerLeft = journey.powerLevel;
			if (next.used == true) {
				// this means that a power was used
				powerLeft--;
			}
			amazo(new pair(duplicate, powerLeft));
			// recurse on each and every path generated
		}
	}
	private boolean canGo(int row, int col, int dir) {
		// not needed since our maze has a surrounding block of wall
		// but Joe the Average Coder is a defensive coder!
		if (row + ddir[dir][0] < 0 || row + ddir[dir][0] >= maze.getRows()) {
			return false;
		}
		if (col + ddir[dir][1] < 0 || col + ddir[dir][1] >= maze.getColumns()) {
			return false;
		}
		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}
		return false;
	}
	private boolean canVisit(int row, int col, int dir) {
		return row + ddir[dir][0] >= 0 && row + ddir[dir][0] < maze.getRows() &&
				col + ddir[dir][1] >= 0 && col + ddir[dir][1] < maze.getColumns();
	}
	private ArrayList canGo(int row, int col, boolean used, pair currentPath) {
		//overloaded canGo
		ArrayList list = new ArrayList<>();
		for (int i = 0; i < 4; i++){
			Location curr = new Location(row + ddir[i][0], col + ddir[i][1]);
			if (!used){
				// when the power is not used
				if (canGo(row, col, i) && !currentPath.placeSeen.contains(curr)){
					list.add(new Location(row + ddir[i][0], col + ddir[i][1], false));
				}
			} else {
				if (!canGo(row, col, i) && canVisit(row, col, i) && !currentPath.placeSeen.contains(curr)) {
					list.add(new Location(row + ddir[i][0], col + ddir[i][1], true));
				}
			}
		}
		return list;
	}
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		int counter = 0;
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				if (powerDistance[i][j].distance == k) {
					counter++;
				}
			}
		}
		return counter;
	}
	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower amazor = new MazeSolverWithPower();
			amazor.initialize(maze);
			System.out.println(amazor.pathSearch(4, 1, 4, 3, 1));
			MazePrinter.printMaze(maze);
			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + amazor.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class PathPower {
	// basically a pair for the shortest diastance and power needed to reach a location
	int distance;
	int power;
	PathPower(int shortest, int power) {
		this.distance = shortest;
		this.power = power;
	}
}
class Location implements Comparable {
	// check if the row, column and whether a power was used to reach this place
	int row;
	int column;
	boolean used = false;
	Location(int row, int column) {
		this.row = row;
		this.column = column;
	}
	Location(int row, int column, boolean used){
		this.row = row;
		this.column = column;
		this.used = used;
	}
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof Location) {
			Location other = (Location) obj;
			return this.row == other.row && this.column == other.column;
		} else {
			return false;
		}
	}
	public int compareTo(Object o) {
		return 0;
	}
}
class pair {
	// storing the path and the amount of power left to traverse
	int powerLevel;
	ArrayList placeSeen;
	pair(ArrayList placeSeen, int powerLevel) {
		this.placeSeen = placeSeen;
		this.powerLevel = powerLevel;
	}
}
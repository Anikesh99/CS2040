import java.util.*;

public class MazeSolver implements IMazeSolver {
	private Maze maze;
	private boolean solved = false;
	private int[][] visited;
	private int endRow, endCol;
	private int startRow, startCol;
	private int move_count;
	ArrayList<Coord> q = new ArrayList<>();

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private int[][] ddir = new int[][]{
			{-1, 0}, // North
			{1, 0},  // South
			{0, 1},  // East
			{0, -1}  // West
	};

	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new int[maze.getRows()][maze.getColumns()];
		solved = false;
	}

	@Override
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
				this.visited[i][j] = -1;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		this.startRow = startRow;
		this.startCol = startCol;
		solved = false;
		return amazo(startRow, startCol, 0);
	}

	private Integer amazo(int row, int col, int rooms) {
		if (q.isEmpty()) {
			explore(row, col, rooms);
		}
		int size = q.size();
		for (int i = 0; i < size; i++) {
			if (visited[q.get(0).x][q.get(0).y] == -1) {
				explore(q.get(0).x, q.get(0).y, rooms + 1);
			}
			q.remove(0);
		}

		if (q.isEmpty()) {
			if (solved) {
				pathCreator();
				return move_count;
			}
			return null;
		}
		return amazo(q.get(0).x, q.get(0).y, rooms + 1);
	}

	private void explore(int r, int c, int rooms) {
		if (r == endRow && c == endCol && !solved) {
			move_count = rooms;
			//System.out.println(move_count +"hi");
			solved = true;
		}
		visited[r][c] = rooms;
		for (int dir = 0; dir < 4; dir++) {
			if (canGo(r, c, dir)) {
				q.add(new Coord(r + ddir[dir][0], c + ddir[dir][1]));
			}
		}
	}

	private void pathCreator() {
		Coord now = new Coord(endRow, endCol);
		while (now.x != startRow || now.y != startCol) {
			maze.getRoom(now.x, now.y).onPath = true;
			for (int i = 0; i < 4; i++) {
				Coord parent = new Coord(now.x + ddir[i][0], now.y + ddir[i][1]);
				if (parent.x < 0 || parent.x >= maze.getRows() || parent.y >= maze.getColumns() || parent.y < 0) {
					continue;
				}
				if (visited[now.x][now.y] - visited[parent.x][parent.y] == 1) {
					if (!canGo(now.x, now.y, i)){
						now = parent;
						break;
					}
				}
			}
		}
		maze.getRoom(startRow, startCol).onPath = true;
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
		if (visited[row + ddir[dir][0]][col + ddir[dir][1]] != -1) {
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

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		int counter = 0;
		for (int i = 0; i < maze.getRows(); i++) {
			for (int j = 0; j < maze.getColumns(); j++) {
				if (visited[i][j] == k) {
					counter++;
				}
			}
		}
		return counter;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
 			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);
 			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Coord {
	int x;
	int y;

	Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
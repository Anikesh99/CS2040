Breadth First search

\-    Explore level by level(no going backwards)

\-    Skip already visited nodes 

\-    Calculate level[i] from level[i-1]

\-    Fails when there is a graph with two components

\-    Runtime – O(V+E)

\-    Use when you want to find the minimum number of hops not minimum distance (Parent nodes store the shortest path)

\-    Shortest path is always a tree

\-    Use a queue

Depth First Search

\-    Follow path until stuck and then backtrack until you find a new edge

\-    Don’t repeat a vertex

\-    DFS parent graph is a tree

\-    Runtime – O(V+E)

\-    If the graph is in an adjacency matrix, the runtime for DFS is O(V^2)

\-    Use a stack for iterative DFS

Adjacency List/Matrix

\-    Adjacency list is better for cycles, fast query to enumerate all neighbours and fast query to find a neighbour of v

\-    Memory – O(V+E) for a graph and O(V) for a cycle

\-    Adjacency matrix is useful for finding if you can get from one node to another with n hops and fast query to check if v and w are neighbours

\-    Memory – O(V^2) for all

\-    If graph is dense use adjacency matrix else use adjacency list where dense is E=O(V^2)

\-    List – Much better space, Matrix – somewhat faster

Where DFS and BFS fail?

\-    DFS and BFS do not explore every path because once a node is visited it is never explored again

\-    Too expensive as some graphs have exponential number of paths

Directed Graphs 

\-    Each edge is unique and directed and the order of the vertices in the edge matter

\-    Can be represented using an adjacency list/matrix

\-    Adjacency list only stores the outgoing edges from a vertex. Space – O(V+E)

\-    Adjacency Matrix only stores 1 for those edges which are possible A[v, w] represents edge (v,w). Space – O(V^2)

\-    Using breadth first search – follow outgoing edges and ignore incoming edges 

\-    Using depth first search – follow outgoing edges and backtrack through incoming edges

Topological ordering

\-    Properties

\1.   Sequential total ordering of all nodes (The order in which they are performed)

\2.   Edges only point forward

\3.   Only works for acyclic directed graphs and not all graphs may have a topological ordering

\-    Use DFS to find a topological ordering in a directed acyclic graph

\-    Two ways to search a directed graph could be pre order dfs or post order dfs

\-    Time complexity for topological sort is O(V+E) – DFS runtime

\-    A graph is strongly connected iff every vertex has a path to all other. A graph of strongly connected components is acyclic.

Bellman Ford

\-    Reduce estimate :: Invariant: estimate >= distance

\-    Bellman ford is relax all the edges and then you store the shortest distance in the node

\-    Stop when E relaxations don’t change the estimate

\-    Runtime – O(EV)

\-    To detect negative weight cycles run bellman ford for V + 1 iterations. If the estimate changes in the last iteration it is a negative weight cycle

\-    When all edges have the same weight use normal bfs

\-    Special case: Weighted undirected Tree – A tree has only V edges which makes using dfs to find the shortest/only edge give a O(V) runtime

 

Djikstra’s algo

\-    Use a AVL tree to store the vertices/distances.

\-    Keep relaxing down the shortest edge and store the just newly visited nodes in a pQueue

1. Maintain distance estimates
2. Find unfinished vertex with smallest estimate
3. Relax all outgoing edges
4. Mark vertex as done and pop from pQueue

`relax(Edge e) {`
	`	int v = e.from();`
`		int w = e.to();`
`	double weight = e.weight();`
`	if (distTo[w] > distTo[v] + weight) {`
`	distTo[w] = distTo[v] + weight;`
`	parent[w] = v;`
`	if (pq.contains(w))`
`	pq.decreaseKey(w, distTo[w]);`
`	else`
`	pq.insert(w, distTo[w]);`
`	}`
`}`

- Djikstra's uses a pqueue by AVL tree (Runtime: O(ElogV)) which supports 
  - insertion(key, priority) -  O(logn)
  - deleteMin() - O(log n)
  - decreaseKey(key, priority) - O(log n)
  - contains(key): O(1)

- Can stop doing djikstras as soon as you dequeue the destination

- Depends on pQueue implementation 

  ![image-20200422140134962](C:\Users\banik\AppData\Roaming\Typora\typora-user-images\image-20200422140134962.png)

- Fails with negative edges because you cannot reweight the graph

| DFS                                                 | Djikstra's                   | BFS          |
| --------------------------------------------------- | ---------------------------- | ------------ |
| Use stack                                           | Use priority Queue           | Use queue    |
| Use post order DFS for directed acyclic graphs O(E) | Use for non negative weights | Use for tree |



Union find

- Pre process the maze and then answer queries

- If you want to find if two nodes are connected in a graph. Pre process to identify all the connected components and label them

- Union find needs to support union (connect two components) and find (is there a path between two objects) so you need to maintain a set of connected nodes

  | Implementation | Quick Union                                                  | Quick Find                                                   | Weighted Union                                               | Weighted Union and Path Compression                          |
  | -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | Logic:         | Two objects are connected if they are part of the same tree. So you just store the immediate parent for each object. | Two objects are connected if they have the same component identifier so you maintain a int[] componentId concurrent to the object array | `union(int p, int q)<br/>while (parent[p] !=p) p = parent[p];<br/>while (parent[q] !=q) q = parent[q];<br/>if (size[p] > size[q] {<br/>parent[q] = p; // Link q to p<br/>size[p] = size[p] + size[q];<br/>}<br/>else {<br/>parent[p] = q; // Link p to q<br/>size[q] = size[p] + size[q];<br/>}`               Maximum depth: O(logn)  The height only increases when you merge two trees of same size | After finding the root: set the parent of each traversed node to the root.                   findRoot(int p) {<br/>root = p;<br/>while (parent[root] != root) {<br/>parent[root] = parent[parent[root]];<br/>root = parent[root];<br/>}<br/>return root;<br/>} + Weighted Union |
  | Find:          | `find(int p, int q) while (parent[p] != p){p = parent[p];}      while (parent[q] != q) {q = parent[q];}    return (p == q);` Runtime: O(n) | `find(int p, int q)<br/>return(componentId[p] == componentId[q]);`  Runtime: O(1) | Same as quick union but runtime is O(log n)                  | Alpha(m, n) for both                                         |
  | Union          | `union(int p, int q) while (parent[p] != p) {p = parent[p];}   while (parent[q] != q) {q = parent[q];} parent[p] = q;` Runtime: O(n) | `union(int p, int q) updateComponent = componentId[q]                   for (int i=0; i < componentId.length; i++) {if (componentId[i] == updateComponent) componentId[i] = componentId[p];`} Runtime: O(n) | Runtime is O(log n)                                          | Almost linear performance                                    |

MST

- Properties

  1. A MST cannot be used to find shortest path

  2. If you cut an MST, then the two cut components are MSTs

  3. In every cycle the maximum weight edge is not in the MST
  4. The minimum weight edge may or may not be in the MST
  5. For every vertex the minimum outgoing edge is always part of the MST

- Prim Algorithm 

  - Steps 

    1. Get all the set of nodes and then find the minimum edge to an unvisited node
    2. Add that unvisited node to the list of visited nodes and repeat

  - Use a priority queue to find the lightest edge

  - `while (!pq.isEmpty()){`
    `Node v = pq.deleteMin();`
    `S.put(v);`
    `for each (Edge e : v.edgeList()){`
    `Node w = e.otherNode(v);`
    `if (!S.get(w)) {`
    `pq.decreaseKey(w, e.getWeight());`

    `'if wt is decreased{'parent.put(w, v);}`
    `}`
    `}`
    `}`

  - Runtime: O(E logV)

- Kruskal's Algorithm
  - Steps
    1. Sort edges by weight
    2. Take edges in ascending order
    3. If an edge has both endpoints already visited then remove it from the MST
  - Use union find and connect two nodes if they are in the same blue tree
  - `// Sort edges and initialize`
    `Edge[] sortedEdges = sort(G.E());`
    `ArrayList<Edge> mstEdges = new ArrayList<Edge>();`
    `UnionFind uf = new UnionFind(G.V());`
    `// Iterate through all the edges, in order`
    `for (int i=0; i<sortedEdges.length; i++) {`
    `Edge e = sortedEdges[i]; // get edge`
    `Node v = e.one(); // get node endpoints`
    `Node w = e.two();`
    `if (!uf.find(v,w)) { // in the same tree?`
    `mstEdges.add(e); // save edge`
    `uf.union(v,w); // combine trees`
    `}`
    `}`
  - Runtime: O(E logV)

- Boruvka's
  - Properties
    1. Parllelizable
    2. Faster in planar graphs
    3. Flexible
  - Steps
    1. Add in all the obvious edges i.e. for each node add in the minimum adjacent edge(atleast n/2 edges)
    2. Look at each connected component and add in the minimum outgoing component
    3. Merge the new connected components and add in the outgoing edges
  - Runtime: O(ElogV)

Special MST Variants

- Maximum spanning tree- Just multiply all the edge weights by -1 and find the most negative tree
- Steiner Tree- Minimum spanning tree of a subset of vertices(NP hard)
  - Use all pair shortest path to calculate shortest path from v to w
    1. For every required vertex (v,w), calculate the
    shortest path from (v to w).
    2. Construct new graph on required nodes.
    3. Run MST on new graph.
    4. Map new edges back to original graph.

Dynamic Programming

- If no overlapping subproblems use divide and conquer else use dynamic programming

- Longest increasing subsequence

  - Runtime O(n^2) using greedy subproblems

  - Steps
    1. No need to topological sort since its already in the right order
    2. Using directed acyclic graph examine each outgoing edge from a node and find the maximum.
    3. The maximum length can be found by the maximum length of the previous node + 1.

  ![image-20200422202759456](C:\Users\banik\AppData\Roaming\Typora\typora-user-images\image-20200422202759456.png)

- All pairs shortest path
  - Floyd Warshall 
    - Runtime: O(V^3) 
    - Utter trash never use



Heaps

- Implements a max priority queue

- Biggest items at root, Smallest item at leaves

- priority[parent] >= priority[child]

- Fill every level from the left 

- Max height: floor(log n)

- Cost of building heap: O(n)

- parent(x) = floor((x - 1)/2)

- | Operation   | Steps                                                        | Runtime  |
  | ----------- | ------------------------------------------------------------ | -------- |
  | Insertion   | Add a new leaf and then bubble up                            | O(log n) |
  | Deletion    | Swap the element and the last element, then remove it when it is last and bubble down the actual last | O(log n) |
  | IncreaseKey | Find the element you want to change and then increase its key and bubble up | O(log n) |
  | DecreaseKey | Find the element and update its priority and then bubbledown to the left | O(log n) |
  | extractMax  | delete(root)                                                 | O(log n) |

- Heap sort can be performed by just extractingMax n times which is a runtime of O(nlogn)



Fingerprint hashing

- No false negatives
- Key is not stored in the table: Only store 0/1 vector
- Probability of a false positive: 1 - (1/e)^(n/m)
- Increased space to reduce collisions
- Use a bloom filter to reduce chances of false positives
- Probability of false positive: (1 - e^(-kn/m))^k

Orthogonal Range Searching Trees

- Query time for a one dimensional query is O(k + log n) where k is the number of points found
- Preprocessing time: O(log n) and space: O(n)
- If you want to know just how many points are in the range: augment the tree by keeping count of the number of nodes in each sub tree
- Two dimensional range tree is simply to augment each node in the x-tree with the y-tree containing all the points in the subtree
- Query time for 2 dimensional range tree: O((logn)^2 + k) and space complexity is O(nlogn)
- Insertion is O(n)

Range Interval Tree

- Make the tree on the starting point and augment with the maximum end point in subtree
- If search goes right, there is no overlap with the left subtree
- Interval search: O(log n)
- 
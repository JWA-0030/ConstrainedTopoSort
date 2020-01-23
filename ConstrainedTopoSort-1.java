// Jesse Alsing


import java.io.*;
import java.util.*;

public class ConstrainedTopoSort{

	// we want some global private variables to keep track of our data structures and the number in inputs (N)
	private boolean [][] matrix;
	private int N;
	private boolean [] visited;
	
	public ConstrainedTopoSort(String filename){
		
		try{
			
			// Create the scanner object for file input.
			Scanner in = new Scanner(new File(filename));
			
			// Read numbers from the file.
			N = in.nextInt();
			matrix = new boolean[N][N];
			
			// I know that each line following the first is the node we are at even though it isnt scanned in
			for(int i = 0; i < N; i++){
				
				// I need to number of vertices out to make our inner for loop size
				int numberOut = in.nextInt();
				
				for(int j = 0; j < numberOut; j++){
					// Here instead of adding one to our max matrix size I just decrement the integer scanned in by 1
					matrix[i][in.nextInt() -1] = true;
				}	
			}
		} 
		// we want to catch an error if the file isnt found and print it to screen
       catch (FileNotFoundException ex)
       {
           System.out.println("Error "+ ex);
       }
	}
	
	public boolean hasConstrainedTopoSort(int x, int y){
		
		visited = new boolean[matrix.length];
		
		// Thank you almighty Sean for letting us use your toposort code.
		// I only need this to make sure that there is a valid Toposort for the given graph
		int [] incoming = new int[matrix.length];
		int cnt = 0;

		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix.length; j++)
				incoming[j] += (matrix[i][j] ? 1 : 0);
			
		Queue<Integer> q = new ArrayDeque<Integer>();

		for (int i = 0; i < matrix.length; i++)
			if (incoming[i] == 0)
				q.add(i);
			
		while (!q.isEmpty()){

			int node = q.remove();

			++cnt;

			for (int i = 0; i < matrix.length; i++)
				if (matrix[node][i] && --incoming[i] == 0)
					q.add(i);
		}
			
		if (cnt != matrix.length)
			return false;
		
		// I need to decrement x and y by one so it will search the appropriate spots in my matrix
		return DFS(y-1, x-1, visited);
	}
	
	// I can just do a Depth First Search on y and if we find x we know that a topological sort does NOT exist
	// I also used some of Sean's code here but I had to edit it quite a bit
	private boolean DFS(int node, int x, boolean [] visited){
		
		visited [node] = true;
		boolean retVal = true;
		
		if(node == x){
			return false;
		}
		
		for (int i = 0; i < matrix.length; i++){
			
			if (matrix[node][i] && !visited[i]){
				// I want to and these two values because when I hit a base case i go back to the previous call
				// and still need that false and we know true && false is false
				retVal = DFS(i, x, visited) && retVal;
			}
		}
		return retVal;
	}

	// I am torn about how to rate this. I spent a couple days trying to conceptualize it and when i thought i found
	// one with linear runtime i freaked and it only didnt work on two types of graphs. So i had to scrap it and 
	// After spending a few days trying to forget the line of thought that led me to the wrong answer so I asked a few classmates
	// a few conceptual questions and saw that I can run a DFS on Y and if we find X we can return false and all graphs fit this.
	// I thought "what about disconnected graphs dont those DFS include both halves" - well they dont once it finished seraching that graph
	// it will not run on the other part and if X isnt found then there is a valid toposort
	public static double difficultyRating(){
	
		return 2.5;
	}

	 public static double hoursSpent(){

		return 8.5;
	}	

}
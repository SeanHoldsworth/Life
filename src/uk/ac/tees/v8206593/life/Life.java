package uk.ac.tees.v8206593.life;

import java.util.ArrayList;

class Life {
    private int lenSide;  // Length of edge of board
    private int nCells;   // Number of cells on board

    // Each cell offset indexes a list of neighbor offsets. I would like to
    // use a simple array here but Java has a problem creating arrays of
    // generic objects and so I settled on using an ArrayList.
    
    //private ArrayList<Integer> neighborsByCell[];

    private ArrayList<ArrayList<Integer>> neighborsByCell;
    private int thisGen[], nextGen[];

    Life(int cellsPerSide) {
        initialize(cellsPerSide);
    }

    public void initialize(int cellsPerSide) {
        lenSide = cellsPerSide;
        nCells = lenSide * lenSide;

        thisGen = new int[nCells];
        nextGen = new int[nCells];

        // Construct the array providing a list neighbors for each cell.

        neighborsByCell = new ArrayList<ArrayList<Integer>>();

        for (int i = 0; i < nCells; i++) {
            neighborsByCell.add(findNeighbors(i));
            thisGen[i] = (Math.random() < 0.2) ? 1 : 0;
        }
    }

    private ArrayList<Integer> findNeighbors(int offset) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        final int modulo = offset % lenSide;

        if (offset >= lenSide) {
            if (modulo > 0)
                neighbors.add(offset - lenSide - 1);

            neighbors.add(offset - lenSide);

            if (modulo != lenSide - 1)
                neighbors.add(offset - lenSide + 1);
        }

        if (modulo != 0)
            neighbors.add(offset - 1);

        if (modulo != (lenSide - 1))
            neighbors.add(offset + 1);

        if (offset < nCells - lenSide) {
            if (modulo > 0)
                neighbors.add(offset + lenSide - 1);

            neighbors.add(offset + lenSide);

            if (modulo != (lenSide - 1)) 
                neighbors.add(offset + lenSide + 1);
        }

        return neighbors;
    }

    public void createNextGen() {
        int nextState[][] = {
            {0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0}};

        for (int i = 0; i < nCells; i++) {
            int nNeighbors = 0;

            for (int offset : neighborsByCell.get(i))
                nNeighbors += thisGen[offset];

            nextGen[i] = nextState[thisGen[i]][nNeighbors];
        }

        int temp[] = nextGen;
        nextGen = thisGen;
        thisGen = temp;
    }

    public int[] getCells() {
        return thisGen;
    }

    public void display() {
        for (int i = 0; i < nCells; i++) {
            System.out.printf("%c ", "_X".charAt(thisGen[i]));
            if ((i + 1) % lenSide == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static void main(String args[]) {
        Life life = new Life(15);

        while (true) {
            life.display();
            System.console().readLine();
        }
    }
}

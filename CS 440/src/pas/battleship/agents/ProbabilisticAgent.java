package src.pas.battleship.agents;


// SYSTEM IMPORTS
import java.util.*;


// JAVA PROJECT IMPORTS
import edu.bu.battleship.agents.Agent;
import edu.bu.battleship.game.Game.GameView;
import edu.bu.battleship.game.EnemyBoard;
import edu.bu.battleship.game.ships.Ship;
import edu.bu.battleship.game.EnemyBoard.Outcome;
import edu.bu.battleship.utils.Coordinate;


public class ProbabilisticAgent
    extends Agent
{

    private static Map<Ship.ShipType, Integer> shipLengths = new HashMap<Ship.ShipType, Integer>();
    private static String mode; // Hunt or target, target if we found a hit and are attacking around it. Hunt if we don't know any hits on the board

    public ProbabilisticAgent(String name)
    {
        super(name);
        shipLengths.put(Ship.ShipType.AIRCRAFT_CARRIER, 5);
        shipLengths.put(Ship.ShipType.BATTLESHIP, 4);
        shipLengths.put(Ship.ShipType.DESTROYER, 3);
        shipLengths.put(Ship.ShipType.SUBMARINE, 3);
        shipLengths.put(Ship.ShipType.PATROL_BOAT, 2); 
        mode = "HUNT";
        System.out.println("[INFO] ProbabilisticAgent.ProbabilisticAgent: constructed agent");
    }

    @Override
    public Coordinate makeMove(final GameView game)
    {
        int[][] probMap = getHeatMap(game);

        for (int i = 0; i < probMap.length; i++) {
            for (int j = 0; j < probMap[0].length; j++) {
                System.out.print(probMap[i][j] + " ");
            }
            System.out.println();
        }

        return null;
    }

    private int[][] getHeatMap(final GameView game) {

        // Get the dimension of the game board 
        int rows = game.getGameConstants().getNumRows();
        int cols = game.getGameConstants().getNumCols();

        // Should be init to a grid of 0.0s 
        int[][] heatMap = new int[rows][cols];

        // Previous Shots 
        EnemyBoard.Outcome[][] previousShots = game.getEnemyBoardView();

        Map<Ship.ShipType, Integer> shipCounts = game.getEnemyShipTypeToNumRemaining();

        // Loop over each ship type
        for (Ship.ShipType ship : shipCounts.keySet()) {
            
            int remainingLength = shipLengths.get(ship) - 1; // The length of the current polling ship, subtract one because current square counts as one of the "length"

            int count = shipCounts.get(ship); // The count of the number of ships of this type remaining

            // Loop over board
            for (int i = 0; i < heatMap.length; i++) {
                for (int j = 0; j < heatMap[0].length; j++) {

                    // Only poll this cell, if we have not shot here before
                    if (previousShots[i][j].equals(Outcome.UNKNOWN)) {
                        // List of possible orientation's endpoints
                        List<Coordinate> endpoints = new ArrayList<Coordinate>();

                        if (i - remainingLength >= 0) {
                            endpoints.add(new Coordinate(i - remainingLength, j));
                        }

                        if (i + remainingLength < heatMap.length) {
                            endpoints.add(new Coordinate(i + remainingLength, j));
                        }

                        if (j - remainingLength >= 0) {
                            endpoints.add(new Coordinate(i, j - remainingLength));
                        }

                        if (j + remainingLength < heatMap[0].length) {
                            endpoints.add(new Coordinate(i, j + remainingLength));
                        }

                        // If all points between [i][j] and endpoint are unknown, increment the heatmap
                        for (Coordinate endpoint: endpoints) {
                            boolean valid = true;
                            int x = i;
                            int y = j;

                            // Check which direction the boat is for this endpoint
                            if (endpoint.getXCoordinate() == x) {
                                // Vertical so change y
                                y = Math.min(y, endpoint.getYCoordinate());
                                for (int k = 0; k < remainingLength + 1; k++) {
                                    if (!previousShots[x][y + k].equals(Outcome.UNKNOWN)) {
                                        valid = false;
                                        break;
                                    }
                                }

                            } else {
                                // Horizonal so change x
                                x = Math.min(x, endpoint.getXCoordinate());
                                for (int k = 0; k < remainingLength + 1; k++) {
                                    if (!previousShots[x + k][y].equals(Outcome.UNKNOWN)) {
                                        valid = false;
                                        break;
                                    }
                                }
                            }

                            // If valid update the heatmap
                            if (valid) {
                                heatMap[i][j] += count;
                            
                            }
                        }
                    }
                }
            }

        }

        return heatMap;
    }

    @Override
    public void afterGameEnds(final GameView game) {}

}

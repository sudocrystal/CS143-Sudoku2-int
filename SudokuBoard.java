import java.util.*;
import java.io.*;

public class SudokuBoard {
    private int[][] board; // 0 for empty spaces

    public SudokuBoard(String fileName) {
        board = new int[9][9];
        loadFile(fileName);
    }

    private void loadFile(String fileName) {
        try {
            Scanner input = new Scanner(new File(fileName));
            for (int r = 0; r < board.length; r++) {
                String line = input.nextLine();
                for (int c = 0; c < board[r].length; c++) {
                    int spot = 0;
                    if (line.charAt(c) != '.') {
                        spot = line.charAt(c) - '0';
                    }
                    board[r][c] = spot;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
    }

    private boolean checkData() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];
                if (val < 0 || val > 9) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkRows() {
        for (int r = 0; r < 9; r++) {
            Set < Integer > number = new HashSet < > ();
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];
                if (val != 0) {
                    if (!number.add(val)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkColumns() {
        for (int c = 0; c < 9; c++) {
            Set < Integer > number = new HashSet < > ();
            for (int r = 0; r < 9; r++) {
                int val = board[r][c];
                if (val != 0) {
                    if (!number.add(val)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int[][] miniSquare(int spot) {
        int[][] mini = new int[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                mini[r][c] = board[(spot - 1) / 3 * 3 + r][(spot - 1) % 3 *
                    3 + c
                ];
            }
        }
        return mini;
    }

    private boolean checkSquares() {
        for (int spot = 1; spot <= 9; spot++) {
            int[][] mini = miniSquare(spot);
            Set < Integer > seen = new HashSet < > ();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    int val = mini[r][c];
                    if (val != 0) {
                        if (!seen.add(val)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }



    public boolean isValid() {
        return checkData() && checkRows() && checkColumns() &&
        checkSquares();
    }

    public boolean isSolved() {
        if (!isValid()) {
            return false;
        }

        Map < Integer, Integer > counts = new HashMap < > ();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int val = board[r][c];

                if (val >= 1 && val <= 9) {
                    counts.put(val, counts.getOrDefault(val, 0) + 1);
                }
            }
        }

        for (int i = 1; i <= 9; i++) {
            if (!counts.containsKey(i) || counts.get(i) != 9) {
                return false;
            }
        }

        return true;
    }

    public String toString() {
        String result = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 1; c < board[r].length; c++) {
                // Space at the start of each row
                if (c == 0) {
                    result += " ";
                }

                if (board[r][c] == 0) {
                    result += "-";
                } else {
                    result += board[r][c];
                }

                // Prevent a '|' at the end of the row
                if (c < board[r].length - 1) {
                    if ((c + 1) % 3 == 0) {
                        result += " | ";
                    } else {
                        result += "   ";
                    }
                }
            }
            result += "\n";

            // Prevent divider after last row
            if (r < board.length - 1) {
                if ((r + 1) % 3 == 0) {
                    result += "–––––––––––|–––––––––––|––––––––––\n";
                }
            }
        }
        return result;
    }
}
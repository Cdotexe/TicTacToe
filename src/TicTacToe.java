import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private char currentPlayer;
    private JButton[] cells;

    public TicTacToe() {
        super("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        currentPlayer = 'X';

        JPanel panel = new JPanel(new GridLayout(3, 3));
        panel.setPreferredSize(new Dimension(300, 300));

        Font font = new Font("Times New Roman", Font.BOLD, 60);

        cells = new JButton[9];
        for (int i = 0; i < 9; i++) {
            JButton cell = new JButton();
            cell.setBackground(Color.BLACK);
            cell.setForeground(Color.WHITE);
            cell.setFont(font);
            cell.setFocusPainted(false);
            cell.addActionListener(this);
            panel.add(cell);
            cells[i] = cell;
        }

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void makeMove(JButton cell) {
        cell.setText(Character.toString(currentPlayer));
        cell.setEnabled(false);
    }

    private boolean checkWin(char player) {
        int[][] winningCombinations = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
                {0, 4, 8}, {2, 4, 6} // diagonals
        };

        for (int[] combination : winningCombinations) {
            int a = combination[0];
            int b = combination[1];
            int c = combination[2];
            if (cells[a].getText().equals(Character.toString(player)) &&
                    cells[b].getText().equals(Character.toString(player)) &&
                    cells[c].getText().equals(Character.toString(player))) {
                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull() {
        for (JButton cell : cells) {
            if (cell.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton cell : cells) {
            cell.setText("");
            cell.setEnabled(true);
        }
    }

    private void announceResult(String message) {
        JOptionPane.showMessageDialog(this, message);
        resetBoard();
    }

    public void actionPerformed(ActionEvent e) {
        JButton cell = (JButton) e.getSource();

        if (cell.isEnabled()) {
            makeMove(cell);

            if (checkWin(currentPlayer)) {
                announceResult("Player " + currentPlayer + " wins! Winner winner chicken dinner!");
            } else if (isBoardFull()) {
                announceResult("The game is a draw!");
            } else {
                switchPlayer();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TicTacToe();
            }
        });
    }
}

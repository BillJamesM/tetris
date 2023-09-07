package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel {

    private JButton newGameButton;
    private JButton exitButton;
    private JButton helpButton;
    private JButton pauseButton;

    private ActionListener newGameAction;
    private ActionListener exitAction;
    private ActionListener helpAction;
    private ActionListener pauseAction;

    public GameMenu() {
        setLayout(new FlowLayout());

        newGameButton = new JButton("New Game");
        exitButton = new JButton("End Game");
        helpButton = new JButton("Help");
        pauseButton = new JButton("Pause/Resume");

        // Add buttons to the panel
        add(newGameButton);
        add(exitButton);
        add(helpButton);
        add(pauseButton);

        // Initialize button actions
        initializeActions();
//        setButtonActions();
    }

    public void setNewGameAction(ActionListener actionListener) {
        newGameAction = actionListener;
    }

    public void setExitAction(ActionListener actionListener) {
        exitAction = actionListener;
    }

    public void setHelpAction(ActionListener actionListener) {
        helpAction = actionListener;
    }

    public void setPauseAction(ActionListener actionListener) {
        pauseAction = actionListener;
    }

    private void initializeActions() {
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (newGameAction != null) {
                    newGameAction.actionPerformed(e);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (exitAction != null) {
                    exitAction.actionPerformed(e);
                }
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (helpAction != null) {
                    helpAction.actionPerformed(e);
                }
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pauseAction != null) {
                    pauseAction.actionPerformed(e);
                }
            }
        });
    }
}

package com.owenherbert.cp3406.rocketmaths.utility;

import com.owenherbert.cp3406.rocketmaths.utility.Image;

/**
 * The TutorialPage enum represents a single page on the tutorial activity.
 *
 * @author Owen Herbert
 */
public enum TutorialPage {

    PLANET_ALIGNMENT(
            0,
            Image.TUT_PLANET_ALIGNMENT,
            "Planet alignment",
            "Shake your device when two planets align horizontally to claim extra points."),

    FORGOT_WORKING_NUMBER(
            1,
            Image.TUT_FORGOT_WORKING_NUM,
            "Forgot working number",
            "Tap the equation bar to remind yourself of the working number. Points will be removed."
    ),

    SHARE_HIGHSCORE(
            2,
            Image.TUT_SHARE_HIGHSCORE,
            "Share your highscore",
            "Click your highscore to share it on Twitter."
    ),

    GAME_GOAL(
            3,
            Image.TUT_GAME_GOAL,
            "Goal of the game",
            "The goal of the game is to gain as many points as possible before the CPU gets to the end."
    ),

    GAME_DIFFICULTY(
            4,
            Image.TUT_GAME_DIFFICULTY,
            "Change the difficulty",
            "You can change the difficulty of the game by visiting settings."
    );

    // instance variables
    private final int id;
    private final Image image;
    private final String title;
    private final String description;

    /**
     * Creates a TutorialPage.
     *
     * @param id the id of the enum
     * @param image the image to be displayed
     * @param title the title of the tutorial page
     * @param description the description of the tutorial page
     */
    TutorialPage(int id, Image image, String title, String description) {

        this.id = id;
        this.image = image;
        this.title = title;
        this.description = description;
    }

    /**
     * Get the id.
     *
     * @return the id
     */
    public int getId() {

        return id;
    }

    /**
     * Get the image.
     *
     * @return the image
     */
    public Image getImage() {

        return image;
    }

    /**
     * Get the title.
     *
     * @return the title
     */
    public String getTitle() {

        return title;
    }

    /**
     * Get the description.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }
}

package com.owenherbert.cp3406.rocketmaths.utility;

import com.owenherbert.cp3406.rocketmaths.R;

/**
 * The Image enum represents an image resource.
 *
 * @author Owen Herbert
 */
public enum Image {
    //TODO: put in package - improve structure
    PLANET_ONE(R.drawable.planet, true),
    PLANET_TWO(R.drawable.planet_2, true),
    PLANETS_FAR(R.drawable.planets_far, true),
    ROCKET(R.drawable.rocket, false),
    STAR_CLUSTER(R.drawable.stars, true),
    TUT_PLANET_ALIGNMENT(R.drawable.planet_alignment, false),
    TUT_FORGOT_WORKING_NUM(R.drawable.forgot_working_number, false),
    TUT_SHARE_HIGHSCORE(R.drawable.share_highscore, false),
    TUT_GAME_GOAL(R.drawable.game_goal, false),
    TUT_GAME_DIFFICULTY(R.drawable.game_difficulty, false);

    // instance variables
    private final int resourceId;
    private final boolean isIntendedForParallax;

    /**
     * Creates an Image.
     *
     * @param resourceId the android resource id of the image
     * @param isIntendedForParallax if the image is intended for parallax use
     */
    Image(int resourceId, boolean isIntendedForParallax) {

        this.resourceId = resourceId;
        this.isIntendedForParallax = isIntendedForParallax;
    }

    public int getResourceId() {

        return resourceId;
    }

    public boolean isIntendedForParallax() {

        return isIntendedForParallax;
    }
}

package bbs.december.blocktrail.movement;

public enum Moves {

    //on spot moves (turning airnode into regular block and vis versa)
    OSM_DIG(0, 0, 0) {
        //turns current node into an airnode
    }

    OSM_PLACE(0, 0, 0) {
        //place block directly beneath the feet of the player in order to stop the fall/jump.
    }

    //traverse moves
    T_WHITE_NORTH(0, 0, -1) {
        //can NOT result in airnode
    },

    T_WHITE_NORTHEAST(+1, 0, -1) {
        //can NOT result in airnode
    },

    T_WHITE_EAST(+1, 0, 0) {
        //can NOT result in airnode
    },

    T_WHITE_SOUTHEAST(+1, 0, +1) {
        //can NOT result in airnode
    },

    T_WHITE_SOUTH(0, 0, +1) {
        //can NOT result in airnode
    },

    T_WHITE_SOUTHWEST(-1, 0, +1) {
        //can NOT result in airnode
    },

    T_WHITE_WEST(-1, 0, 0) {
        //can NOT result in airnode
    },

    T_WHITE_NORTHWEST(-1, 0, -1) {

    },
    
    //TRAVERSE TO DROP
    D_WHITE_NORTH(0, -1, -1) {
        //can  result in airnode
    },

    D_WHITE_NORTHEAST(+1, -1, -1) {
        //can  result in airnode
    },

    D_WHITE_EAST(+1, -1, 0) {
        //can  result in airnode
    },

    D_WHITE_SOUTHEAST(+1, -1, +1) {
        //can  result in airnode
    },

    D_WHITE_SOUTH(0, -1, +1) {
        //can  result in airnode
    },

    D_WHITE_SOUTHWEST(-1, -1, +1) {
        //can  result in airnode
    },

    D_WHITE_WEST(-1, -1, 0) {
        //can  result in airnode
    },

    D_WHITE_NORTHWEST(-1, -1, -1) {

    },

    //jumpmoves
    //white
    J_WHITE_NORTH(0, +1, -1) {
        //can result in airnode
    },

    J_WHITE_NORTHEAST(+1, +1, -1) {
        //can result in airnode
    },

    J_WHITE_EAST(+1, +1, 0) {
        //can result in airnode
    },

    J_WHITE_SOUTHEAST(+1, +1, +1) {
        //can result in airnode
    },

    J_WHITE_SOUTH(0, +1, +1) {
        //can result in airnode
    },

    J_WHITE_SOUTHWEST(-1, +1, +1) {
        //can result in airnode
    },

    J_WHITE_WEST(-1, +1, 0) {
        //can result in airnode
    },

    J_WHITE_NORTHWEST(-1, +1, -1) {
        //can result in airnode
    },

    //blue

    J_BLUE_NORTH_(0, +1, -1) {
        //can result in airnode
    },
    

    J_BLUE_EAST(+2, +1, 0) {
        //can result in airnode
    },
    

    J_BLUE_SOUTH(0, +1, +2) {
        //can result in airnode
    },

    J_BLUE_WEST(-2, +1, 0) {
        //can result in airnode
    },
    
    //yellow
    J_YELLOW_NORTH(0, +1, -3) {
        //can result in airnode
    },

    J_YELLOW_NORTHEAST(+2, +1, -2) {
        //can result in airnode
    },

    J_YELLOW_EAST(+3, +1, 0) {
        //can result in airnode
    },

    J_YELLOW_SOUTHEAST(+2, +1, +2) {
        //can result in airnode
    },

    J_YELLOW_SOUTH(0, +1, +3) {
        //can result in airnode
    },

    J_YELLOW_SOUTHWEST(-2, +1, +2) {
        //can result in airnode
    },

    J_YELLOW_WEST(-3, +1, 0) {
        //can result in airnode
    },

    J_YELLOW_NORTHWEST(-2, +1, -2) {
        //can result in airnode
    },
    
    //red

    J_RED_NORTH(0, +1, -4) {
        //can NOT result in airnode
    },

    J_RED_NORTHEAST(+3, +1, -3) {
        //can NOT result in airnode
    },

    J_RED_EAST(+4, +1, 0) {
        //can NOT result in airnode
    },

    J_RED_SOUTHEAST(+3, +1, +3) {
        //can NOT result in airnode
    },

    J_RED_SOUTH(0, +1, +4) {
        //can NOT result in airnode
    },

    J_RED_SOUTHWEST(-3, +1, +3) {
        //can NOT result in airnode
    },

    J_RED_WEST(-4, +1, 0) {
        //can NOT result in airnode
    },

    J_RED_NORTHWEST(-3, +1, -3) {
        //can NOT result in airnode
    },
    
    //purple
    J_PURPLE_NORTH(0, 0, -4) {
        //can  result in airnode
    },

    J_PURPLE_NORTHEAST(+3, 0, -3) {
        //can  result in airnode
    },

    J_PURPLE_EAST(+4, 0, 0) {
        //can  result in airnode
    },

    J_PURPLE_SOUTHEAST(+3, 0, +3) {
        //can  result in airnode
    },

    J_PURPLE_SOUTH(0, 0, +4) {
        //can  result in airnode
    },

    J_PURPLE_SOUTHWEST(-3, 0, +3) {
        //can  result in airnode
    },

    J_PURPLE_WEST(-4, 0, 0) {
        //can  result in airnode
    },

    J_PURPLE_NORTHWEST(-3, 0, -3) {
        //can  result in airnode
    },

    /**
    //on spot moves
    UP_PILLAR(0, +1, 0) {

    },

    DOWN_DIG(0, -1, 0) {

    },

    //even traverse movement (walking/sprinting)
    EVEN_NORTH_TRAVERSE(0, 0, -1) {

    },

    EVEN_NORTHEAST_TRAVERSE(+1, 0, -1) {

    },

    EVEN_EAST_TRAVERSE(+1, 0, 0) {

    },

    EVEN_SOUTHEAST_TRAVERSE(+1, 0, +1) {

    },

    EVEN_SOUTH_TRAVERSE(0, 0, +1) {

    },

    EVEN_SOUTHWEST_TRAVERSE(-1, 0, +1) {

    },

    EVEN_WEST_TRAVERSE(-1, 0, 0) {

    },

    EVEN_NORTHWEST_TRAVERSE(-1, 0, -1) {

    },

    //down traverse movement (walking/sprinting)
    DOWN_NORTH_TRAVERSE(0, -1, -1) {

    },

    DOWN_NORTHEAST_TRAVERSE(+1, -1, -1) {

    },

    DOWN_EAST_TRAVERSE(+1, -1, 0) {

    },

    DOWN_SOUTHEAST_TRAVERSE(+1, -1, +1) {

    },

    DOWN_SOUTH_TRAVERSE(0, -1, +1) {

    },

    DOWN_SOUTHWEST_TRAVERSE(-1, -1, +1) {

    },

    DOWN_WEST_TRAVERSE(-1, -1, 0) {

    },

    DOWN_NORTHWEST_TRAVERSE(-1, -1, -1) {

    },

    //up movement (simple jump)
    UP_NORTH_TRAVERSE(0, +1, -1) {

    },

    UP_NORTHEAST_TRAVERSE(+1, +1, -1) {

    },

    UP_EAST_TRAVERSE(+1, +1, 0) {

    },

    UP_SOUTHEAST_TRAVERSE(+1, +1, +1) {

    },

    UP_SOUTH_TRAVERSE(0, +1, +1) {

    },

    UP_SOUTHWEST_TRAVERSE(-1, +1, +1) {

    },

    UP_WEST_TRAVERSE(-1, +1, 0) {

    },

    UP_NORTHWEST_TRAVERSE(-1, +1, -1) {

    },

    //there are a little over 4 times as many options as are listed here right now

     **/

}

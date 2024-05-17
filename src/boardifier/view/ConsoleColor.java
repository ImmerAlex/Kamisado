package boardifier.view;

public class ConsoleColor {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;97m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\u001B[48;2;0;0;0m";  // BLACK
    public static final String RED_BACKGROUND = "\u001B[48;2;255;0;0m";    // RED
    public static final String GREEN_BACKGROUND = "\u001B[48;2;0;255;0m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\u001B[48;2;255;255;0m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\u001B[48;2;0;0;255m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String GREY_BACKGROUND = "\033[47m";  // GREY
    public static final String WHITE_BACKGROUND = "\033[107m";  // WHITE
    public static final String BROWN_BACKGROUND = "\u001B[48;2;139;69;19m"; // BROWN
    public static final String PINK_BACKGROUND = "\u001B[48;2;255;0;128m"; // PINK
    public static final String MAGENTA_BACKGROUND = "\u001B[48;2;255;0;255m"; // MAGENTA
    public static final String ORANGE_BACKGROUND = "\u001B[48;2;255;165;0m"; // ORANGE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE


    private static String[] BACKGROUNDS = {BROWN_BACKGROUND, GREEN_BACKGROUND, RED_BACKGROUND, YELLOW_BACKGROUND, PINK_BACKGROUND, MAGENTA_BACKGROUND, BLUE_BACKGROUND, ORANGE_BACKGROUND};

    public static String getColorValue(String value) {
        for (int i = 0; i < BACKGROUNDS.length; i++) {
            if (value.contains(BACKGROUNDS[i])) {
                return BACKGROUNDS[i];
            }
        }
        return "null";
    }

    public static String getColor(int color) {
        return switch (color) {
            case 1 -> BROWN_BACKGROUND;
            case 2 -> GREEN_BACKGROUND;
            case 3 -> RED_BACKGROUND;
            case 4 -> YELLOW_BACKGROUND;
            case 5 -> PINK_BACKGROUND;
            case 6 -> MAGENTA_BACKGROUND;
            case 7 -> BLUE_BACKGROUND;
            case 8 -> ORANGE_BACKGROUND;
            default -> WHITE_BACKGROUND;
        };
    }
}

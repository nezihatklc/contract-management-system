package com.project.cms.ui.animation;

import com.project.cms.util.ConsoleColors;

/**
 * Utility class responsible for rendering all ASCII-based animations used in the CMS project.
 * <p>
 * This class provides multiple visual effects such as space warp transitions, glitch reveal,
 * particle explosions, glowing color shifts, sliding group banners, disco animations, and
 * balloon party effects. It is used primarily during application startup and shutdown.
 */

public class AsciiAnimator {

    // =========================================
    //              UTIL
    // =========================================

    /**
     * Pauses the animation for the given number of milliseconds.
     *
     * @param ms time to sleep in milliseconds
     */
    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    /**
     * Clears the console screen using ANSI escape codes.
     */

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

     /**
     * Returns a randomly selected star character decorated with a random color.
     *
     * @return a colored ASCII star
     */

    private static String randomStarChar() {
        String[] stars = {"✦", "✧", "✸", "✺", "✶", "✷", "✹", "✵"};
        String s = stars[(int) (Math.random() * stars.length)];
        int c = (int) (Math.random() * 2);
        String color;
        switch (c) {
            case 0 -> color = ConsoleColors.YELLOW_BRIGHT;
            default -> color = ConsoleColors.RED_BOLD;
        }
        return color + s + ConsoleColors.RESET;
    }

     /**
     * Generates a horizontal line of random star characters.
     *
     * @param count number of stars in the line
     * @return full star line as string
     */

    private static String starLine(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(randomStarChar());
        }
        return sb.toString();
    }

    /**
     * Prints a star-filled background of the specified number of lines.
     *
     * @param lines number of starfield lines
     */

    private static void drawStarfield(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println(starLine(80));
        }
    }

    
    private static final String WELCOME_ASCII = """
 __        __   _
 \\ \\      / /__| | ___ ___  _ __ ___   ___
  \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\
   \\ V  V /  __/ | (_| (_) | | | | | |  __/
    \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|
""";

    private static final String GOODBYE_ASCII = """
   ____                 _ _
  / ___| ___   ___   __| | |__  _   _  ___
 | |  _ / _ \\ / _ \\ / _` | '_ \\| | | |/ _ \\
 | |_| | (_) | (_) | (_| | |_) | |_| |  __/
  \\____|\\___/ \\___/ \\__,_|_.__/ \\__, |\\___|
                                |___/
""";

    private static final String GROUP_TITLE =
            ConsoleColors.PURPLE_BOLD +
                    "                         ★  GROUP 18  ★" +
                    ConsoleColors.RESET;

    private static final String NAMES_BOX =
            ConsoleColors.PURPLE_BOLD +
                    "+------------------------------------------------------------+\n" +
                    "|                       Nezihat Kılıç                       |\n" +
                    "|                        Simay Mutlu                        |\n" +
                    "|                      Pelin Cömertler                      |\n" +
                    "|                    Zeynep Sıla Şimşek                     |\n" +
                    "+------------------------------------------------------------+" +
                    ConsoleColors.RESET;

    // =========================================
    //       1) SPACE WARP / TUNNEL INTRO
    // =========================================

     /**
     * Plays a tunnel-like “space warp” intro animation, then reveals the given ASCII text.
     *
     * @param ascii  ASCII logo to display
     * @param frames number of animation frames
     */
    private static void spaceWarpIntro(String ascii, int frames) {
        String[] logoLines = ascii.split("\n");
        int logoHeight = logoLines.length;

        for (int f = 0; f < frames; f++) {
            clearScreen();

            double t = (double) f / (frames - 1); // 0..1
            int height = 20;
            int center = 40;

            StringBuilder sb = new StringBuilder();

            for (int y = 0; y < height; y++) {
                int dy = Math.abs(y - height / 2);
                int offset = (int) (dy * (1.5 + 6 * t));

                
                for (int i = 0; i < center - offset; i++) sb.append(" ");
                sb.append(ConsoleColors.RED_BOLD).append("/").append(ConsoleColors.RESET);
                
                
                int gap = offset * 2;
                for (int i = 0; i < gap; i++) {
                    if (Math.random() < 0.06) sb.append(randomStarChar());
                    else sb.append(" ");
                }

                sb.append(ConsoleColors.RED_BOLD).append("\\").append(ConsoleColors.RESET);
                sb.append("\n");
            }

            
            if (t > 0.45) {
                sb.append("\n");
                for (String line : logoLines) {
                    sb.append(ConsoleColors.RED).append(line).append(ConsoleColors.RESET).append("\n");
                }
            }

            System.out.print(sb.toString());
            sleep(55);
        }

        clearScreen();
    }

    // =========================================
    //       2) GLITCH REVEAL
    // =========================================

    /**
     * Displays a glitchy reveal effect over the given ASCII text. Characters shake, distort,
     * and randomly transform before stabilizing.
     *
     * @param ascii  ASCII text to reveal
     * @param frames number of glitch animation frames
     */
    private static void glitchReveal(String ascii, int frames) {
        String[] lines = ascii.split("\n");

        for (int f = 0; f < frames; f++) {
            clearScreen();
            drawStarfield(2);
            System.out.println();

            double intensity = 1.0 - (double) f / (frames - 1); 

            for (String line : lines) {
                StringBuilder sb = new StringBuilder();

                // yatay “shake”
                int shake = (int) Math.round(Math.sin(f * 0.7) * 3 * intensity);
                if (shake > 0) {
                    sb.append(" ".repeat(shake));
                }

                for (int i = 0; i < line.length(); i++) {
                    char ch = line.charAt(i);

                    if (ch == ' ') {
                        sb.append(" ");
                        continue;
                    }

                    if (Math.random() < 0.15 * intensity) {
                        
                        char noise;
                        double r = Math.random();
                        if (r < 0.33) noise = '#';
                        else if (r < 0.66) noise = '@';
                        else noise = '*';

                        sb.append(ConsoleColors.YELLOW_BRIGHT).append(noise).append(ConsoleColors.RESET);
                    } else {
                        sb.append(ConsoleColors.RED_BOLD).append(ch).append(ConsoleColors.RESET);
                    }
                }

                System.out.println(sb);
            }

            System.out.println();
            drawStarfield(1);
            sleep(70);
        }

        clearScreen();
    }

    // =========================================
    //       3) PARTICLE EXPLOSION + ASSEMBLE
    // =========================================

     /**
     * Plays a particle explosion animation followed by a falling-letter assembly
     * of the ASCII logo.
     *
     * @param ascii           ASCII text to assemble
     * @param explosionFrames number of explosion frames
     * @param delayMs         delay between explosion frames
     */
    private static void particleExplosion(String ascii, int explosionFrames, int delayMs) {
        
        for (int f = 0; f < explosionFrames; f++) {
            clearScreen();
            for (int y = 0; y < 18; y++) {
                StringBuilder row = new StringBuilder();
                for (int x = 0; x < 80; x++) {
                    double r = Math.random();
                    if (r < 0.03) {
                        row.append(ConsoleColors.YELLOW_BRIGHT).append("*").append(ConsoleColors.RESET);
                    } else if (r < 0.06) {
                        row.append(ConsoleColors.RED_BOLD).append("•").append(ConsoleColors.RESET);
                    } else if (r < 0.08) {
                        row.append(ConsoleColors.RED).append("×").append(ConsoleColors.RESET);
                    } else {
                        row.append(" ");
                    }
                }
                System.out.println(row);
            }
            sleep(delayMs);
        }

        letterDropAssemble(ascii, 35);
    }

     /**
     * Performs the falling-letter assembly effect, where each character drops
     * from above and settles into its final ASCII logo position.
     *
     * @param ascii ASCII text to assemble
     * @param speed animation speed in milliseconds
     */

    private static void letterDropAssemble(String ascii, int speed) {
        String[] L = ascii.split("\n");
        int H = L.length;
        int W = 0;
        for (String s : L) W = Math.max(W, s.length());

        double[][] y = new double[H][W];
        double[][] v = new double[H][W];

        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                y[r][c] = -(int) (Math.random() * 12 + 4);
                v[r][c] = Math.random() * 0.4 + 0.2;
            }
        }

        boolean done = false;

        while (!done) {
            done = true;
            clearScreen();
            drawStarfield(2);
            System.out.println();

            StringBuilder out = new StringBuilder();

            for (int r = 0; r < H; r++) {
                for (int c = 0; c < W; c++) {
                    char ch = (c < L[r].length()) ? L[r].charAt(c) : ' ';

                    if (ch == ' ') {
                        out.append(" ");
                        continue;
                    }

                    if (y[r][c] < r) {
                        y[r][c] += v[r][c];
                        v[r][c] += 0.04;          
                        out.append(" ");
                        done = false;
                    } else {
                        // hafif bounce
                        if (v[r][c] > 0.6) {
                            v[r][c] = -v[r][c] * 0.25;
                            y[r][c] = r - 0.2;
                            done = false;
                            out.append(ConsoleColors.YELLOW_BRIGHT).append(ch).append(ConsoleColors.RESET);
                        } else {
                            y[r][c] = r;
                            out.append(ConsoleColors.YELLOW_BRIGHT).append(ch).append(ConsoleColors.RESET);
                        }
                    }
                }
                out.append("\n");
            }

            System.out.print(out);
            System.out.println();
            drawStarfield(1);
            sleep(speed);
        }
    }

    // =========================================
    //       4) COLOR SHIFT GLOW
    // =========================================

    /**
     * Applies a multi-stage glowing color transition (red → bold red → yellow)
     * on the ASCII text, creating a smooth lighting effect.
     *
     * @param ascii   ASCII text to animate
     * @param steps   number of glow steps
     * @param delayMs delay between frames
     */
    private static void colorShiftGlow(String ascii, int steps, int delayMs) {
        String[] lines = ascii.split("\n");

        for (int s = 0; s < steps; s++) {
            clearScreen();
            drawStarfield(2);
            System.out.println();

            double t = (double) s / (steps - 1);
            String color;
            if (t < 0.33) color = ConsoleColors.RED;
            else if (t < 0.66) color = ConsoleColors.RED_BOLD;
            else color = ConsoleColors.YELLOW_BRIGHT;

            for (String line : lines) {
                System.out.println(color + line + ConsoleColors.RESET);
            }

            System.out.println();
            drawStarfield(1);
            sleep(delayMs);
        }

        
        clearScreen();
        drawStarfield(2);
        System.out.println();
        for (String line : lines) {
            System.out.println(ConsoleColors.WHITE_BRIGHT + line + ConsoleColors.RESET);
        }
        System.out.println();
        drawStarfield(1);
        sleep(600);
    }

    // =========================================
    //       5) GROUP BOX SLIDE-IN
    // =========================================

    /**
     * Slides in the group title and team members box from top to bottom while
     * keeping the ASCII logo visible above.
     *
     * @param ascii   ASCII logo
     * @param steps   number of animation steps
     * @param delayMs delay between frames
     */
    private static void slideInGroupBox(String ascii, int steps, int delayMs) {
        String[] logoLines = ascii.split("\n");
        String[] boxLines = (GROUP_TITLE + "\n" + NAMES_BOX).split("\n");

        for (int s = 1; s <= steps; s++) {
            clearScreen();
            drawStarfield(1);
            System.out.println();

            for (String line : logoLines) {
                System.out.println(ConsoleColors.YELLOW_BRIGHT + line + ConsoleColors.RESET);
            }

            System.out.println();

            int visible = (int) ((double) s / steps * boxLines.length);
            for (int i = 0; i < visible && i < boxLines.length; i++) {
                System.out.println(boxLines[i]);
            }

            System.out.println();
            drawStarfield(1);
            sleep(delayMs);
        }
    }

    // =========================================
    //       6) DISCO DANCE
    // =========================================

    /**
     * Shows a dancing stickman animation accompanied by a moving disco ball
     * and multiple dancers with alternating colors.
     */
    private static void playDiscoDance() {
        String RESET = "\u001B[0m";
        String CYAN = "\u001B[96m";
        String MAGENTA = "\u001B[95m";
        String YELLOW = "\u001B[93m";
        String RED = "\u001B[91m";
        String GREEN = "\u001B[92m";

        // Disco ball frames
        String[] ballFrames = {
                "   🪩   ",
                " ✨🪩✨ ",
                "   🪩   ",
                " ✨🪩✨ "
        };

        // Stickman dance frames
        String[] dancerFrames = {
                "   \\o/   \n    |    \n   / \\   ",
                "    o    \n   /|\\   \n   / \\   ",
                "   \\o    \n    |\\   \n   / \\   ",
                "    o/   \n   /|    \n   / \\   "
        };
        
        int numDancers = 5; 

        for (int i = 0; i < 40; i++) {
            clearScreen();

            
            StringBuilder sbBalls = new StringBuilder();
            String ballColor = (i % 2 == 0) ? CYAN : MAGENTA;
            String currentBall = ballFrames[i % ballFrames.length];
            
            sbBalls.append("     "); 
            for(int k=0; k < numDancers; k++) {
                 
                 sbBalls.append(ballColor).append(currentBall).append("   ").append(RESET);
            }
            System.out.println(sbBalls.toString());
            System.out.println(); 

            
            String currentDancerFrame = dancerFrames[i % dancerFrames.length];
            String[] lines = currentDancerFrame.split("\n");
            
           
            for (String linePart : lines) {
                StringBuilder sbDancerLine = new StringBuilder();
                sbDancerLine.append("     "); 
                
                for (int k = 0; k < numDancers; k++) {
                    // Renkleri çeşitlendir
                    String dColor;
                    if (k % 3 == 0) dColor = YELLOW;
                    else if (k % 3 == 1) dColor = GREEN;
                    else dColor = RED;
                    
                    sbDancerLine.append(dColor).append(linePart).append("   ").append(RESET);
                }
                System.out.println(sbDancerLine.toString());
            }

            sleep(150);
        }

        System.out.println();
    }

    // =========================================
    //          7) FULL PARTY BALLOON ANIMATION
    // =========================================

    /**
     * Plays a decorative balloon animation with floating patterns, confetti
     * movement, and a final explosion effect.
     */
    private static void playFullPartyBalloons() {

        String[] COLORS = {
                ConsoleColors.CYAN_BOLD,
                ConsoleColors.PURPLE_BOLD,
                ConsoleColors.YELLOW_BRIGHT,
                ConsoleColors.RED_BOLD,
                ConsoleColors.GREEN_BOLD
        };

        String[] balloonLines = {
                " 🎈  🎈  🎈    🎈  🎈     🎈   🎈    🎈  🎈   🎈  🎈 ",
                "   🎈    🎈  🎈     🎈    🎈   🎈    🎈   🎈     🎈 ",
                " 🎈    🎈     🎈   🎈    🎈     🎈   🎈   🎈    🎈 ",
                "    🎈  🎈    🎈    🎈    🎈   🎈     🎈   🎈     ",
                " 🎈   🎈    🎈     🎈    🎈    🎈     🎈   🎈   🎈 ",
                "   🎈     🎈   🎈      🎈   🎈     🎈   🎈     🎈  ",
                " 🎈    🎈    🎈    🎈     🎈    🎈    🎈     🎈   ",
                "     🎈    🎈     🎈    🎈     🎈    🎈     🎈     ",
                " 🎈    🎈    🎈     🎈     🎈      🎈     🎈    🎈 ",
                "   🎈   🎈     🎈    🎈      🎈     🎈    🎈       ",
                " 🎈   🎈    🎈     🎈    🎈     🎈   🎈    🎈   🎈 ",
                "     🎈     🎈    🎈     🎈     🎈    🎈      🎈   ",
                " 🎈     🎈    🎈      🎈     🎈     🎈    🎈   🎈  ",
                "   🎈     🎈    🎈    🎈      🎈    🎈     🎈      "
        };


        for (int frame = 0; frame < 25; frame++) {
            clearScreen();

            int offset = frame % balloonLines.length;

            
            StringBuilder confetti = new StringBuilder();
            for (int i = 0; i < 80; i++) {
                double r = Math.random();
                if (r < 0.04) confetti.append(ConsoleColors.YELLOW_BRIGHT).append("✦").append(ConsoleColors.RESET);
                else if (r < 0.08) confetti.append(ConsoleColors.RED_BOLD).append("✶").append(ConsoleColors.RESET);
                else confetti.append(" ");
            }
            System.out.println(confetti);

            
            for (int i = 0; i < balloonLines.length; i++) {
                String color = COLORS[(frame + i) % COLORS.length];
                System.out.println(color + balloonLines[(i + offset) % balloonLines.length] + ConsoleColors.RESET);
            }

            sleep(180);
        }

        
        for (int i = 0; i < 6; i++) {
            clearScreen();
            StringBuilder explosion = new StringBuilder();

            for (int j = 0; j < 80; j++) {
                double r = Math.random();
                if (r < 0.05) explosion.append(ConsoleColors.RED_BOLD).append("💥").append(ConsoleColors.RESET);
                else if (r < 0.10) explosion.append(ConsoleColors.YELLOW_BRIGHT).append("*").append(ConsoleColors.RESET);
                else if (r < 0.15) explosion.append(ConsoleColors.PURPLE_BOLD).append("✧").append(ConsoleColors.RESET);
                else explosion.append(" ");
            }

            System.out.println(explosion);
            sleep(150);
        }

        System.out.println();
    }

    // =========================================
    //            WELCOME / GOODBYE
    // =========================================

     /**
     * Plays the full welcome animation sequence, including:
     * <ul>
     *     <li>Space warp intro</li>
     *     <li>Glitch reveal</li>
     *     <li>Particle explosion and logo assembly</li>
     *     <li>Color glow transition</li>
     *     <li>Group banner slide-in</li>
     *     <li>Disco dance finale</li>
     * </ul>
     */
    public static void showWelcome() {
        
        spaceWarpIntro(WELCOME_ASCII, 18);

        glitchReveal(WELCOME_ASCII, 10);

        
        particleExplosion(WELCOME_ASCII, 9, 45);

        
        colorShiftGlow(WELCOME_ASCII, 6, 80);

        
        slideInGroupBox(WELCOME_ASCII, 6, 90);

        sleep(1500);

        
        playDiscoDance();
        sleep(500);
    }

    /**
     * Plays the goodbye animation sequence shown when the program exits.
     * Includes a space warp transition, glitch effects, explosion,
     * glowing fade-out, group box slide-in, and a celebratory balloon show.
     */

    public static void showGoodbye() {
        
        spaceWarpIntro(GOODBYE_ASCII, 14);
        glitchReveal(GOODBYE_ASCII, 8);
        particleExplosion(GOODBYE_ASCII, 7, 45);
        colorShiftGlow(GOODBYE_ASCII, 5, 80);
        slideInGroupBox(GOODBYE_ASCII, 5, 90);

        sleep(1500);

       
        playFullPartyBalloons();
    }

   
}

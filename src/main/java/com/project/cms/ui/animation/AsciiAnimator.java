package com.project.cms.ui.animation;

import com.project.cms.util.ConsoleColors;

public class AsciiAnimator {

    // =========================================
    //              UTIL
    // =========================================
    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (Exception ignored) {}
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

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

    private static String starLine(int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(randomStarChar());
        }
        return sb.toString();
    }

    private static void drawStarfield(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.println(starLine(80));
        }
    }

    // =========================================
    //              SABİT METİNLER
    // =========================================
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

                // sol taraf
                for (int i = 0; i < center - offset; i++) sb.append(" ");
                sb.append(ConsoleColors.RED_BOLD).append("/").append(ConsoleColors.RESET);
                
                // orta boşluk
                int gap = offset * 2;
                for (int i = 0; i < gap; i++) {
                    if (Math.random() < 0.06) sb.append(randomStarChar());
                    else sb.append(" ");
                }

                sb.append(ConsoleColors.RED_BOLD).append("\\").append(ConsoleColors.RESET);
                sb.append("\n");
            }

            // ortalarda logo gölgesi
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
    private static void glitchReveal(String ascii, int frames) {
        String[] lines = ascii.split("\n");

        for (int f = 0; f < frames; f++) {
            clearScreen();
            drawStarfield(2);
            System.out.println();

            double intensity = 1.0 - (double) f / (frames - 1); // başlangıçta çok glitch, sonra azalıyor

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
                        // random glitch karakter
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
    private static void particleExplosion(String ascii, int explosionFrames, int delayMs) {
        // 3.1 – patlama gürültüsü
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

        // 3.2 – harf düşerek toplanma
        letterDropAssemble(ascii, 35);
    }

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
                        v[r][c] += 0.04;          // gravity
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

        // final beyaz parıltı
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
    private static void slideInGroupBox(String ascii, int steps, int delayMs) {
        String[] logoLines = ascii.split("\n");
        String[] boxLines = (GROUP_TITLE + "\n" + NAMES_BOX).split("\n");

        for (int s = 1; s <= steps; s++) {
            clearScreen();
            drawStarfield(1);
            System.out.println();

            // logo sabit
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
        
        int numDancers = 5; // Parti kalabalığı

        for (int i = 0; i < 40; i++) {
            clearScreen();

            // --- 1) Disco Balls Satırı (Yan Yana) ---
            StringBuilder sbBalls = new StringBuilder();
            String ballColor = (i % 2 == 0) ? CYAN : MAGENTA;
            String currentBall = ballFrames[i % ballFrames.length];
            
            sbBalls.append("     "); // Sol boşluk
            for(int k=0; k < numDancers; k++) {
                 // Her topun yanına biraz boşluk
                 sbBalls.append(ballColor).append(currentBall).append("   ").append(RESET);
            }
            System.out.println(sbBalls.toString());
            System.out.println(); 

            // --- 2) Dansçılar Satırları (Yan Yana) ---
            // Seçili dans karesini satır satır bölüyoruz
            String currentDancerFrame = dancerFrames[i % dancerFrames.length];
            String[] lines = currentDancerFrame.split("\n");
            
            // Dansçının her satırı için (kafa, gövde, bacak vs.)
            for (String linePart : lines) {
                StringBuilder sbDancerLine = new StringBuilder();
                sbDancerLine.append("     "); // Sol boşluk
                
                for (int k = 0; k < numDancers; k++) {
                    // Renkleri çeşitlendir
                    String dColor;
                    if (k % 3 == 0) dColor = YELLOW;
                    else if (k % 3 == 1) dColor = GREEN;
                    else dColor = RED;
                    
                    // Dansçılar arası boşluk
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


        // Yukarı doğru kayan balon animasyonu
        for (int frame = 0; frame < 25; frame++) {
            clearScreen();

            int offset = frame % balloonLines.length;

            // Konfeti karışması
            StringBuilder confetti = new StringBuilder();
            for (int i = 0; i < 80; i++) {
                double r = Math.random();
                if (r < 0.04) confetti.append(ConsoleColors.YELLOW_BRIGHT).append("✦").append(ConsoleColors.RESET);
                else if (r < 0.08) confetti.append(ConsoleColors.RED_BOLD).append("✶").append(ConsoleColors.RESET);
                else confetti.append(" ");
            }
            System.out.println(confetti);

            // Balon çizimi
            for (int i = 0; i < balloonLines.length; i++) {
                String color = COLORS[(frame + i) % COLORS.length];
                System.out.println(color + balloonLines[(i + offset) % balloonLines.length] + ConsoleColors.RESET);
            }

            sleep(180);
        }

        // BALON PATLAMA EFEKTİ
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
    public static void showWelcome() {
        // 1) uzay tüneli
        spaceWarpIntro(WELCOME_ASCII, 18);

        // 2) glitch reveal
        glitchReveal(WELCOME_ASCII, 10);

        // 3) patlama + toplanma
        particleExplosion(WELCOME_ASCII, 9, 45);

        // 4) renk geçişli glow
        colorShiftGlow(WELCOME_ASCII, 6, 80);

        // 5) group 18 + isim kutusu aşağıdan kayarak gelsin
        slideInGroupBox(WELCOME_ASCII, 6, 90);

        sleep(1500);

        // 6) Disco Dance
        playDiscoDance();
        sleep(500);
    }

    public static void showGoodbye() {
        // goodbye için biraz daha kısa ama aynı tarz epik animasyon
        spaceWarpIntro(GOODBYE_ASCII, 14);
        glitchReveal(GOODBYE_ASCII, 8);
        particleExplosion(GOODBYE_ASCII, 7, 45);
        colorShiftGlow(GOODBYE_ASCII, 5, 80);
        slideInGroupBox(GOODBYE_ASCII, 5, 90);

        sleep(1500);

        // 7) Full Party Balloons
        playFullPartyBalloons();
    }

   
}

package com.github.tmslpm.gamepowunlimited.utils;

import com.github.tmslpm.gamepowunlimited.GamePowerUnlimited;
import com.github.tmslpm.gamepowunlimited.enums.PieceType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface GameToImage {

    Color BORDER_COLOR = new Color(45, 45, 45);

    static void generate(GamePowerUnlimited game, int cellSize) {

       // BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // enable anti aliasing
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        BufferedImage gridBuffer = GameToImage.generateGrid(game, cellSize, renderingHints);
        BufferedImage colIdBuffer = GameToImage.generateHeader(game, renderingHints, gridBuffer.getWidth());

        /////////////////:

        BufferedImage merged = new BufferedImage(gridBuffer.getWidth(), gridBuffer.getHeight() + colIdBuffer.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = merged.createGraphics();
        graphics.drawImage(colIdBuffer,0, 0, null);
        graphics.drawImage(gridBuffer,0 , colIdBuffer.getHeight(), null);

        try {
            ImageIO.write(merged, "png", new File("CustomImage.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage generateHeader(GamePowerUnlimited game, RenderingHints renderingHints, int width) {
        int height = 82;
        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = buffer.createGraphics();
        graphics.setRenderingHints(renderingHints);
        graphics.setFont(new Font("Consolas", Font.BOLD, 35));

        graphics.setColor(BORDER_COLOR);
        graphics.drawRect(0,0, width-1, height);

        FontMetrics metrics = graphics.getFontMetrics();
        String strVisitor = "visitor";
        int strVisitorWidth = (int) metrics.getStringBounds(strVisitor, graphics).getBounds2D().getWidth();

        String strHome = "home";
        int strHomeWidth = (int) metrics.getStringBounds(strHome, graphics).getBounds2D().getWidth();

        String strPointVisitor = "0";
        int strPointVisitorWidth = (int) metrics.getStringBounds(strPointVisitor, graphics).getBounds2D().getWidth();

        String strPointHome = "0";
        int strPointHomeWidth = (int) metrics.getStringBounds(strPointVisitor, graphics).getBounds2D().getWidth();

        int lineY = 40;
        int paddingLeft = width / 4;

        drawTextWithShadow(graphics,
                strVisitor,
                paddingLeft - (strVisitorWidth / 2),
                lineY,
                PieceType.YELLOW.getColor());

        drawTextWithShadow(graphics,
                strHome,
                width - 14 - paddingLeft - (strHomeWidth/2),
                lineY,
                PieceType.RED.getColor());

        graphics.setFont(new Font("Consolas", Font.BOLD, 30));
        drawTextWithShadow(graphics,
                strPointVisitor,
                paddingLeft - (strPointVisitorWidth/2),
                lineY  + 30,
                PieceType.YELLOW.getColor());

        drawTextWithShadow(graphics,
                strPointHome,
                width - 14 - paddingLeft - (strPointHomeWidth/2),
                lineY + 30,
                PieceType.RED.getColor());

        return buffer;
    }

    private static BufferedImage generateGrid(GamePowerUnlimited game, int cellSize, RenderingHints renderingHints) {
        int paddingHeight = 12;// require pair
        int paddingWidth = 12; // require pair
        int height = (game.getXLength() * cellSize) + (game.getXLength() * paddingHeight) + (paddingHeight);
        int width = (game.getYLength() * cellSize) + (game.getYLength() * paddingWidth) + 1;

        BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = buffer.createGraphics();
        graphics.setRenderingHints(renderingHints);

        // paint (note: position X != image pos X)
        for (int x = 0; x < (game.getXLength()); x++) {
            int imagePosY = (x * cellSize) + (paddingHeight / 2);
            for (int y = 0; y < game.getYLength(); y++) {
                int imagePosX = (y * cellSize);
                graphics.setColor(BORDER_COLOR);
                graphics.drawRect(0, 0, imagePosX + cellSize + (paddingWidth * y) + (paddingWidth), height - 1);

                PieceType piece = game.getGrid()[x + game.getComboLength()][y + game.getComboLength()];
                if (!PieceType.EMPTY.equals(piece)) {
                    drawPiece(graphics,
                            imagePosX + (paddingWidth * y) + (paddingWidth / 2),
                            imagePosY + (paddingHeight * x) + (paddingHeight / 2),
                            cellSize, piece.getColor());
                }
            }

        }
        return buffer;
    }

    private static void drawPiece(Graphics2D graphics, int x, int y, int size, Color color) {
        int ratioAlpha = 50;
        int maxShadow = 15;
        int gradientAlpha = ratioAlpha / maxShadow;

        Composite oldComposite = graphics.getComposite();
        graphics.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        graphics.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(), ratioAlpha));
        graphics.fillRoundRect(x,y, size, size, 90, 90);
        graphics.setComposite(oldComposite);
        graphics.setColor(color);
        graphics.drawRoundRect(x,y, size, size, 90, 90);


        graphics.setComposite(AlphaComposite.SrcOver.derive(0.5f));
        for (int i = 0; i < maxShadow; i++) {
            graphics.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(), ratioAlpha - (gradientAlpha * i)));
            graphics.drawRoundRect(x + i, y + i, size - (i*2), size- (i*2), 90, 90);
        }
        graphics.setComposite(oldComposite);
    }

    private static void drawTextWithShadow(Graphics2D graphics, String text, int x, int y, Color color) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawString(text, x, y);
        graphics.setColor(color);
        graphics.drawString(text, x - 2, y - 2);
    }

}

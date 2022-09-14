package main;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author Hevanafa
 */
public class Midlet extends MIDlet {
    public void startApp() {
        CanvasTemplate template = new CanvasTemplate(true);
        template.parent = this;
        Display.getDisplay(this).setCurrent(template);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
    }
}

class CanvasTemplate extends GameCanvas implements Runnable {
    Midlet parent;
    Image gawrGura;
    int frameCount = 0;
    
    public CanvasTemplate(boolean suppressKeyEvents) {
        super(suppressKeyEvents);
        setFullScreenMode(true);
        loadImage();
        
        Thread runner = new Thread(this);
        runner.start();
    }

    private void loadImage() {
        try {
            gawrGura = Image.createImage("main/Gawr Gura.png");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void run() {
        Graphics g = getGraphics();
        
        while (true) {
            g.setColor(0x6495ED);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.drawImage(
                gawrGura,
                getWidth() / 2,
                getHeight() / 2,
                Graphics.VCENTER | Graphics.HCENTER
            );

            g.setColor(0xffffff);
            g.drawString(frameCount + "", 0, 0, Graphics.TOP | Graphics.LEFT);
            g.drawString("Exit", getWidth(), getHeight(), Graphics.BOTTOM | Graphics.RIGHT);
            
            flushGraphics();
            repaint();
            
            frameCount++;
            
            try {
                Thread.sleep(33);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    protected void keyPressed(int keyCode) {
        if (keyCode == -7)
            parent.notifyDestroyed();
    }
}
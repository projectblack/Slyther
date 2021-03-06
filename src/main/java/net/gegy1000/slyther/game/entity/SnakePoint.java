package net.gegy1000.slyther.game.entity;

import net.gegy1000.slyther.server.ConnectedClient;

public class SnakePoint {
    public float posX;
    public float posY;
    public float prevPosX;
    public float prevPosY;
    public float wehang;
    public float fx;
    public float fy;
    public float ebx;
    public float eby;
    public boolean dying;
    public float deathAnimation;
    public int eiu;
    public int[] efs = new int[128];
    public float[] exs = new float[128];
    public float[] eys = new float[128];
    public float[] ems = new float[128];

    public SnakePoint(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void update() {
        prevPosX = posX;
        prevPosY = posY;
    }
}

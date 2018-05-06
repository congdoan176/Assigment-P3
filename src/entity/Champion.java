/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author honghung
 */
public class Champion implements Serializable {

    
    
    
//    public String toString() {
//        return "Champion{" + "id=" + id + ", img=" + img + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", ox=" + ox + ", oy=" + oy + '}';
//    }
    
    @Override
    public String toString() {
        return "Champion{" + "id=" + id + ", img=" + img + ", x=" + x + ", y=" + y + ", w=" + w + ", h=" + h + ", ox=" + ox + ", oy=" + oy + "}";
    }
    
    private long id;

    
    private String img;
    private int x;
    private int y;
    private int w;
    private int h;
    private int ox;
    private int oy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOx() {
        return ox;
    }

    public void setOx(int ox) {
        this.ox = ox;
    }

    public int getOy() {
        return oy;
    }

    public void setOy(int oy) {
        this.oy = oy;
    }

    public Champion(long id, String img, int x, int y, int w, int h, int ox, int oy) {
        this.id = id;
        this.img = img;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ox = ox;
        this.oy = oy;
    }

    public Champion(String img, int x, int y, int w, int h, int ox, int oy) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.ox = ox;
        this.oy = oy;
    }

    public Champion(String img, int x, int y, int w, int h) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

}
